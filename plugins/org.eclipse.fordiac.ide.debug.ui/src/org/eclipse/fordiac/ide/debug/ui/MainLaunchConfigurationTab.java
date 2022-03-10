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
package org.eclipse.fordiac.ide.debug.ui;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.fordiac.ide.debug.LaunchConfigurationAttributes;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public abstract class MainLaunchConfigurationTab extends AbstractLaunchConfigurationTab {
	private Text resourceText;

	@Override
	public void createControl(final Composite parent) {
		final Composite comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(comp);
		setControl(comp);

		final Composite resourceComponent = createResourceComponent(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(resourceComponent);
	}

	protected Composite createResourceComponent(final Composite parent) {
		final Group group = new Group(parent, SWT.BORDER);
		GridLayoutFactory.swtDefaults().applyTo(group);
		group.setText("Target");

		final Composite comp = new Composite(group, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(3).applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(comp);

		final Label resourceLabel = new Label(comp, SWT.NONE);
		resourceLabel.setText("Location:");
		GridDataFactory.swtDefaults().applyTo(resourceLabel);

		resourceText = new Text(comp, SWT.BORDER);
		resourceText.setEnabled(false);
		resourceText.setMessage("Location");
		resourceText.addModifyListener(e -> scheduleUpdateJob());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(resourceText);

		final Button resourceButton = new Button(comp, SWT.BORDER);
		resourceButton.setText("Browse...");
		resourceButton.addSelectionListener(widgetSelectedAdapter(e -> handleResourceButtonSelected()));
		GridDataFactory.swtDefaults().applyTo(resourceButton);
		return group;
	}

	private void handleResourceButtonSelected() {
		IResource resource = getResource();
		final ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(getShell(), new WorkbenchLabelProvider(),
				new WorkbenchContentProvider());
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		if (resource != null) {
			dialog.setInitialSelection(resource);
		}
		dialog.setAllowMultiple(false);
		dialog.addFilter(new ViewerFilter() {

			@Override
			public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
				final IResource resource = Adapters.adapt(element, IResource.class);
				try {
					return filterTargetResource(resource);
				} catch (final CoreException e) {
				}
				return false;
			}
		});
		dialog.open();
		final Object[] result = dialog.getResult();
		if (result != null && result.length > 0 && result[0] instanceof IResource) {
			resource = (IResource) result[0];
			final String resourceString = resource.getFullPath().toString();
			resourceText.setText(resourceString);
			handleResourceUpdated();
		}
	}

	@Override
	public void setDefaults(final ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(LaunchConfigurationAttributes.RESOURCE, "");
	}

	@Override
	public void initializeFrom(final ILaunchConfiguration configuration) {
		try {
			final String resourceAttribute = configuration.getAttribute(LaunchConfigurationAttributes.RESOURCE, "");
			resourceText.setText(resourceAttribute);
		} catch (final CoreException e) {
		}
	}

	@Override
	public void performApply(final ILaunchConfigurationWorkingCopy configuration) {
		final String resourceString = resourceText.getText();
		configuration.setAttribute(LaunchConfigurationAttributes.RESOURCE, resourceString);
	}

	protected abstract void handleResourceUpdated();

	protected abstract boolean filterTargetResource(IResource resource) throws CoreException;

	@Override
	public String getName() {
		return "Main";
	}

	protected IResource getResource() {
		final String resourceString = resourceText.getText();
		if (resourceString != null && !resourceString.isEmpty()) {
			return ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(resourceString));
		}
		return null;
	}
}