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
package org.eclipse.capra.core.handlers;

import org.eclipse.capra.core.CapraException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;

/**
 * This interface defines functionality required to map chosen Objects in the
 * Eclipse workspace to EObjects of some kind, which can then be traced and
 * persisted in EMF models.
 * 
 * Implementations can use the provided concepts of an {@link ArtifactWrapper}
 * if this is suitable.
 * 
 * @author Anthony Anjorin, Salome Maro
 */
public interface ArtifactHandler {

	/**
	 * Can the handler map selection to an EObject as required?
	 * 
	 * @param selection
	 *            The object to be mapped to an EObject
	 * @return <code>true</code> if selection can be handled, <code>false</code>
	 *         otherwise.
	 */
	boolean canHandleSelection(Object selection);

	/**
	 * Gets the name for the selected object.
	 * 
	 * @param selection
	 * @return the name
	 * @throws CapraException Something went wrong
	 */
	String getName(Object selection) throws CapraException;

	/**
	 * Gets the icon to be displayed together with the name of the selected object.
	 * 
	 * @param selection
	 * @return the image
	 * @throws CapraException Something went wrong
	 */
	Image getIcon(Object selection) throws CapraException;

	/**
	 * Gets the URI for the selected object.
	 * 
	 * @param selection
	 * @return the URI as a string
	 * @throws CapraException Something went wrong
	 */
	String getURI(Object selection) throws CapraException;

	/**
	 * Map the object selection to an EObject.
	 * 
	 * @param selection
	 *            The object to be mapped
	 * @param artifactModel
	 * @return the eobject for the selection
	 * @throws CapraException Something went wrong
	 */
	EObject getEObjectForSelection(Object selection, EObject artifactModel) throws CapraException;

	/**
	 * Resolve the persisted EObject to the originally selected Object from the
	 * Eclipse workspace. This is essentially the inverse of the
	 * getEObjectForSelection operation.
	 * 
	 * @param artifact
	 *            The persisted EObject
	 * @return originally selected object
	 * @throws CapraException Something went wrong
	 */
	Object resolveArtifact(EObject artifact) throws CapraException;

	/**
	 * Gets the type name of the object.
	 * 
	 * @param obj
	 *            the object
	 * @return the type name for the object
	 * @throws CapraException Something went wrong
	 */
	String getObjectTypeName(Object obj) throws CapraException;
}
