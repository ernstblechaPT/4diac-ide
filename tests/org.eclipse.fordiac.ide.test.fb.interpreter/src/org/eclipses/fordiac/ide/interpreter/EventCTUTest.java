/*******************************************************************************
 * Copyright (c) 2021 Johannes Kepler University Linz
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garmend�a, Bianca Wiesmayr
 *       - initial implementation and/or documentation
 *******************************************************************************/
package org.eclipses.fordiac.ide.interpreter;


import org.eclipse.fordiac.ide.model.libraryElement.BasicFBType;
import org.eclipse.fordiac.ide.model.libraryElement.ServiceSequence;
import org.junit.Test;

public class EventCTUTest extends AbstractInterpreterTest {

	public EventCTUTest() {
		// do nothing
	}

	@Test
	public void test() {
		final BasicFBType fb = loadFBType("E_CTU"); //$NON-NLS-1$
		ServiceSequence seq = fb.getService().getServiceSequence().get(0);

		setVariable(fb, "PV", "2"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransaction("R", "RO", "Q:=FALSE;CV:=0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addTransaction(seq, new FBTransaction("CU", "CUO", "Q:=FALSE;CV:=1")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addTransaction(seq, new FBTransaction("CU", "CUO", "Q:=TRUE;CV:=2")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		runTest(fb, seq, "START"); //$NON-NLS-1$

		seq = addServiceSequence(fb.getService());
		setVariable(fb, "PV", "0"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransaction("R", "RO", "Q:=FALSE;CV:=0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addTransaction(seq, new FBTransaction("CU", "CUO", "Q:=TRUE;CV:=1")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		addTransaction(seq, new FBTransaction("R", "RO", "Q:=FALSE;CV:=0")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		runTest(fb, seq, "START"); //$NON-NLS-1$

		seq = addServiceSequence(fb.getService());
		setVariable(fb, "PV", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		addTransaction(seq, new FBTransaction("CU", "CUO", "Q:=TRUE;CV:=1")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		runTest(fb, seq, "START"); //$NON-NLS-1$
	}

}