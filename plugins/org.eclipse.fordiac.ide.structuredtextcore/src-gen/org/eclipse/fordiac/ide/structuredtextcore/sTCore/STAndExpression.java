/**
 * generated by Xtext 2.25.0
 */
package org.eclipse.fordiac.ide.structuredtextcore.sTCore;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ST And Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.sTCore.STAndExpression#getLeft <em>Left</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.sTCore.STAndExpression#getOp <em>Op</em>}</li>
 *   <li>{@link org.eclipse.fordiac.ide.structuredtextcore.sTCore.STAndExpression#getRight <em>Right</em>}</li>
 * </ul>
 *
 * @see org.eclipse.fordiac.ide.structuredtextcore.sTCore.STCorePackage#getSTAndExpression()
 * @model
 * @generated
 */
public interface STAndExpression extends STExpression
{
  /**
   * Returns the value of the '<em><b>Left</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Left</em>' containment reference.
   * @see #setLeft(STExpression)
   * @see org.eclipse.fordiac.ide.structuredtextcore.sTCore.STCorePackage#getSTAndExpression_Left()
   * @model containment="true"
   * @generated
   */
  STExpression getLeft();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.sTCore.STAndExpression#getLeft <em>Left</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Left</em>' containment reference.
   * @see #getLeft()
   * @generated
   */
  void setLeft(STExpression value);

  /**
   * Returns the value of the '<em><b>Op</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Op</em>' attribute.
   * @see #setOp(String)
   * @see org.eclipse.fordiac.ide.structuredtextcore.sTCore.STCorePackage#getSTAndExpression_Op()
   * @model
   * @generated
   */
  String getOp();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.sTCore.STAndExpression#getOp <em>Op</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Op</em>' attribute.
   * @see #getOp()
   * @generated
   */
  void setOp(String value);

  /**
   * Returns the value of the '<em><b>Right</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the value of the '<em>Right</em>' containment reference.
   * @see #setRight(STExpression)
   * @see org.eclipse.fordiac.ide.structuredtextcore.sTCore.STCorePackage#getSTAndExpression_Right()
   * @model containment="true"
   * @generated
   */
  STExpression getRight();

  /**
   * Sets the value of the '{@link org.eclipse.fordiac.ide.structuredtextcore.sTCore.STAndExpression#getRight <em>Right</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Right</em>' containment reference.
   * @see #getRight()
   * @generated
   */
  void setRight(STExpression value);

} // STAndExpression