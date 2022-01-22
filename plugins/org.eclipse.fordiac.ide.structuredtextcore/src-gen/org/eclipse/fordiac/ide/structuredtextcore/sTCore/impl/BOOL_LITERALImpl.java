/**
 * generated by Xtext 2.25.0
 */
package org.eclipse.fordiac.ide.structuredtextcore.sTCore.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.fordiac.ide.structuredtextcore.sTCore.BOOL_LITERAL;
import org.eclipse.fordiac.ide.structuredtextcore.sTCore.STCorePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>BOOL LITERAL</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.sTCore.impl.BOOL_LITERALImpl#getNot <em>Not</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.sTCore.impl.BOOL_LITERALImpl#isKeyWordValue <em>Key Word Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BOOL_LITERALImpl extends MinimalEObjectImpl.Container implements BOOL_LITERAL
{
  /**
   * The default value of the '{@link #getNot() <em>Not</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNot()
   * @generated
   * @ordered
   */
  protected static final String NOT_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getNot() <em>Not</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getNot()
   * @generated
   * @ordered
   */
  protected String not = NOT_EDEFAULT;

  /**
   * The default value of the '{@link #isKeyWordValue() <em>Key Word Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isKeyWordValue()
   * @generated
   * @ordered
   */
  protected static final boolean KEY_WORD_VALUE_EDEFAULT = false;

  /**
   * The cached value of the '{@link #isKeyWordValue() <em>Key Word Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #isKeyWordValue()
   * @generated
   * @ordered
   */
  protected boolean keyWordValue = KEY_WORD_VALUE_EDEFAULT;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected BOOL_LITERALImpl()
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
    return STCorePackage.Literals.BOOL_LITERAL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getNot()
  {
    return not;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setNot(String newNot)
  {
    String oldNot = not;
    not = newNot;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.BOOL_LITERAL__NOT, oldNot, not));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isKeyWordValue()
  {
    return keyWordValue;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void setKeyWordValue(boolean newKeyWordValue)
  {
    boolean oldKeyWordValue = keyWordValue;
    keyWordValue = newKeyWordValue;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, STCorePackage.BOOL_LITERAL__KEY_WORD_VALUE, oldKeyWordValue, keyWordValue));
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
      case STCorePackage.BOOL_LITERAL__NOT:
        return getNot();
      case STCorePackage.BOOL_LITERAL__KEY_WORD_VALUE:
        return isKeyWordValue();
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
      case STCorePackage.BOOL_LITERAL__NOT:
        setNot((String)newValue);
        return;
      case STCorePackage.BOOL_LITERAL__KEY_WORD_VALUE:
        setKeyWordValue((Boolean)newValue);
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
      case STCorePackage.BOOL_LITERAL__NOT:
        setNot(NOT_EDEFAULT);
        return;
      case STCorePackage.BOOL_LITERAL__KEY_WORD_VALUE:
        setKeyWordValue(KEY_WORD_VALUE_EDEFAULT);
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
      case STCorePackage.BOOL_LITERAL__NOT:
        return NOT_EDEFAULT == null ? not != null : !NOT_EDEFAULT.equals(not);
      case STCorePackage.BOOL_LITERAL__KEY_WORD_VALUE:
        return keyWordValue != KEY_WORD_VALUE_EDEFAULT;
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    if (eIsProxy()) return super.toString();

    StringBuilder result = new StringBuilder(super.toString());
    result.append(" (not: ");
    result.append(not);
    result.append(", keyWordValue: ");
    result.append(keyWordValue);
    result.append(')');
    return result.toString();
  }

} //BOOL_LITERALImpl