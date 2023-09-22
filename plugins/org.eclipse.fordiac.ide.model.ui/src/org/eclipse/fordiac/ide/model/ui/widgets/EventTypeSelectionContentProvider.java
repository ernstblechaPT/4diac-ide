/*******************************************************************************
 * Copyright (c) 2023 Martin Erich Jobst
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
package org.eclipse.fordiac.ide.model.ui.widgets;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.fordiac.ide.model.libraryElement.LibraryElement;
import org.eclipse.fordiac.ide.model.typelibrary.EventTypeLibrary;
import org.eclipse.fordiac.ide.model.typelibrary.TypeEntry;

public class EventTypeSelectionContentProvider implements ITypeSelectionContentProvider {

	public static final EventTypeSelectionContentProvider INSTANCE = new EventTypeSelectionContentProvider();

	protected EventTypeSelectionContentProvider() {
	}

	@Override
	public Collection<LibraryElement> getTypes(final Object input) {
		return Collections.unmodifiableCollection(EventTypeLibrary.getInstance().getEventTypes());
	}

	@Override
	public Collection<TypeEntry> getTypeEntries(final Object input) {
		return Collections.emptyList();
	}
}
