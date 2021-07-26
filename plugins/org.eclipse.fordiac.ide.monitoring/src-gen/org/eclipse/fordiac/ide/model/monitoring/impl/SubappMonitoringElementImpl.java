/**
 * ******************************************************************************
 * Copyright (c) 2012, 2013, 2015 - 2017 Profactor GmbH, fortiss GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gerhard Ebenhofer, Alois Zoitl, Gerd Kainz
 *      - initial API and implementation and/or initial documentation
 * ******************************************************************************
 *
 */
package org.eclipse.fordiac.ide.model.monitoring.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.fordiac.ide.deployment.monitoringbase.MonitoringBaseElement;
import org.eclipse.fordiac.ide.model.monitoring.MonitoringPackage;
import org.eclipse.fordiac.ide.model.monitoring.SubappMonitoringElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subapp Monitoring Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.model.monitoring.impl.SubappMonitoringElementImpl#getAnchor <em>Anchor</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SubappMonitoringElementImpl extends MonitoringElementImpl implements SubappMonitoringElement {
	/**
	 * The cached value of the '{@link #getAnchor() <em>Anchor</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnchor()
	 * @generated
	 * @ordered
	 */
	protected MonitoringBaseElement anchor;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubappMonitoringElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MonitoringPackage.Literals.SUBAPP_MONITORING_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MonitoringBaseElement getAnchor() {
		if (anchor != null && anchor.eIsProxy()) {
			InternalEObject oldAnchor = (InternalEObject)anchor;
			anchor = (MonitoringBaseElement)eResolveProxy(oldAnchor);
			if (anchor != oldAnchor) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MonitoringPackage.SUBAPP_MONITORING_ELEMENT__ANCHOR, oldAnchor, anchor));
			}
		}
		return anchor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MonitoringBaseElement basicGetAnchor() {
		return anchor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAnchor(MonitoringBaseElement newAnchor) {
		MonitoringBaseElement oldAnchor = anchor;
		anchor = newAnchor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MonitoringPackage.SUBAPP_MONITORING_ELEMENT__ANCHOR, oldAnchor, anchor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getQualifiedString() {
				return anchor.getQualifiedString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MonitoringPackage.SUBAPP_MONITORING_ELEMENT__ANCHOR:
				if (resolve) return getAnchor();
				return basicGetAnchor();
			default:
				return super.eGet(featureID, resolve, coreType);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MonitoringPackage.SUBAPP_MONITORING_ELEMENT__ANCHOR:
				setAnchor((MonitoringBaseElement)newValue);
				return;
			default:
				super.eSet(featureID, newValue);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MonitoringPackage.SUBAPP_MONITORING_ELEMENT__ANCHOR:
				setAnchor((MonitoringBaseElement)null);
				return;
			default:
				super.eUnset(featureID);
				return;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MonitoringPackage.SUBAPP_MONITORING_ELEMENT__ANCHOR:
				return anchor != null;
			default:
				return super.eIsSet(featureID);
		}
	}

} //SubappMonitoringElementImpl