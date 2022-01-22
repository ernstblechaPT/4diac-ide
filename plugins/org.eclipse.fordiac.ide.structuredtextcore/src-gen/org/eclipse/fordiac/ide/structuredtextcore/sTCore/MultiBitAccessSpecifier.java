/**
 * generated by Xtext 2.25.0
 */
package org.eclipse.fordiac.ide.structuredtextcore.sTCore;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Multi Bit Access Specifier</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see org.eclipse.fordiac.ide.structuredtextcore.sTCore.STCorePackage#getMultiBitAccessSpecifier()
 * @model
 * @generated
 */
public enum MultiBitAccessSpecifier implements Enumerator
{
  /**
   * The '<em><b>Lword Access</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #LWORD_ACCESS_VALUE
   * @generated
   * @ordered
   */
  LWORD_ACCESS(0, "lwordAccess", ".%L"),

  /**
   * The '<em><b>Dword Access</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #DWORD_ACCESS_VALUE
   * @generated
   * @ordered
   */
  DWORD_ACCESS(1, "dwordAccess", ".%D"),

  /**
   * The '<em><b>Word Access</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #WORD_ACCESS_VALUE
   * @generated
   * @ordered
   */
  WORD_ACCESS(2, "wordAccess", ".%W"),

  /**
   * The '<em><b>Byte Access</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #BYTE_ACCESS_VALUE
   * @generated
   * @ordered
   */
  BYTE_ACCESS(3, "byteAccess", ".%B"),

  /**
   * The '<em><b>Bit Access</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #BIT_ACCESS_VALUE
   * @generated
   * @ordered
   */
  BIT_ACCESS(4, "bitAccess", ".%X"),

  /**
   * The '<em><b>Bit Access Shortcut</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #BIT_ACCESS_SHORTCUT_VALUE
   * @generated
   * @ordered
   */
  BIT_ACCESS_SHORTCUT(5, "bitAccessShortcut", ".");

  /**
   * The '<em><b>Lword Access</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #LWORD_ACCESS
   * @model name="lwordAccess" literal=".%L"
   * @generated
   * @ordered
   */
  public static final int LWORD_ACCESS_VALUE = 0;

  /**
   * The '<em><b>Dword Access</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #DWORD_ACCESS
   * @model name="dwordAccess" literal=".%D"
   * @generated
   * @ordered
   */
  public static final int DWORD_ACCESS_VALUE = 1;

  /**
   * The '<em><b>Word Access</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #WORD_ACCESS
   * @model name="wordAccess" literal=".%W"
   * @generated
   * @ordered
   */
  public static final int WORD_ACCESS_VALUE = 2;

  /**
   * The '<em><b>Byte Access</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #BYTE_ACCESS
   * @model name="byteAccess" literal=".%B"
   * @generated
   * @ordered
   */
  public static final int BYTE_ACCESS_VALUE = 3;

  /**
   * The '<em><b>Bit Access</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #BIT_ACCESS
   * @model name="bitAccess" literal=".%X"
   * @generated
   * @ordered
   */
  public static final int BIT_ACCESS_VALUE = 4;

  /**
   * The '<em><b>Bit Access Shortcut</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #BIT_ACCESS_SHORTCUT
   * @model name="bitAccessShortcut" literal="."
   * @generated
   * @ordered
   */
  public static final int BIT_ACCESS_SHORTCUT_VALUE = 5;

  /**
   * An array of all the '<em><b>Multi Bit Access Specifier</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final MultiBitAccessSpecifier[] VALUES_ARRAY =
    new MultiBitAccessSpecifier[]
    {
      LWORD_ACCESS,
      DWORD_ACCESS,
      WORD_ACCESS,
      BYTE_ACCESS,
      BIT_ACCESS,
      BIT_ACCESS_SHORTCUT,
    };

  /**
   * A public read-only list of all the '<em><b>Multi Bit Access Specifier</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<MultiBitAccessSpecifier> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>Multi Bit Access Specifier</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param literal the literal.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static MultiBitAccessSpecifier get(String literal)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      MultiBitAccessSpecifier result = VALUES_ARRAY[i];
      if (result.toString().equals(literal))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Multi Bit Access Specifier</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param name the name.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static MultiBitAccessSpecifier getByName(String name)
  {
    for (int i = 0; i < VALUES_ARRAY.length; ++i)
    {
      MultiBitAccessSpecifier result = VALUES_ARRAY[i];
      if (result.getName().equals(name))
      {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>Multi Bit Access Specifier</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the integer value.
   * @return the matching enumerator or <code>null</code>.
   * @generated
   */
  public static MultiBitAccessSpecifier get(int value)
  {
    switch (value)
    {
      case LWORD_ACCESS_VALUE: return LWORD_ACCESS;
      case DWORD_ACCESS_VALUE: return DWORD_ACCESS;
      case WORD_ACCESS_VALUE: return WORD_ACCESS;
      case BYTE_ACCESS_VALUE: return BYTE_ACCESS;
      case BIT_ACCESS_VALUE: return BIT_ACCESS;
      case BIT_ACCESS_SHORTCUT_VALUE: return BIT_ACCESS_SHORTCUT;
    }
    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final int value;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String name;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String literal;

  /**
   * Only this class can construct instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private MultiBitAccessSpecifier(int value, String name, String literal)
  {
    this.value = value;
    this.name = name;
    this.literal = literal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public int getValue()
  {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getName()
  {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String getLiteral()
  {
    return literal;
  }

  /**
   * Returns the literal value of the enumerator, which is its string representation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString()
  {
    return literal;
  }
  
} //MultiBitAccessSpecifier