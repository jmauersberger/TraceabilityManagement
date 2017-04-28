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
package org.eclipse.capra.ui.handlers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.adapters.TraceLinkAdapter;
import org.eclipse.capra.core.adapters.TraceMetaModelAdapter;
import org.eclipse.capra.core.adapters.PersistenceAdapter;
import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.capra.core.operations.CreateConnection;
import org.eclipse.capra.core.util.ArtifactWrappingUtil;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.capra.ui.views.SelectionView;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;

public class TraceCreationHandler extends AbstractHandler {
	protected Collection<ArtifactHandler> artifactHandlers = ExtensionPointUtil.getArtifactHandlers();
	protected TraceMetaModelAdapter traceAdapter = ExtensionPointUtil.getTraceMetamodelAdapter().get();
	protected List<TraceLinkAdapter> traceLinkAdapters = ExtensionPointUtil.getTraceLinkAdapters();
	protected PersistenceAdapter persistenceAdapter = ExtensionPointUtil.getTracePersistenceAdapter().get();

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		Shell shell = window.getShell();
		try {
			doExecute(shell);
		} catch (CapraException e) {
			showMessage(shell, "Exception Occurred", e.getLocalizedMessage());
		}
		return null;
	}

	private void doExecute(Shell shell) throws CapraException {
		List<Object> selection = SelectionView.getOpenedView().getSelection();

		List<Object> sources = Collections.singletonList(selection.get(0));
		List<Object> targets = selection.subList(1, selection.size());

		List<TraceLinkAdapter> availableTraceTypes = getAvailableTraceTypes(selection);
		Optional<TraceLinkAdapter> selectedTraceType = selectTraceType(shell, availableTraceTypes);
		if (selectedTraceType.isPresent()) {
			CreateConnection createOperation = new CreateConnection(sources, targets, selectedTraceType.get().getLinkType());
			createOperation.execute();
			
		} else {
			throw new CapraException("No trace type selected.");
		}

	}

	private List<TraceLinkAdapter> getAvailableTraceTypes(List<Object> selection) throws CapraException {
		ResourceSet resourceSet = new ResourceSetImpl();
		EObject artifactWrapperModel = persistenceAdapter.getArtifactWrapperModel(resourceSet);

		List<EObject> selectionAsEObjects = ArtifactWrappingUtil.wrap(selection, artifactHandlers,
				artifactWrapperModel);
		List<EObject> sources = Collections.singletonList(selectionAsEObjects.get(0));
		List<EObject> targets = selectionAsEObjects.subList(1, selectionAsEObjects.size());
		return traceAdapter.getAvailableTraceTypes(sources, targets);
	}

	private Optional<TraceLinkAdapter> selectTraceType(Shell shell, List<TraceLinkAdapter> availableTraceTypes) {

		ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell, new LabelProvider() {
			@Override
			public String getText(Object element) {
				TraceLinkAdapter adapter = (TraceLinkAdapter) element;
				return adapter.getLinkType();
			}
		});
		dialog.setTitle("Select the trace type you want to create");
		dialog.setElements(availableTraceTypes.toArray());
		dialog.setMessage("The following " + traceLinkAdapters.size()
				+ " trace types are available to link the first element in the selection to all other elements.");

		if (dialog.open() == Window.OK) {
			return Optional.of((TraceLinkAdapter) dialog.getFirstResult());
		}

		return Optional.empty();
	}

	private void showMessage(Shell shell, String title, String message) {
		MessageDialog.openError(shell, title, message);
	}
}
