/*******************************************************************************
 * Copyright (c) 2016 Chalmers | University of Gothenburg, rt-labs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 *   Contributors:
 *      Chalmers | University of Gothenburg and rt-labs - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.capra.ui.views;

import java.util.List;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.util.CapraExceptionUtil;
import org.eclipse.capra.ui.linkview.views.CapraTableViewer;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class SelectionView extends ViewPart {

	/** The ID of the view as specified by the extension. */
	public static final String ID = "org.eclipse.capra.generic.views.SelectionView";

	/** The actual table containing selected elements */
	public CapraTableViewer viewer;

	/** The maintained selection of EObjects */

	public void createPartControl(Composite parent) {
		viewer = new CapraTableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);

		getSite().setSelectionProvider(viewer);
		hookContextMenu();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {

			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	public void setFocus() {
		viewer.getControl().setFocus();
	}

	public List<Object> getSelection() {
		return viewer.getObjects();
	}

	public void clearSelection() {
		viewer.clearSelection();
	}

	public static SelectionView getOpenedView() {
		try {
			return (SelectionView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void removeFromSelection(List<Object> currentselection) {
		viewer.removeFromSelection(currentselection);
	}

	public void dropToSelection(List<Object> selection) {
		try {
			viewer.addToSelection(selection);
		} catch (CapraException e) {
			CapraExceptionUtil.handleException(e, "Error while Dropping");
		}
	}
}
