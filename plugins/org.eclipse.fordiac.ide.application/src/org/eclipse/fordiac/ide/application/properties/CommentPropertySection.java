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
package org.eclipse.fordiac.ide.application.properties;

import org.eclipse.fordiac.ide.gef.properties.AbstractSection;
import org.eclipse.fordiac.ide.model.commands.change.ChangeCommentCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeNameCommand;
import org.eclipse.fordiac.ide.model.commands.change.ChangeValueCommand;
import org.eclipse.fordiac.ide.model.edit.providers.CommentLabelProvider;
import org.eclipse.fordiac.ide.model.edit.providers.InitialValueLabelProvider;
import org.eclipse.fordiac.ide.model.edit.providers.NameLabelProvider;
import org.eclipse.fordiac.ide.model.edit.providers.TypeLabelProvider;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.fordiac.ide.ui.widget.TableWidgetFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class CommentPropertySection extends AbstractSection {

	private static final int ONE_COLUMN = 1;
	private static final int TWO_COLUMNS = 2;
	private static final int WIDTH_OF_COLUMNS = 150;
	private static final String INITIAL_VALUE = "Initial Value"; //$NON-NLS-1$
	private static final String COMMENT = "Comment"; //$NON-NLS-1$

	private Text nameText;
	private Text commentText;

	private TableViewer inputCommentsViewer;
	private TableViewer outputCommentsViewer;
	private TabbedPropertySheetPage tabbedPropertySheetPage;

	@Override
	public void createControls(final Composite parent, final TabbedPropertySheetPage tabbedPropertySheetPage) {
		this.tabbedPropertySheetPage = tabbedPropertySheetPage;
		setLeftComposite(parent);
		parent.setLayout(new GridLayout(ONE_COLUMN, true));
		parent.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		createFBInfoGroup(parent);
		createTableSection(parent);
		configureTableViewer(inputCommentsViewer, new InputViewerContentProvider());
		configureTableViewer(outputCommentsViewer, new OutputViewerContentProvider());
	}

	@Override
	public void refresh() {
		if (getType() != null && !nameText.isDisposed() && !nameText.getParent().isDisposed()) {
			final CommandStack commandStackBuffer = commandStack;
			commandStack = null;
			nameText.setText(getType().getName() != null ? getType().getName() : ""); //$NON-NLS-1$
			commentText.setText(getType().getComment() != null ? getType().getComment() : ""); //$NON-NLS-1$
			inputCommentsViewer.setInput(getType());
			outputCommentsViewer.setInput(getType());
			commandStack = commandStackBuffer;
		}
	}

	private void configureTableViewer(final TableViewer tableViewer,
			final IStructuredContentProvider contentProvider) {
		final Table table = tableViewer.getTable();

		final CellModifierForCommentSection modifier = new CellModifierForCommentSection();
		tableViewer.setCellModifier(modifier);

		final String[] cols = new String[] { FordiacMessages.Name, FordiacMessages.Type, FordiacMessages.InitialValue,
				FordiacMessages.Comment };
		tableViewer.setColumnProperties(cols);
		tableViewer
		.setCellEditors(new CellEditor[] { null, null,
				new TextCellEditor(table), new TextCellEditor(table) });
		tableViewer.setContentProvider(contentProvider);

		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayout(createTableLayout());

		final TableViewerColumn col1 = new TableViewerColumn(tableViewer, SWT.NONE);
		col1.setLabelProvider(new NameLabelProvider());
		col1.getColumn().setText(FordiacMessages.Name);

		final TableViewerColumn col2 = new TableViewerColumn(tableViewer, SWT.NONE);
		col2.setLabelProvider(new TypeLabelProvider());
		col2.getColumn().setText(FordiacMessages.Type);

		final TableViewerColumn col3 = new TableViewerColumn(tableViewer, SWT.NONE);
		col3.setLabelProvider(new InitialValueLabelProvider());
		col3.getColumn().setText(FordiacMessages.InitialValue);

		final TableViewerColumn col4 = new TableViewerColumn(tableViewer, SWT.NONE);
		col4.setLabelProvider(new CommentLabelProvider());
		col4.getColumn().setText(FordiacMessages.Comment);
	}

	private static TableLayout createTableLayout() {
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(WIDTH_OF_COLUMNS));
		layout.addColumnData(new ColumnPixelData(WIDTH_OF_COLUMNS));
		layout.addColumnData(new ColumnPixelData(WIDTH_OF_COLUMNS));
		layout.addColumnData(new ColumnPixelData(WIDTH_OF_COLUMNS));
		return layout;
	}

	private void createTableSection(final Composite parent) {
		final Composite tableSectionComposite = getWidgetFactory().createComposite(parent);
		tableSectionComposite.setLayout(new GridLayout(TWO_COLUMNS, false));
		tableSectionComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Group inputComposite = new Group(tableSectionComposite, 0);
		final Group outputComposite = new Group(tableSectionComposite, 0);

		inputComposite.setText(FordiacMessages.Inputs);
		outputComposite.setText(FordiacMessages.Outputs);

		inputComposite.setLayout(new GridLayout(ONE_COLUMN, false));
		outputComposite.setLayout(new GridLayout(ONE_COLUMN, false));

		inputCommentsViewer = TableWidgetFactory.createPropertyTableViewer(inputComposite, 0);
		outputCommentsViewer = TableWidgetFactory.createPropertyTableViewer(outputComposite, 0);

		inputComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		outputComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		tableSectionComposite.layout();
	}

	@Override
	public TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return tabbedPropertySheetPage.getWidgetFactory();
	}

	protected void createFBInfoGroup(final Composite parent) {
		final Group fbInfoGroup = new Group(parent, 0);
		fbInfoGroup.setText(FordiacMessages.InstanceInfo);
		fbInfoGroup.setLayout(new GridLayout(TWO_COLUMNS, false));
		final GridData gridData = new GridData(GridData.FILL, 0, true, false);
		fbInfoGroup.setLayoutData(gridData);
		getWidgetFactory().createCLabel(fbInfoGroup, FordiacMessages.Name + ":"); //$NON-NLS-1$
		nameText = createGroupText(fbInfoGroup, true);
		nameText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeNameCommand(getType(), nameText.getText()));
			addContentAdapter();
		});
		getWidgetFactory().createCLabel(fbInfoGroup, FordiacMessages.Comment + ":"); //$NON-NLS-1$
		commentText = createGroupText(fbInfoGroup, true);
		commentText.addModifyListener(e -> {
			removeContentAdapter();
			executeCommand(new ChangeCommentCommand(getType(), commentText.getText()));
			addContentAdapter();
		});
	}


	@Override
	protected Object getInputType(final Object input) {
		if (input instanceof EditPart) {
			return ((EditPart) input).getModel();
		} else if (input instanceof FBNetworkElement) {
			return input;
		}
		return null;
	}

	@Override
	protected INamedElement getType() {
		if (type instanceof FBNetworkElement) {
			return (FBNetworkElement) type;
		}
		return null;
	}

	@Override
	protected void setInputCode() {
		// Nothing for now
	}

	@Override
	protected void setInputInit() {
		// Nothing for now

	}

	private class CellModifierForCommentSection implements ICellModifier {

		// Can modify if it's an initial value or a comment
		@Override
		public boolean canModify(final Object element, final String property) {
			return FordiacMessages.InitialValue.equals(property) || FordiacMessages.Comment.equals(property);
		}

		@Override
		public Object getValue(final Object element, final String property) {
			if (element instanceof VarDeclaration) {
				final VarDeclaration varDec = (VarDeclaration) element;
				switch (property) {
				case INITIAL_VALUE:
					if (varDec.getValue() != null) {
						return varDec.getValue().getValue();
					}
					return ""; //$NON-NLS-1$
				case COMMENT:
					return varDec.getComment();
				default:
					break;
				}
			}
			return null;
		}

		@Override
		public void modify(Object element, final String property, final Object value) {
			Command cmd = null;
			if (element instanceof Item) {
				element = ((Item) element).getData();
				if (element instanceof VarDeclaration) {
					final VarDeclaration varDec = (VarDeclaration) element;
					switch (property) {
					case INITIAL_VALUE:
						cmd = new ChangeValueCommand(varDec, (String) value);
						break;
					case COMMENT:
						cmd = new ChangeCommentCommand(varDec, (String) value);
						break;
					default:
						break;
					}
					executeCommand(cmd);
					refresh();
				}
			}
		}

	}

	private static class InputViewerContentProvider implements IStructuredContentProvider {

		private static final Object[] EMPTY_ARR = new Object[0];

		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof FBNetworkElement) {
				final FBNetworkElement fbNetworkElem = (FBNetworkElement) inputElement;
				return fbNetworkElem.getInterface().getInputVars().toArray();
			}
			return EMPTY_ARR;
		}

	}

	private static class OutputViewerContentProvider implements IStructuredContentProvider {

		private static final Object[] EMPTY_ARR = new Object[0];

		@Override
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof FBNetworkElement) {
				final FBNetworkElement fbNetworkElem = (FBNetworkElement) inputElement;
				return fbNetworkElem.getInterface().getOutputVars().toArray();
			}
			return EMPTY_ARR;
		}

	}


}