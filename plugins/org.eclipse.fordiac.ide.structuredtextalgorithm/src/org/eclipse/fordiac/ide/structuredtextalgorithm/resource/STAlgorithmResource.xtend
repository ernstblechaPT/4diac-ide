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
package org.eclipse.fordiac.ide.structuredtextalgorithm.resource

import com.google.inject.Inject
import java.io.IOException
import java.io.InputStream
import java.util.Map
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.Path
import org.eclipse.fordiac.ide.model.libraryElement.FBType
import org.eclipse.fordiac.ide.model.typelibrary.TypeLibraryManager
import org.eclipse.fordiac.ide.structuredtextalgorithm.util.STAlgorithmPartitioner
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.linking.lazy.LazyLinkingResource
import org.eclipse.xtext.util.LazyStringInputStream

import static extension org.eclipse.emf.ecore.util.EcoreUtil.copy

class STAlgorithmResource extends LazyLinkingResource {
	public static final String OPTION_PLAIN_ST = STAlgorithmResource.name + ".PLAIN_ST";

	@Accessors
	FBType fbType

	@Inject
	extension STAlgorithmPartitioner partitioner

	override doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		clearInternalFBType
		if (options.loadPlainST) {
			super.doLoad(inputStream, options)
		} else {
			try {
				val typeFile = ResourcesPlugin.workspace.root.getFile(new Path(uri.toPlatformString(true)))
				val typeEntry = TypeLibraryManager.INSTANCE.getTypeEntryForFile(typeFile)
				if (typeEntry !== null) {
					val libraryElement = typeEntry.typeEditable
					if (libraryElement instanceof FBType) {
						fbType = libraryElement
					}
				}
			} catch (Throwable t) {
				throw new IOException("Couldn't load FB type", t)
			}
			val text = fbType.combine
			super.doLoad(new LazyStringInputStream(text, encoding), options)
		}
		updateInternalFBType
	}

	def boolean isLoadPlainST(Map<?, ?> options) {
		"stalg".equalsIgnoreCase(uri.fileExtension) || Boolean.TRUE.equals(options?.get(OPTION_PLAIN_ST) ?: "")
	}

	def setFbType(FBType fbType) {
		this.fbType = fbType
		updateInternalFBType
	}

	def protected void updateInternalFBType() {
		clearInternalFBType
		if (!contents.nullOrEmpty && fbType !== null) {
			contents.add(fbType.copy)
		}
	}

	def protected void clearInternalFBType() {
		contents?.removeIf[it instanceof FBType]
	}

	override synchronized getEObject(String uriFragment) {
		try {
			super.getEObject(uriFragment)
		} catch (IllegalArgumentException e) {
			null
		}
	}

	def Map<Object, Object> getDefaultLoadOptions() {
		if (defaultLoadOptions === null) {
			defaultLoadOptions = newHashMap
		}
		return defaultLoadOptions
	}
}
