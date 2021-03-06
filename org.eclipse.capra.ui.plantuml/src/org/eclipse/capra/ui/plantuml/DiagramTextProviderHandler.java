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
package org.eclipse.capra.ui.plantuml;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.adapters.PersistenceAdapter;
import org.eclipse.capra.core.adapters.TraceMetaModelAdapter;
import org.eclipse.capra.core.util.ArtifactWrappingUtil;
import org.eclipse.capra.core.util.CapraExceptionUtil;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.capra.ui.plantuml.util.Connection;
import org.eclipse.capra.ui.plantuml.util.SelectionUtil;
import org.eclipse.capra.ui.plantuml.util.TraceToConnectionConverter;
import org.eclipse.capra.ui.plantuml.util.TransitiveTracesUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IWorkbenchPart;

import net.sourceforge.plantuml.eclipse.utils.DiagramTextProvider;

/**
 * Provides PlantUML with a string representation of elements connected by trace
 * links.
 * 
 * @author Anthony Anjorin, Salome Maro
 */
public class DiagramTextProviderHandler implements DiagramTextProvider {
	@Override
	public String getDiagramText(IWorkbenchPart workbenchPart, ISelection input) {
		try {
			ISelectionProvider selectionProvider = workbenchPart.getSite().getSelectionProvider();
			if (selectionProvider != null) {
				List<Object> selection = SelectionUtil.extractSelectedElements(selectionProvider.getSelection());
				ResourceSetImpl resourceSet = new ResourceSetImpl();
				List<EObject> wrap = ArtifactWrappingUtil.wrap(selection, resourceSet);
				return getDiagramText(wrap, resourceSet);
			}
		} catch (CapraException e1) {
			CapraExceptionUtil.handleException(e1, "Cannot create diagram text for PlantUML.");
		} 

		return null;
	}

	/**
	 * Creates the {@code String} representation PlantUML uses to render the
	 * graphics. Based on the selected models, the relevant trace links are
	 * extracted and a decision is made whether a matrix of a tree view has to
	 * be displayed.
	 * 
	 * @param eobjs
	 *            the models whose trace links should be displayed
	 * @return a string representation of the visualisation that can be rendered
	 *         by PlantUML
	 * @throws CapraException
	 */
	private String getDiagramText(List<EObject> eobjs, ResourceSet resourceSet) throws CapraException {
		if (eobjs.isEmpty()) {
			return "";
		}

		List<EObject> rowElements = null;
		List<EObject> columnElements = null;

		PersistenceAdapter persistenceAdapter = ExtensionPointUtil.getTracePersistenceAdapter();
		TraceMetaModelAdapter traceAdapter = ExtensionPointUtil.getTraceMetamodelAdapter();

		EObject traceModel = persistenceAdapter.getTraceModel(resourceSet);
		List<Connection> connections = new ArrayList<>();
		if (eobjs.size() == 1) {
			EObject eobj = eobjs.get(0);

			if (DisplayTracesHandler.isTraceViewTransitive()) {
				List<EObject> transitiveTraces = TransitiveTracesUtil.transitiveTraces(eobj, traceModel, traceAdapter);
				connections.addAll(TraceToConnectionConverter.convert(transitiveTraces, traceAdapter));
			} else {
				List<EObject> tracesFromSource = traceAdapter.getTracesFromSource(eobj, traceModel);
				List<EObject> tracesToTarget = traceAdapter.getTracesToTarget(eobj, traceModel);

				connections.addAll(TraceToConnectionConverter.convert(tracesToTarget, traceAdapter));
				connections.addAll(TraceToConnectionConverter.convert(tracesFromSource, traceAdapter));
			}

			return VisualizationHelper.createNeighboursView(connections, eobj);
		} else {
			rowElements = eobjs;
			columnElements = rowElements;
		}

		String umlString = VisualizationHelper.createMatrix(traceModel, rowElements, columnElements);
		return umlString;
	}

	@Override
	public boolean supportsWorkbenchPart(IWorkbenchPart workbenchPart) {
		return true;
	}

	@Override
	public boolean supportsSelection(ISelection selection) {
		return true;
	}
}
