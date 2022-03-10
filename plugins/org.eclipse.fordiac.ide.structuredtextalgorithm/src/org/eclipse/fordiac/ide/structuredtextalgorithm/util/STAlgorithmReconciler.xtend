/*******************************************************************************
 * Copyright (c) 2022 Martin Erich Jobst
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Martin Jobst - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.structuredtextalgorithm.util

import org.eclipse.emf.common.util.ECollections
import org.eclipse.emf.common.util.EList
import org.eclipse.fordiac.ide.model.libraryElement.ICallable
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement
import org.eclipse.fordiac.ide.model.libraryElement.STAlgorithm
import org.eclipse.fordiac.ide.model.libraryElement.STMethod

import static extension org.eclipse.emf.ecore.util.EcoreUtil.*

class STAlgorithmReconciler {

	def void reconcile(EList<ICallable> dest, EList<? extends ICallable> source) {
		// check duplicates in source or dest (very bad)
		if (checkDuplicates(source) || checkDuplicates(dest)) {
			return; // don't even try to attempt this or risk screwing dest up
		}
		// remove from dest if not in source
		dest.removeIf[alg|!source.exists[name == alg.name]]
		// add or merge/move according to source
		source.forEach [ alg, index |
			val candidate = dest.findFirst[name == alg.name]
			if (candidate !== null && merge(candidate, alg)) {
				dest.move(index, candidate) // move to position (dest must contain at least index algs since we insert as we go)
			} else {
				dest.add(index, alg) // insert at position
			}
		]
	}

	def protected dispatch boolean merge(INamedElement dest, INamedElement source) { false }

	def protected dispatch boolean merge(STAlgorithm dest, STAlgorithm source) {
		dest => [
			comment = source.comment
			text = source.text
		]
		true
	}

	def protected dispatch boolean merge(STMethod dest, STMethod source) {
		dest => [
			comment = source.comment
			text = source.text
			ECollections.setEList(inputParameters, source.inputParameters.map[copy].toList)
			ECollections.setEList(outputParameters, source.outputParameters.map[copy].toList)
			returnType = source.returnType
		]
		true
	}

	def protected boolean checkDuplicates(EList<? extends ICallable> list) {
		list.exists[algorithm|list.exists[it !== algorithm && name == algorithm.name]]
	}
}