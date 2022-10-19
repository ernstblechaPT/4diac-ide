/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Ulzii Jargalsaikhan - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextcore.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parsetree.reconstr.impl.DefaultCommentAssociater;
import org.eclipse.xtext.parsetree.reconstr.impl.NodeIterator;
import org.eclipse.xtext.util.ITextRegionWithLineInformation;
import org.eclipse.xtext.util.Pair;

public class STCoreExportCommentAssociater extends DefaultCommentAssociater {

	@Override
	protected void associateCommentsWithSemanticEObjects(Map<ILeafNode, EObject> mapping, ICompositeNode rootNode) {
		EObject currentEObject = null;
		List<ILeafNode> currentComments = new ArrayList<ILeafNode>();
		NodeIterator nodeIterator = new NodeIterator(rootNode);
		// rewind to previous token with token owner
		while (nodeIterator.hasPrevious() && currentEObject == null) {
			INode node = nodeIterator.previous();
			if (tokenUtil.isToken(node)) {
				currentEObject = tokenUtil.getTokenOwner(node);
			}
		}
		while (nodeIterator.hasNext()) {
			INode node = nodeIterator.next();
			if (tokenUtil.isCommentNode(node)) {
				currentComments.add((ILeafNode) node);
			}
			boolean isToken = tokenUtil.isToken(node);
			if ((node instanceof ILeafNode || isToken) && currentEObject != null) {
				ITextRegionWithLineInformation textRegion = node.getTextRegionWithLineInformation();
				if (textRegion.getLineNumber() != textRegion.getEndLineNumber()) {
					// found a newline -> associating existing comments with currentEObject
					addMapping(mapping, currentComments, currentEObject);
					currentEObject = null;
				}
			}
			if (isToken) {
				Pair<List<ILeafNode>, List<ILeafNode>> leadingAndTrailingHiddenTokens = tokenUtil
						.getLeadingAndTrailingHiddenTokens(node);
				for (ILeafNode leadingHiddenNode : leadingAndTrailingHiddenTokens.getFirst()) {
					if (tokenUtil.isCommentNode(leadingHiddenNode)) {
						currentComments.add(leadingHiddenNode);
					}
				}
				nodeIterator.prune();
				EObject currentEObjectBuffer = currentEObject;
				currentEObject = tokenUtil.getTokenOwner(node);
				if (currentEObjectBuffer != null) {
					for (ILeafNode leafNode : currentComments) {
						if ((leafNode.getTotalEndLine() > NodeModelUtils.getNode(currentEObjectBuffer).getTotalEndLine()
								+ 1)
								|| leafNode.getGrammarElement() instanceof TerminalRule
										&& ((TerminalRule) leafNode.getGrammarElement()).getName()
												.equalsIgnoreCase("ML_COMMENT")) {
							currentEObjectBuffer = currentEObject;
						}

						addSingleMapping(mapping, leafNode, currentEObjectBuffer);
					}
					currentComments.clear();
					if (node.getOffset() > rootNode.getEndOffset()) {
						// found next EObject outside rootNode
						break;
					}
				}
			}
		}
		if (!currentComments.isEmpty()) {
			if (currentEObject != null) {
				addMapping(mapping, currentComments, currentEObject);
			} else {
				EObject objectForRemainingComments = getEObjectForRemainingComments(rootNode);
				if (objectForRemainingComments != null) {
					addMapping(mapping, currentComments, objectForRemainingComments);
				}
			}
		}
	}

	protected void addSingleMapping(Map<ILeafNode, EObject> mapping, ILeafNode currentComment, EObject currentEObject) {
		mapping.put(currentComment, currentEObject);
	}
}
