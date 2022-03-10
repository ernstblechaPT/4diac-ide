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
package org.eclipse.fordiac.ide.model.eval.value

import org.eclipse.fordiac.ide.model.data.CharType
import org.eclipse.fordiac.ide.model.datatype.helper.IecTypes.ElementaryTypes

class CharValue implements AnyCharValue {
	final byte value;

	public static final CharValue DEFAULT = new CharValue(0 as byte)

	private new(byte value) {
		this.value = value;
	}

	def static toCharValue(byte value) { new CharValue(value) }

	def static toCharValue(String value) { new CharValue((if(value.length > 0) value.charAt(0) else 0) as byte) }

	def static toCharValue(AnyCharsValue value) { value.toString.toCharValue }

	override CharType getType() { ElementaryTypes.CHAR }

	override equals(Object obj) { if(obj instanceof CharValue) value == obj.value else false }

	override hashCode() { Byte.hashCode(value) }

	override toString() { Character.toString(value) }
}