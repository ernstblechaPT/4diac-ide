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
 *   Bianca Wiesmayr - add table design, context menu
 *******************************************************************************/
package org.eclipse.fordiac.ide.application.search;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.fordiac.ide.model.helpers.FBNetworkHelper;
import org.eclipse.fordiac.ide.model.libraryElement.FBNetworkElement;
import org.eclipse.fordiac.ide.model.libraryElement.FBType;
import org.eclipse.fordiac.ide.model.libraryElement.INamedElement;
import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.libraryElement.SubApp;
import org.eclipse.fordiac.ide.model.libraryElement.TypedConfigureableObject;
import org.eclipse.fordiac.ide.model.libraryElement.VarDeclaration;
import org.eclipse.fordiac.ide.model.ui.actions.OpenListenerManager;
import org.eclipse.fordiac.ide.model.ui.editors.HandlerHelper;
import org.eclipse.fordiac.ide.ui.FordiacMessages;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnPixelData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.search.ui.IContextMenuConstants;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.search.ui.text.AbstractTextSearchViewPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IMemento;

public class ModelSearchResultPage extends AbstractTextSearchViewPage {

	private static final String ID = "org.eclipse.fordiac.ide.application.search.ModelSearchResultPage"; //$NON-NLS-1$
	private ModelSearchTableContentProvider contentProvider;
	private String searchDescription;

	private static final int TYPE_COLUMN_WIDTH = 100;
	private static final int NAME_COMMENT_COLUMN_WIDTH = 200;
	private static final int FULL_HIERARCHICAL_NAME_COLUMN_WIDTH = 300;

	private static final String FULL_NAME_COLUMN = "Full Hierarchical Name";

	public ModelSearchResultPage() {
		super(AbstractTextSearchViewPage.FLAG_LAYOUT_FLAT); // FLAG_LAYOUT_FLAT = table layout
	}

	@Override
	public void restoreState(final IMemento memento) {
		// Nothing to do here yet
	}

	@Override
	public void saveState(final IMemento memento) {
		// Nothing to do here yet
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public String getLabel() {
		return null != searchDescription ? searchDescription : "IEC 61499 Model Search";
	}

	@Override
	protected void elementsChanged(final Object[] objects) {
		if (contentProvider != null) {
			contentProvider.elementsChanged(objects);
		}
	}

	@Override
	protected void clear() {
		if (contentProvider != null) {
			contentProvider.clear();
		}
	}

	@Override
	public void setInput(final ISearchResult newSearch, final Object viewState) {
		super.setInput(newSearch, viewState);
		if (newSearch != null) {
			this.searchDescription = newSearch.getLabel();
		}
	}

	@Override
	protected void configureTreeViewer(final TreeViewer viewer) {
		throw new IllegalStateException("Doesn't support tree mode."); //$NON-NLS-1$
	}

	// This method is called if the page was constructed with the flag FLAG_LAYOUT_FLAT (see constructor)
	@Override
	protected void configureTableViewer(final TableViewer viewer) {
		contentProvider = new ModelSearchTableContentProvider(this);
		viewer.setContentProvider(contentProvider);

		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setLayout(createTableLayout(table));

		final TableViewerColumn colKind = new TableViewerColumn(viewer, SWT.LEAD);
		colKind.getColumn().setText("Element Kind");
		colKind.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				final String kind = element.getClass().getSimpleName();
				return kind.substring(0, kind.length() - 4);
			}
		});

		final TableViewerColumn colName = new TableViewerColumn(viewer, SWT.LEAD);
		colName.getColumn().setText(FordiacMessages.Name);

		colName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof INamedElement) {
					return ((INamedElement) element).getName();
				}
				return super.getText(element);
			}
		});
		final TableViewerColumn colComment = new TableViewerColumn(viewer, SWT.LEAD);
		colComment.getColumn().setText(FordiacMessages.Comment);
		colComment.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof INamedElement) {
					return ((INamedElement) element).getComment();
				}
				return super.getText(element);
			}
		});
		final TableViewerColumn colType = new TableViewerColumn(viewer, SWT.LEAD);
		colType.getColumn().setText(FordiacMessages.Type);

		colType.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof FBType) {
					return ((FBType) element).getName();
				}
				if (element instanceof TypedConfigureableObject) {
					final LibraryElement type = ((TypedConfigureableObject) element).getType();
					return type != null ? type.getName() : "untyped";
				}
				if (element instanceof VarDeclaration) {
					final LibraryElement type = ((VarDeclaration) element).getType();
					return type != null ? type.getName() : "unknown";
				}
				return super.getText(element);
			}
		});

		final TableViewerColumn fullHierarchicalName = new TableViewerColumn(viewer, SWT.LEAD);
		fullHierarchicalName.getColumn().setText(FULL_NAME_COLUMN);
		fullHierarchicalName.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(final Object element) {
				if (element instanceof FBNetworkElement) {
					return FBNetworkHelper.getFullHierarchicalName((FBNetworkElement) element);
				}
				return super.getText(element);
			}

		});


		viewer.addDoubleClickListener(ModelSearchResultPage::jumpToBlock);
	}

	// Double click to access the element we looked for
	private static void jumpToBlock(final DoubleClickEvent doubleClick) {
		final StructuredSelection selectionList = (StructuredSelection) doubleClick.getSelection();
		if (!selectionList.isEmpty()) {
			final Object selection = selectionList.getFirstElement();
			if (selection instanceof FBNetworkElement) {
				final FBNetworkElement fbSelection = (FBNetworkElement) selection;
				final IEditorPart editor = OpenListenerManager.openEditor(fbSelection.eContainer().eContainer());
				HandlerHelper.selectElement(selection, HandlerHelper.getViewer(editor));
			}

		}

	}

	protected static TableLayout createTableLayout(final Table table) {
		final TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnPixelData(TYPE_COLUMN_WIDTH));
		layout.addColumnData(new ColumnPixelData(NAME_COMMENT_COLUMN_WIDTH));
		layout.addColumnData(new ColumnPixelData(NAME_COMMENT_COLUMN_WIDTH));
		layout.addColumnData(new ColumnPixelData(TYPE_COLUMN_WIDTH));
		layout.addColumnData(new ColumnPixelData(FULL_HIERARCHICAL_NAME_COLUMN_WIDTH));
		return layout;
	}

	// override to increase visibility of this method
	@Override
	public StructuredViewer getViewer() {
		return super.getViewer();
	}

	public static void showResult(final EObject obj) {
		EObject toOpen = obj;
		if (obj instanceof VarDeclaration) {
			toOpen = ((VarDeclaration) obj).getFBNetworkElement();
		}
		if (obj instanceof SubApp) {
			toOpen = ((SubApp) toOpen).getOuterFBNetworkElement();
		}
		if (toOpen instanceof FBNetworkElement) {
			final IEditorPart p = HandlerHelper.openParentEditor((FBNetworkElement) toOpen);
			HandlerHelper.selectElement(obj, p);
		}
		HandlerHelper.openEditor(obj);
	}

	@Override
	protected void fillContextMenu(final IMenuManager mgr) {
		final Action showInEditor = new ShowInEditorAction(this);
		mgr.prependToGroup(IContextMenuConstants.GROUP_SHOW, showInEditor);
		super.fillContextMenu(mgr);
	}

	private static class ShowInEditorAction extends Action {
		private final ModelSearchResultPage fPage;

		public ShowInEditorAction(final AbstractTextSearchViewPage page) {
			super("Show in Editor");
			setToolTipText("Shows element in the editor");
			fPage = (ModelSearchResultPage) page;
		}

		@Override
		public void run() {
			showResult((EObject) fPage.getViewer().getStructuredSelection().getFirstElement());
		}
	}
}
