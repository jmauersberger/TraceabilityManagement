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

package org.eclipse.capra.ui.notification;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.capra.core.adapters.ArtifactMetaModelAdapter;
import org.eclipse.capra.core.adapters.PersistenceAdapter;
import org.eclipse.capra.core.adapters.TraceMetaModelAdapter;
import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.ui.IMarkerResolution;

/**
 * A quick fix to delete a trace link if one of the linked objects is no longer
 * available.
 * 
 * @author Michael Warne
 */
public class DeleteQuickFix implements IMarkerResolution {

	private PersistenceAdapter tracePersistenceAdapter;
	private ResourceSet resourceSet;
	private EObject artifactModel;
	private String label;
	private EObject traceModel;
	private TraceMetaModelAdapter traceModelAdapter;
	private List<EObject> toDelete = new ArrayList<>();

	DeleteQuickFix(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void run(IMarker marker) {
		try {
		Object deletedFile = marker.getAttribute("fileName");
		
				resourceSet = new ResourceSetImpl();
				tracePersistenceAdapter = ExtensionPointUtil.getTracePersistenceAdapter();
				traceModelAdapter = ExtensionPointUtil.getTraceMetamodelAdapter();
				traceModel = tracePersistenceAdapter.getTraceModel(resourceSet);
				artifactModel = tracePersistenceAdapter.getArtifactWrapperModel(resourceSet);
		
				ArtifactMetaModelAdapter artifactAdapter = ExtensionPointUtil.getArtifactWrapperMetaModelAdapter();
		
				List<EObject> wrappedArtifacts = artifactAdapter.getArtifacts(artifactModel);
				int counter = -1;
				for (EObject artifactWrapper : wrappedArtifacts) {
					counter++;
					
					 ArtifactHandler artifactHandler = artifactAdapter.getArtifactHandler(artifactWrapper);
					
					
					
				}
				
				
		} catch (Exception ex){
			
		}
	}

//	@Override
//	public void run(IMarker marker) {
//		Object deletedFile = marker.getAttribute("fileName");
//
//		resourceSet = new ResourceSetImpl();
//		tracePersistenceAdapter = ExtensionPointUtil.getTracePersistenceAdapter().get();
//		traceModelAdapter = ExtensionPointUtil.getTraceMetamodelAdapter().get();
//		traceModel = tracePersistenceAdapter.getTraceModel(resourceSet);
//		artifactModel = tracePersistenceAdapter.getArtifactWrapperModel(resourceSet);
//
//		ArtifactMetaModelAdapter artifactAdapter = ExtensionPointUtil.getArtifactWrapperMetaModelAdapter().get();
//
//		List<EObject> wrappedArtifacts = artifactAdapter.getArtifacts(artifactModel);
//
//		int counter = -1;
//		for (EObject artifactWrapper : wrappedArtifacts) {
//			counter++;
//			try {
//				String artifactHandler = artifactAdapter.getArtifactHandler(artifactWrapper);
//				if (artifactWrapper.getName().equals(marker.getAttribute("fileName"))) {
//					traceModelAdapter.getTracesFromSource(source, artifactWrapper);
//					traceModelAdapter.getTracesToTarget(target, artifactWrapper);
//					
//					List<EObject> connections = traceModelAdapter.getConnectedElements(artifactWrapper, newTraceModel);
//					connections.forEach(c -> {
//						for (RelatedTo t : traces) {
//							if (c.getTlink().equals(t)) {
//								toDelete.add(t);
//							}
//						}
//					});
//					traces.removeAll(toDelete);
//					newTraceModel.getTraces().addAll(traces);
//
//					ArtifactWrapper toRemove = artifactModel.getArtifacts().get(counter);
//					EcoreUtil.delete(toRemove);
//					try {
//						resourceForTraces.save(null);
//						resourceForArtifacts.save(null);
//					} catch (IOException e) {
//
//						e.printStackTrace();
//					}
//
//					break;
//				}
//			} catch (CoreException e) {
//				e.printStackTrace();
//			}
//		}
//
//		try {
//			marker.delete();
//		} catch (CoreException e) {
//			e.printStackTrace();
//		}
//	}
}
