/**
 * generated by Xtext 2.25.0
 */
package org.eclipse.fordiac.ide.structuredtextcore.sTCore;

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>DATE AND TIME LITERAL</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.sTCore.DATE_AND_TIME_LITERAL#getKeyword <em>Keyword</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.sTCore.DATE_AND_TIME_LITERAL#getDateValue <em>Date Value</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.sTCore.DATE_AND_TIME_LITERAL#getTimeOfDayValue <em>Time Of Day Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.structuredtextcore.sTCore.STCorePackage#getDATE_AND_TIME_LITERAL()
 * @model
 * @generated
 */
public interface DATE_AND_TIME_LITERAL extends EObject
{
  /**
   * Returns the value of the '<em><b>Keyword</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Keyword</em>' attribute.
   * @see #setKeyword(String)
   * @see org.eclipse.fordiac.ide.structuredtextcore.sTCore.STCorePackage#getDATE_AND_TIME_LITERAL_Keyword()
   * @model
   * @generated
   */
  String getKeyword();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.sTCore.DATE_AND_TIME_LITERAL#getKeyword <em>Keyword</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Keyword</em>' attribute.
   * @see #getKeyword()
   * @generated
   */
  void setKeyword(String value);

  /**
   * Returns the value of the '<em><b>Date Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Date Value</em>' attribute.
   * @see #setDateValue(Date)
   * @see org.eclipse.fordiac.ide.structuredtextcore.sTCore.STCorePackage#getDATE_AND_TIME_LITERAL_DateValue()
   * @model
   * @generated
   */
  Date getDateValue();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.sTCore.DATE_AND_TIME_LITERAL#getDateValue <em>Date Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Date Value</em>' attribute.
   * @see #getDateValue()
   * @generated
   */
  void setDateValue(Date value);

  /**
   * Returns the value of the '<em><b>Time Of Day Value</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Time Of Day Value</em>' attribute.
   * @see #setTimeOfDayValue(String)
   * @see org.eclipse.fordiac.ide.structuredtextcore.sTCore.STCorePackage#getDATE_AND_TIME_LITERAL_TimeOfDayValue()
   * @model
   * @generated
   */
  String getTimeOfDayValue();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.sTCore.DATE_AND_TIME_LITERAL#getTimeOfDayValue <em>Time Of Day Value</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Time Of Day Value</em>' attribute.
   * @see #getTimeOfDayValue()
   * @generated
   */
  void setTimeOfDayValue(String value);

} // DATE_AND_TIME_LITERAL