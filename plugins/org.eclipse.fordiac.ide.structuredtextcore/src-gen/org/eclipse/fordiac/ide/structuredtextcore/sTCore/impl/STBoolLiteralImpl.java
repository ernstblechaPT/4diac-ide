/**
 * generated by Xtext 2.25.0
 */
package org.eclipse.fordiac.ide.structuredtextcore.sTCore.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.fordiac.ide.structuredtextcore.sTCore.BOOL_LITERAL;
import org.eclipse.fordiac.ide.structuredtextcore.sTCore.STBoolLiteral;
import org.eclipse.fordiac.ide.structuredtextcore.sTCore.STCorePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ST Bool Literal</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.sTCore.impl.STBoolLiteralImpl#getBoolLiteral <em>Bool Literal</em>}</li>
 * </ul>
 *
 * @generated
 */
public class STBoolLiteralImpl extends STExpressionImpl implements STBoolLiteral
{
  /**
   * The cached value of the '{@link #getBoolLiteral() <em>Bool Literal</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getBoolLiteral()
   * @generated
   * @ordered
   */
  protected BOOL_LITERAL boolLiteral;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected STBoolLiteralImpl()
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
    return STCorePackage.Literals.ST_BOOL_LITERAL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public BOOL_LITERAL getBoolLiteral()
  {
    return boolLiteral;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetBoolLiteral(BOOL_LITERAL newBoolLiteral, NotificationChain msgs)
  {
    BOOL_LITERAL oldBoolLiteral = boolLiteral;
    boolLiteral = newBoolLiteral;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, STCorePackage.ST_BOOL_LITERAL__BOOL_LITERAL, oldBoolLiteral, newBoolLiteral);
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
  public void setBoolLiteral(BOOL_LITERAL newBoolLiteral)
  {
    if (newBoolLiteral != boolLiteral)
    {
      NotificationChain msgs = null;
      if (boolLiteral != null)
        msgs = ((InternalEObject)boolLiteral).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_BOOL_LITERAL__BOOL_LITERAL, null, msgs);
      if (newBoolLiteral != null)
        msgs = ((InternalEObject)newBoolLiteral).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - STCorePackage.ST_BOOL_LITERAL__BOOL_LITERAL, null, msgs);
      msgs = basicSetBoolLiteral(newBoolLiteral, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.ST_BOOL_LITERAL__BOOL_LITERAL, newBoolLiteral, newBoolLiteral));
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
      case STCorePackage.ST_BOOL_LITERAL__BOOL_LITERAL:
        return basicSetBoolLiteral(null, msgs);
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
      case STCorePackage.ST_BOOL_LITERAL__BOOL_LITERAL:
        return getBoolLiteral();
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
      case STCorePackage.ST_BOOL_LITERAL__BOOL_LITERAL:
        setBoolLiteral((BOOL_LITERAL)newValue);
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
      case STCorePackage.ST_BOOL_LITERAL__BOOL_LITERAL:
        setBoolLiteral((BOOL_LITERAL)null);
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
      case STCorePackage.ST_BOOL_LITERAL__BOOL_LITERAL:
        return boolLiteral != null;
    }
    return super.eIsSet(featureID);
  }

} //STBoolLiteralImpl