/*******************************************************************************
 * Copyright (c) 2021 Primetals Technologies Austria GmbH
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Sebastian Hollersbacher
 *     - initial API and implementation and/or initial documentation
 *******************************************************************************/

package org.eclipse.fordiac.ide.gef.widgets;

import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

import java.util.function.Consumer;

import org.eclipse.fordiac.ide.model.data.DataType;
import org.eclipse.fordiac.ide.model.data.StructuredType;
import org.eclipse.fordiac.ide.model.libraryElement.AdapterDeclaration;
import org.eclipse.fordiac.ide.model.libraryElement.ConfigurableObject;
import org.eclipse.fordiac.ide.model.libraryElement.Event;
import org.eclipse.fordiac.ide.model.libraryElement.IInterfaceElement;
import org.eclipse.fordiac.ide.model.libraryElement.StructManipulator;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.editors.DataTypeDropdown;
import org.eclipse.fordiac.ide.model.ui.widgets.ITypeSelectionContentProvider;
import org.eclipse.fordiac.ide.model.ui.widgets.OpenStructMenu;

public class TypeSelectionWidget {
	private static final String TYPE = "TYPE"; //$NON-NLS-1$

	private final TabbedPropertySheetWidgetFactory widgetFactory;
	private Consumer<String> handleSelectionChanged;

	private ConfigurableObject configurableObject;
	private ITypeSelectionContentProvider contentProvider;

	private TableViewer tableViewer;
	private Button openEditorButton;

	public TypeSelectionWidget(TabbedPropertySheetWidgetFactory widgetFactory) {
		this.widgetFactory = widgetFactory;
	}

	public TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return widgetFactory;
	}

	public void createControls(final Composite parent) {
		final Composite composite = getWidgetFactory().createComposite(parent);
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		composite.setLayout(gridLayout);
		composite.setLayoutData(new GridData(SWT.FILL, 0, true, false));

		tableViewer = createTableViewer(composite);
		tableViewer.setColumnProperties(new String[] { TYPE });
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setCellModifier(new ICellModifier() {
			@Override
			public void modify(final Object element, final String property, final Object value) {
				if (TYPE.equals(property) && element != null && !((TableItem) element).getData().equals(value)) {
					disableOpenEditorForAnyType();
					handleSelectionChanged.accept(value.toString());
				}
			}

			@Override
			public Object getValue(final Object element, final String property) {
				if (TYPE.equals(property)) {
					return element;
				}
				return "Could not load";
			}

			@Override
			public boolean canModify(final Object element, final String property) {
				return true;
			}
		});

		openEditorButton = new Button(composite, SWT.PUSH);
		openEditorButton.setText(FordiacMessages.OPEN_TYPE_EDITOR_MESSAGE);
		openEditorButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				String typeName = null;
				if (configurableObject instanceof StructManipulator) {
					typeName = ((StructManipulator) configurableObject).getStructType().getName();
				} else if (configurableObject instanceof IInterfaceElement) {
					typeName = ((IInterfaceElement) configurableObject).getType().getName();
				}
				
				if(typeName != null) {
					OpenStructMenu.openStructEditor(getDataType(typeName).getPaletteEntry().getFile());
				}
			}
		});
	}
	
	private DataType getDataType(String dataTypeName) {
		return contentProvider.getTypes().stream().filter(type -> type.getName().equals(dataTypeName)).findAny().orElse(null);
	}

	public void initialize(final ConfigurableObject type, final ITypeSelectionContentProvider contentProvider,
			Consumer<String> handleSelectionChanged) {
		this.configurableObject = type;
		this.contentProvider = contentProvider;
		this.handleSelectionChanged = handleSelectionChanged;

		if (type instanceof StructManipulator) {
			resizeTextField();
			tableViewer.setInput(new String[] { ((StructManipulator) configurableObject).getStructType().getName() });
		} else if (type instanceof IInterfaceElement) {
			tableViewer.setInput(new String[] { ((IInterfaceElement) configurableObject).getType().getName() });
		}

		disableOpenEditorForAnyType();
		tableViewer.setCellEditors(createCellEditors());
	}

	public void refresh() {
		if (configurableObject instanceof IInterfaceElement) {
			tableViewer.setInput(new String[] { ((IInterfaceElement) configurableObject).getType().getName() });
		}
		disableOpenEditorForAnyType();
	}

	private CellEditor[] createCellEditors() {
		return new CellEditor[] { new DataTypeDropdown(contentProvider, tableViewer) };
	}

	private TableViewer createTableViewer(final Composite parent) {
		final TableViewer viewer = new TableViewer(parent, SWT.NO_SCROLL | SWT.BORDER);

		final Table table = viewer.getTable();
		TableLayout tableLayout = new TableLayout();
		tableLayout.addColumnData(new ColumnWeightData(100));
		table.setLayout(tableLayout);
		table.setLayoutData(new GridData(SWT.FILL, 0, true, false));
		table.setLinesVisible(false);
		table.setHeaderVisible(false);
		new TableColumn(table, SWT.NONE);

		return viewer;
	}

	private void resizeTextField() {
		final Table table = tableViewer.getTable();
		table.getColumn(0).dispose();
		table.setLayout(new TableLayout());
		table.setLayoutData(new GridData(SWT.FILL, 0, false, false));
		new TableColumn(table, SWT.NONE).setWidth(150);
	}

	private void disableOpenEditorForAnyType() {
		if (configurableObject instanceof StructManipulator) {
			openEditorButton.setEnabled(
					!"ANY_STRUCT".contentEquals(((StructManipulator) configurableObject).getStructType().getName()));	//$NON-NLS-1$
		} else if (configurableObject instanceof Event || configurableObject instanceof AdapterDeclaration) {
			// reset parent composite and dispose button
			final Composite typeComp = tableViewer.getTable().getParent();
			GridLayout gridLayout = new GridLayout(1, false);
			gridLayout.marginWidth = 0;
			gridLayout.marginHeight = 0;
			typeComp.setLayout(gridLayout);
			typeComp.setLayoutData(new GridData(SWT.FILL, 0, true, false));
			openEditorButton.dispose();
		} else if (configurableObject instanceof VarDeclaration) {
			openEditorButton.setEnabled(((VarDeclaration) configurableObject).getType() instanceof StructuredType
					&& !"ANY_STRUCT".contentEquals(((VarDeclaration) configurableObject).getTypeName()));		//$NON-NLS-1$
		}
	}
}