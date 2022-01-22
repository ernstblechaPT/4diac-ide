/*******************************************************************************
 * Copyright (c) 2022 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Dunja Životin - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.search;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.widgets.LabelFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.search.ui.ISearchPage;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ModelSearchPage extends DialogPage implements ISearchPage {

	public static final String EXTENSION_POINT_ID = "org.eclipse.fordiac.ide.application.search.ModelSearchPage"; //$NON-NLS-1$
	public static final String ID = "ModelSearchPage"; //$NON-NLS-1$

	private ISearchPageContainer container;
	private Label label;
	private Button instanceName;
	private Button pinName;
	private Button type;
	private Button comment;
	private Text query;
	private Composite composite;


	public Label getLabel() {
		return label;
	}

	public Button getInstanceName() {
		return instanceName;
	}

	public Button getPinName() {
		return pinName;
	}

	public Button getType() {
		return type;
	}

	public Button getComment() {
		return comment;
	}

	public ISearchPageContainer getContainer() {
		return container;
	}

	@Override
	public void createControl(final Composite parent) {

		GridLayoutFactory.fillDefaults().numColumns(1).margins(LayoutConstants.getMargins()).generateLayout(parent);

		// Factory for the big composite
		composite = WidgetFactory.composite(NONE).create(parent);
		GridLayoutFactory.fillDefaults().numColumns(1).generateLayout(composite);

		final LabelFactory labelFactory = LabelFactory.newLabel(SWT.NONE);
		labelFactory.text("Search for: ").create(composite); //$NON-NLS-1$

		// Factory for the checkbox composite (to have them side by side)
		final Composite checkboxComposite = WidgetFactory.composite(NONE).create(composite);
		GridLayoutFactory.fillDefaults().numColumns(4).generateLayout(checkboxComposite);

		instanceName = WidgetFactory.button(SWT.CHECK).text("Instance Name").create(checkboxComposite); //$NON-NLS-1$
		pinName = WidgetFactory.button(SWT.CHECK).text("Pin Name").create(checkboxComposite); //$NON-NLS-1$
		type = WidgetFactory.button(SWT.CHECK).text("Type").create(checkboxComposite); //$NON-NLS-1$
		comment = WidgetFactory.button(SWT.CHECK).text("Comment").create(checkboxComposite); //$NON-NLS-1$

		instanceName.setSelection(true);
		pinName.setSelection(true);
		type.setSelection(true);
		comment.setSelection(true);

		// Text box for the actual search
		labelFactory.text("Name:").create(composite); //$NON-NLS-1$
		query = WidgetFactory.text(SWT.BORDER).message("Type the query").create(composite); //$NON-NLS-1$

		setControl(composite);
	}

	@Override
	public boolean performAction() {
		// What type is the user searching for
		final boolean isCheckedInstanceName = instanceName.getSelection();
		final boolean isCheckedPinName = pinName.getSelection();
		final boolean isCheckedType = type.getSelection();
		final boolean isCheckedComment = comment.getSelection();

		// Search string aka the name of it
		final String searchString = query.getText();

		// Check to see if at least one of the check boxes is ticked and if the search string exists
		if (!searchString.equals("")
				&& (isCheckedInstanceName || isCheckedPinName || isCheckedType || isCheckedComment)) {

			final ModelQuerySpec modelQuerySpec = new ModelQuerySpec(searchString, isCheckedInstanceName,
					isCheckedPinName, isCheckedType, isCheckedComment);

			final ModelSearchQuery searchJob = new ModelSearchQuery(modelQuerySpec);
			NewSearchUI.runQueryInBackground(searchJob); // add ISearchResultViewPart view as a second param

			// return true; -> closes the page
			return true;

		}
		// return false; -> the dialog remains open so the user can fix the search parameters
		errorDialogDisplay();
		return false;

	}

	private void errorDialogDisplay() {
		MessageDialog.openWarning(getShell(), "Warning",
				"The search box is empty or none of the checkboxes are ticked! Please correct that and try to search again.");
	}

	@Override
	public void setContainer(final ISearchPageContainer container) {
		this.container = container;
	}

}