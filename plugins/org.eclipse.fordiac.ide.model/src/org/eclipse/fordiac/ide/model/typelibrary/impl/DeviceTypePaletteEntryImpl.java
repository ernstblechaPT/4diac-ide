/*******************************************************************************
 * Copyright (c) 2008, 2022 Profactor GmbH, TU Wien ACIN, fortiss GmbH,
 * 							Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Ingo Hegny, Monika Wenger, Martin Jobst
 *      - initial API and implementation and/or initial documentation
 *    Alois Zoitl  - turned the Palette model into POJOs
 ******************************************************************************/
package org.eclipse.fordiac.ide.model.typelibrary.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.fordiac.ide.model.Palette.PalettePackage;
import org.eclipse.fordiac.ide.model.dataimport.CommonElementImporter;
import org.eclipse.fordiac.ide.model.libraryElement.DeviceType;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.DeviceTypeEntry;
import org.eclipse.fordiac.ide.ui.FordiacLogHelper;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Device Type Palette Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class DeviceTypePaletteEntryImpl extends AbstractTypeEntryImpl implements DeviceTypeEntry {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeviceTypePaletteEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PalettePackage.Literals.DEVICE_TYPE_PALETTE_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DeviceType getDeviceType() {
		final LibraryElement type = super.getType();
		if(type instanceof DeviceType){
			return (DeviceType) type;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(final LibraryElement type) {
		if(type instanceof DeviceType){
			super.setType(type);
		}else{
			super.setType(null);
			if(null != type){
				FordiacLogHelper .logError("tried to set no DeviceType as type entry for DeviceTypePaletteEntry");  //$NON-NLS-1$
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CommonElementImporter getImporter() {
		return new org.eclipse.fordiac.ide.model.dataimport.DEVImporter(getFile());
	}

} //DeviceTypePaletteEntryImpl
