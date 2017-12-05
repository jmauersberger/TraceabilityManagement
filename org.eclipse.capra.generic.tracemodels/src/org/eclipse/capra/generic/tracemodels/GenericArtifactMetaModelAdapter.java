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
package org.eclipse.capra.generic.tracemodels;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.capra.GenericArtifactMetaModel.ArtifactWrapper;
import org.eclipse.capra.GenericArtifactMetaModel.ArtifactWrapperContainer;
import org.eclipse.capra.GenericArtifactMetaModel.GenericArtifactMetaModelFactory;
import org.eclipse.capra.core.adapters.AbstractArtifactMetaModelAdapter;
import org.eclipse.emf.ecore.EObject;

/**
 * Provides generic functionality to deal with artifact meta models.
 */
public class GenericArtifactMetaModelAdapter extends AbstractArtifactMetaModelAdapter {

	private ArtifactWrapperContainer getContainer(EObject artifactModel) {
		return (ArtifactWrapperContainer) artifactModel;
	}

	@Override
	public EObject createModel() {
		return GenericArtifactMetaModelFactory.eINSTANCE.createArtifactWrapperContainer();
	}

	@Override
	public List<EObject> getArtifacts(EObject artifactModel) {
		ArtifactWrapperContainer container = (ArtifactWrapperContainer) artifactModel;
		List<EObject> result = new ArrayList<>();
		result.addAll(container.getArtifacts());
		return result;
	}

	public EObject getArtifact(EObject artifactModel, String artifactUri) {
		ArtifactWrapperContainer container = getContainer(artifactModel);
		for (ArtifactWrapper artifact : container.getArtifacts()) {
			if (artifactUri.equals(getArtifactUri(artifact))) {
				return artifact;
			}
		}
		return null;
	}

	@Override
	public EObject createArtifact(EObject artifactModel, String artifactHandler, String artifactUri,
			String artifactName) {
		ArtifactWrapperContainer container = getContainer(artifactModel);
		EObject existingWrapper = getArtifact(artifactModel, artifactUri);
		if (existingWrapper != null)
			return existingWrapper;

		ArtifactWrapper wrapper = GenericArtifactMetaModelFactory.eINSTANCE.createArtifactWrapper();
		wrapper.setArtifactHandler(artifactHandler);
		wrapper.setUri(artifactUri);
		wrapper.setName(artifactName);
		container.getArtifacts().add(wrapper);

		return wrapper;
	}

	@Override
	public String getArtifactName(EObject artifact) {
		if (artifact instanceof ArtifactWrapper) {
			ArtifactWrapper wrapper = (ArtifactWrapper) artifact;
			return wrapper.getName();
		}
		return null;
	}

	@Override
	public String getArtifactUri(EObject artifact) {
		if (artifact instanceof ArtifactWrapper) {
			ArtifactWrapper wrapper = (ArtifactWrapper) artifact;
			return wrapper.getUri();
		}
		return null;
	}
}
