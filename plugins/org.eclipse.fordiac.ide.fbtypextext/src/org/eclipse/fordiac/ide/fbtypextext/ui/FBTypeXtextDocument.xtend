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
 * 	 Christoph Binder - Extracted code from STAlgorithmDocument, to enable possibility to reuse this class for multiple xtexteditors
 *******************************************************************************/
package org.eclipse.fordiac.ide.fbtypextext.ui

import com.google.inject.Inject
import org.eclipse.core.runtime.IAdaptable
import org.eclipse.fordiac.ide.fbtypextext.FBTypeXtextResource
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.xtext.ui.editor.model.DocumentTokenSource
import org.eclipse.xtext.ui.editor.model.XtextDocument
import org.eclipse.xtext.ui.editor.model.edit.ITextEditComposer

class FBTypeXtextDocument extends XtextDocument implements IAdaptable {
	
	@Inject
	new(DocumentTokenSource tokenSource, ITextEditComposer composer) {
		super(tokenSource, composer)
	}

	override <T> T getAdapter(Class<T> adapterType) {
		if(FBType.equals(adapterType)) adapterType.cast(FBType) else super.getAdapter(adapterType);
	}

	def FBType getFBType() {
		readOnly[resource|if(resource instanceof FBTypeXtextResource) resource.fbType else null]
	}
}