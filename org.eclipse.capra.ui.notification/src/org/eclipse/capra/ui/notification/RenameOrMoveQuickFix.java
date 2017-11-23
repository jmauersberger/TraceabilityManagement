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

import java.io.IOException;
import java.util.List;

import org.eclipse.capra.core.adapters.ArtifactMetaModelAdapter;
import org.eclipse.capra.core.adapters.PersistenceAdapter;
import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ui.IMarkerResolution;

/**
 * Renames the associated artifact wrapper in the artifact model to reflect
 * changes in the original resource represented by the wrapper. If the original
 * resource has been renamed or moved, the respective wrapper if renamed to
 * reflect the new name/location of file.
 * 
 * @author Michael Warne
 */
public class RenameOrMoveQuickFix implements IMarkerResolution {

	private String label;

	RenameOrMoveQuickFix(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void run(IMarker marker) {
		URI uri;
		PersistenceAdapter tracePersistenceAdapter;
		ArtifactMetaModelAdapter artifactAdapter;
		ResourceSet resourceSet;
		EObject artifactModel;
		Resource resource = null;
		EObject traceModel;

		String movedToPath = null;
		String oldFileName = null;
		String newFileName = null;

		try {
			movedToPath = (String) marker.getAttribute("DeltaMovedToPath");
			oldFileName = (String) marker.getAttribute("oldFileName");
			newFileName = (String) marker.getAttribute("newFileName");
		} catch (CoreException e) {
			e.printStackTrace();
		}
		try {
			resourceSet = new ResourceSetImpl();
			tracePersistenceAdapter = ExtensionPointUtil.getTracePersistenceAdapter();
			artifactModel = tracePersistenceAdapter.getArtifactWrapperModel(resourceSet);
			artifactAdapter = ExtensionPointUtil.getArtifactWrapperMetaModelAdapter();
			traceModel = tracePersistenceAdapter.getTraceModel(resourceSet);
			uri = EcoreUtil.getURI(artifactModel);
			resource = resourceSet.createResource(uri);

			List<EObject> list = artifactAdapter.getArtifacts(artifactModel);
			for (EObject artifact : list) {
				ArtifactHandler artifactHandler = artifactAdapter.getArtifactHandler(artifact);
				String artifactName = artifactHandler.getName(artifact);
				if (artifactName.contentEquals(oldFileName)) {
					// TODO: No functionality implemented
				}

				// if (aw.getName().equals(oldFileName)) {
				// aw.setName(newFileName);
				// aw.setUri(movedToPath);
				// }
			}

			resource.getContents().add(traceModel);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		try {
			resource.save(null);
		} catch (IOException e) {

			e.printStackTrace();
		}
		try {
			marker.delete();
		} catch (CoreException e) {

			e.printStackTrace();
		}
	}
}
