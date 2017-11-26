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
package org.eclipse.capra.core.adapters;

import java.util.List;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.emf.ecore.EObject;

/**
 * This interface defines the functionality necessary to deal with meta models
 * that describe the artifacts to and from which trace links are created.
 */
public interface ArtifactMetaModelAdapter {

	/**
	 * Create a new model for artifacts.
	 * 
	 * @return the new model
	 * @throws CapraException Something went wrong
	 */
	EObject createModel() throws CapraException;

	/**
	 * Gets the artifacts from the artifact model.
	 * 
	 * @param artifactModel
	 * @return the list of artifacts
	 * @throws CapraException Something went wrong
	 */
	List<EObject> getArtifacts(EObject artifactModel) throws CapraException;

	/**
	 * Create a new artifact. The list of artifacts is searched for an existing
	 * artifact with the same handler and uri. If found, the existing artifact is
	 * returned, otherwise a new artifact is created.
	 * 
	 * @param artifactHandler
	 *            Handler of artifact
	 * @param artifactUri
	 *            Uri of artifact
	 * @param artifactName
	 *            Name of artifact
	 * @return new or existing artifact
	 * @throws CapraException Something went wrong
	 */
	EObject createArtifact(EObject artifactModel, String artifactHandler, String artifactUri, String artifactName)
			throws CapraException;

	/**
	 * Get artifact with given handler and uri.
	 * 
	 * @param artifactHandler
	 *            Handler of artifact
	 * @param artifactUri
	 *            Uri of artifact
	 * @return artifact if found, null otherwise
	 * @throws CapraException Something went wrong
	 */
	EObject getArtifact(EObject artifactModel, String artifactUri) throws CapraException;

	/**
	 * Get a handler for the given artifact
	 * 
	 * @param artifact
	 * @return artifact handler
	 * @throws CapraException Something went wrong
	 */
	// String getArtifactHandlerName(EObject artifact) throws CapraException;

	/**
	 * Get the name of the given artifact.
	 * 
	 * @param artifact
	 * @return artifact name
	 * @throws CapraException Something went wrong
	 */
	String getArtifactName(EObject artifact) throws CapraException;

	/**
	 * Get the URI of the given artifact.
	 * 
	 * @param artifact
	 * @return artifact uri
	 * @throws CapraException Something went wrong
	 */
	String getArtifactUri(EObject artifact) throws CapraException;

	/**
	 * Get an instance of the artifact handler.
	 * 
	 * @param artifact
	 * @return artifact handler instance
	 * @throws CapraException Something went wrong
	 */
	ArtifactHandler getArtifactHandler(Object artifact) throws CapraException;
}
