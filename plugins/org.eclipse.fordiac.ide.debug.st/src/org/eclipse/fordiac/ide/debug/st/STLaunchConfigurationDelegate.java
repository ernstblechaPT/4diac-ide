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
package org.eclipse.fordiac.ide.debug.st;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.emf.common.util.URI;
import org.eclipse.fordiac.ide.debug.EvaluatorDebugTarget;
import org.eclipse.fordiac.ide.debug.EvaluatorProcess;
import org.eclipse.fordiac.ide.debug.LaunchConfigurationAttributes;
import org.eclipse.fordiac.ide.model.eval.Evaluator;
import org.eclipse.fordiac.ide.model.eval.st.STFunctionEvaluator;
import org.eclipse.fordiac.ide.model.eval.st.variable.STVariableOperations;
import org.eclipse.fordiac.ide.model.eval.variable.Variable;
import org.eclipse.fordiac.ide.structuredtextcore.stcore.STVarDeclaration;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunction;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.stfunction.STFunctionSource;
import org.eclipse.fordiac.ide.structuredtextfunctioneditor.util.STFunctionParseUtil;

public class STLaunchConfigurationDelegate extends LaunchConfigurationDelegate {

	@Override
	public void launch(final ILaunchConfiguration configuration, final String mode, final ILaunch launch,
			final IProgressMonitor monitor) throws CoreException {
		final IResource resource = LaunchConfigurationAttributes.getResource(configuration);
		if (resource instanceof IFile) {
			final var errors = new ArrayList<String>();
			final STFunctionSource source = STFunctionParseUtil
					.parse(URI.createPlatformResourceURI(resource.getFullPath().toString(), true), errors);
			if (source == null) {
				throw new CoreException(Status.error(errors.stream().collect(Collectors.joining("\n")))); //$NON-NLS-1$
			}
			final var function = STLaunchConfigurationAttributes.getFunction(configuration, source, null);
			if (function == null) {
				throw new CoreException(
						Status.error(String.format("Cannot find function \"%s\" in source %s", configuration //$NON-NLS-1$
								.getAttribute(STLaunchConfigurationAttributes.FUNCTION, ""), resource.getName()))); //$NON-NLS-1$
			}
			final var defaultArguments = getDefaultArguments(function);
			final var variables = LaunchConfigurationAttributes.getArguments(configuration, defaultArguments);
			launch(function, variables, configuration, mode, launch, monitor);
		}
	}

	public void launch(final STFunction function, final List<Variable> variables,
			final ILaunchConfiguration configuration, final String mode, final ILaunch launch,
			final IProgressMonitor monitor) throws CoreException {
		final Evaluator evaluator = createEvaluator(function, variables);
		if (ILaunchManager.RUN_MODE.equals(mode)) {
			final EvaluatorProcess process = new EvaluatorProcess(configuration.getName(), evaluator, launch);
			process.start();
		} else if (ILaunchManager.DEBUG_MODE.equals(mode)) {
			final EvaluatorDebugTarget debugTarget = new EvaluatorDebugTarget(configuration.getName(), evaluator,
					launch);
			debugTarget.start();
		} else {
			throw new CoreException(Status.error("Illegal launch mode: " + mode)); //$NON-NLS-1$
		}
	}

	@SuppressWarnings("static-method")
	public Evaluator createEvaluator(final STFunction function, final List<Variable> variables) {
		return new STFunctionEvaluator(function, variables, null);
	}

	@SuppressWarnings("static-method")
	protected List<Variable> getDefaultArguments(final STFunction function) {
		return function.getInputParameters().stream().map(STVarDeclaration.class::cast)
				.map(STVariableOperations::newVariable).collect(Collectors.toList());
	}
}