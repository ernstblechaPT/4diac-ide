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
package org.eclipse.fordiac.ide.fb.interpreter.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FBTransactionBuilder {

	private final String inputEvent;
	private final String inputParameter;
	private final List<String> outputEvent;
	private final List<String> outputParameter;

	public String getInputEvent() {
		return inputEvent;
	}

	public List<String> getOutputEvent() {
		return outputEvent;
	}

	public List<String> getOutputParameter() {
		return outputParameter;
	}

	public FBTransactionBuilder(final String inputEvent, final String inputParameter, final List<String> outputEvent,
			final List<String> outputParameter) {
		this.inputEvent = inputEvent;
		this.inputParameter = inputParameter;
		this.outputEvent = outputEvent;
		this.outputParameter = outputParameter;
	}

	public FBTransactionBuilder(final String inputEvent, final String outputEvent, final String outputParameter) {
		this(inputEvent, null, Arrays.asList(outputEvent), Arrays.asList(outputParameter));
	}

	public FBTransactionBuilder(final String inputEvent) {
		this(inputEvent, null, Collections.emptyList(), Collections.emptyList());
	}

	public FBTransactionBuilder(final String inputEvent, final String outputEvent) {
		this(inputEvent, outputEvent, ""); //$NON-NLS-1$
	}

	public FBTransactionBuilder(final String inputEvent, final List<String> outputEvents) {
		this(inputEvent, null, outputEvents, Collections.emptyList());
	}

	public static FBTransactionBuilder getSimpleFBTransaction(final String parameters) {
		return new FBTransactionBuilder("REQ", "CNF", parameters); //$NON-NLS-1$//$NON-NLS-2$
	}
}