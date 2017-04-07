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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.capra.core.handlers.PriorityHandler;
import org.eclipse.capra.core.helpers.ExtensionPointHelper;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class SelectionView extends ViewPart {

	/** The ID of the view as specified by the extension. */
	public static final String ID = "org.eclipse.capra.generic.views.SelectionView";

	/** The actual table containing selected elements */
	public TableViewer viewer;

	/** The maintained selection of EObjects */
	private List<ObjectWithHandler> selection = new ArrayList<>();

	private class ObjectWithHandler {
		private Object o;
		private ArtifactHandler handler;

		public ObjectWithHandler(Object o, ArtifactHandler handler) {
			this.o = o;
			this.handler = handler;
		}
	}

	private class ViewContentProvider implements IStructuredContentProvider {
		@Override
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(Object parent) {
			return selection.toArray();
		}
	}

	private class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		@Override
		public String getText(Object element) {
			return doGetText((ObjectWithHandler) element);
		};

		@Override
		public String getColumnText(Object element, int index) {
			return doGetText((ObjectWithHandler) element);
		}

		@Override
		public Image getColumnImage(Object element, int index) {
			return doGetImage((ObjectWithHandler) element);
		}

		@Override
		public Image getImage(Object element) {
			return doGetImage((ObjectWithHandler) element);
		}

		private String doGetText(ObjectWithHandler objWitHandler) {
			return objWitHandler.handler.getName(objWitHandler.o);
		}

		private Image doGetImage(ObjectWithHandler objWithHandler) {
			return objWithHandler.handler.getIcon(objWithHandler.o);
		}
	}

	class SelectionDropAdapter extends ViewerDropAdapter {
		TableViewer view;

		public SelectionDropAdapter(TableViewer viewer) {
			super(viewer);
			this.view = viewer;
		}

		@Override
		public boolean performDrop(Object data) {
			dropToSelection(data);
			return true;
		}

		@Override
		public boolean validateDrop(Object target, int operation, TransferData transferType) {
			return true;
		}

	}

	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setInput(getViewSite());

		getSite().setSelectionProvider(viewer);
		hookContextMenu();

		// DND.DROP_MOVE deletes files when dropping files outside of eclipse.
		// Press CTRL for link.
		int ops = DND.DROP_COPY | DND.DROP_LINK;
		Transfer[] transfers = new Transfer[] { org.eclipse.ui.part.ResourceTransfer.getInstance(),
				org.eclipse.ui.part.EditorInputTransfer.getInstance(), org.eclipse.swt.dnd.FileTransfer.getInstance(),
				org.eclipse.swt.dnd.RTFTransfer.getInstance(), org.eclipse.swt.dnd.TextTransfer.getInstance(),
				org.eclipse.swt.dnd.URLTransfer.getInstance(),
				org.eclipse.jface.util.LocalSelectionTransfer.getTransfer(),
				org.eclipse.emf.edit.ui.dnd.LocalTransfer.getInstance() };

		viewer.addDropSupport(ops, transfers, new SelectionDropAdapter(viewer));
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

	public void dropToSelection(Object data) {
		Collection<?> coll = null;
		if (data instanceof TreeSelection) {
			TreeSelection tree = (TreeSelection) data;
			coll = tree.toList();
		} else if (data instanceof Collection<?>) {
			coll = (Collection<?>) data;
		} else {
			coll = Collections.singleton(data);
		}

		for (Object o : coll) {
			ArtifactHandler handler = getHandler(o);
			if (handler != null) {
				ObjectWithHandler objectWithLabel = new ObjectWithHandler(o, handler);
				selection.add(objectWithLabel);
			}
		}

		viewer.refresh();

	}

	private ArtifactHandler getHandler(Object target) {
		Collection<ArtifactHandler> artifactHandlers = ExtensionPointHelper.getArtifactHandlers();
		List<ArtifactHandler> availableHandlers = artifactHandlers.stream()
				.filter(handler -> handler.canHandleSelection(target)).collect(Collectors.toList());
		Optional<PriorityHandler> priorityHandler = ExtensionPointHelper.getPriorityHandler();
		if (availableHandlers.size() == 0) {
			MessageDialog.openWarning(getSite().getShell(), "No handler for selected item",
					"There is no handler for " + target + " so it will be ignored.");
			return null;
		} else if (availableHandlers.size() > 1 && !priorityHandler.isPresent()) {
			MessageDialog.openWarning(getSite().getShell(), "Multiple handlers for selected item",
					"There are multiple handlers for " + target + " so it will be ignored.");
			return null;
		} else if (availableHandlers.size() > 1 && !priorityHandler.isPresent()) {
			return priorityHandler.get().getSelectedHandler(availableHandlers, target);
		} else
			return availableHandlers.get(0);
	}

	public List<Object> getSelection() {
		return selection.stream().map(objectWithHandler -> objectWithHandler.o).collect(Collectors.toList());
	}

	public void clearSelection() {
		selection.clear();
		viewer.refresh();
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
		selection.removeAll(currentselection);
		viewer.refresh();
	}
}
