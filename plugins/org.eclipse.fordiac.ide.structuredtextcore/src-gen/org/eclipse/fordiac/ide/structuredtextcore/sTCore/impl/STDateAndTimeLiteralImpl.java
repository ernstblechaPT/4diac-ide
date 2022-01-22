/**
 * generated by Xtext 2.25.0
 */
package org.eclipse.fordiac.ide.structuredtextcore.sTCore.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.fordiac.ide.structuredtextcore.sTCore.DATE_AND_TIME_LITERAL;
import org.eclipse.fordiac.ide.structuredtextcore.sTCore.STCorePackage;
import org.eclipse.fordiac.ide.structuredtextcore.sTCore.STDateAndTimeLiteral;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ST Date And Time Literal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.sTCore.impl.STDateAndTimeLiteralImpl#getTimeLiteral <em>Time Literal</em>}</li>
 * </ul>
 *
 * @generated
 */
public class STDateAndTimeLiteralImpl extends STExpressionImpl implements STDateAndTimeLiteral
{
  /**
   * The cached value of the '{@link #getTimeLiteral() <em>Time Literal</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTimeLiteral()
   * @generated
   * @ordered
   */
  protected DATE_AND_TIME_LITERAL timeLiteral;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected STDateAndTimeLiteralImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return STCorePackage.Literals.ST_DATE_AND_TIME_LITERAL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public DATE_AND_TIME_LITERAL getTimeLiteral()
  {
    return timeLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetTimeLiteral(DATE_AND_TIME_LITERAL newTimeLiteral, NotificationChain msgs)
  {
    DATE_AND_TIME_LITERAL oldTimeLiteral = timeLiteral;
    timeLiteral = newTimeLiteral;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, STCorePackage.ST_DATE_AND_TIME_LITERAL__TIME_LITERAL, oldTimeLiteral, newTimeLiteral);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setTimeLiteral(DATE_AND_TIME_LITERAL newTimeLiteral)
  {
    if (newTimeLiteral != timeLiteral)
    {
      NotificationChain msgs = null;
      if (timeLiteral != null)
        msgs = ((InternalEObject)timeLiteral).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_DATE_AND_TIME_LITERAL__TIME_LITERAL, null, msgs);
      if (newTimeLiteral != null)
        msgs = ((InternalEObject)newTimeLiteral).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_DATE_AND_TIME_LITERAL__TIME_LITERAL, null, msgs);
      msgs = basicSetTimeLiteral(newTimeLiteral, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_DATE_AND_TIME_LITERAL__TIME_LITERAL, newTimeLiteral, newTimeLiteral));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case STCorePackage.ST_DATE_AND_TIME_LITERAL__TIME_LITERAL:
        return basicSetTimeLiteral(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case STCorePackage.ST_DATE_AND_TIME_LITERAL__TIME_LITERAL:
        return getTimeLiteral();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case STCorePackage.ST_DATE_AND_TIME_LITERAL__TIME_LITERAL:
        setTimeLiteral((DATE_AND_TIME_LITERAL)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case STCorePackage.ST_DATE_AND_TIME_LITERAL__TIME_LITERAL:
        setTimeLiteral((DATE_AND_TIME_LITERAL)null);
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case STCorePackage.ST_DATE_AND_TIME_LITERAL__TIME_LITERAL:
        return timeLiteral != null;
    }
    return super.eIsSet(featureID);
  }

} //STDateAndTimeLiteralImpl