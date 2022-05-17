/**
 * *******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmendía, Bianca Wiesmayr
 *          - initial implementation and/or documentation
 * *******************************************************************************
 */
package org.eclipse.fordiac.ide.fb.interpreter.OpSem;

import org.eclipse.emf.common.util.EList;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetwork;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>FB
 * Network Runtime</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime#getFbnetwork
 * <em>Fbnetwork</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime#getFbRuntimes
 * <em>Fb Runtimes</em>}</li>
 * <li>{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime#getTransferData
 * <em>Transfer Data</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFBNetworkRuntime()
 * @model
 * @generated
 */
public interface FBNetworkRuntime extends FBRuntimeAbstract {
	/**
	 * Returns the value of the '<em><b>Fbnetwork</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Fbnetwork</em>' reference.
	 * @see #setFbnetwork(FBNetwork)
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFBNetworkRuntime_Fbnetwork()
	 * @model required="true"
	 * @generated
	 */
	FBNetwork getFbnetwork();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBNetworkRuntime#getFbnetwork
	 * <em>Fbnetwork</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Fbnetwork</em>' reference.
	 * @see #getFbnetwork()
	 * @generated
	 */
	void setFbnetwork(FBNetwork value);

	/**
	 * Returns the value of the '<em><b>Fb Runtimes</b></em>' containment reference
	 * list. The list contents are of type
	 * {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.FBRuntimeAbstract}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Fb Runtimes</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFBNetworkRuntime_FbRuntimes()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<FBRuntimeAbstract> getFbRuntimes();

	/**
	 * Returns the value of the '<em><b>Transfer Data</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link org.eclipse.fordiac.ide.fb.interpreter.OpSem.TransferData}. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Transfer Data</em>' containment reference list.
	 * @see org.eclipse.fordiac.ide.fb.interpreter.OpSem.OperationalSemanticsPackage#getFBNetworkRuntime_TransferData()
	 * @model containment="true" resolveProxies="true"
	 * @generated
	 */
	EList<TransferData> getTransferData();

} // FBNetworkRuntime