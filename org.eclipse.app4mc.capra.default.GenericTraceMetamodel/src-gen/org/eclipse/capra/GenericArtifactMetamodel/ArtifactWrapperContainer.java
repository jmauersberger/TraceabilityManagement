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
/**
 */
package org.eclipse.capra.GenericArtifactMetamodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Artifact Wrapper Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.capra.GenericArtifactMetamodel.ArtifactWrapperContainer#getArtifacts <em>Artifacts</em>}</li>
 * </ul>
 *
 * @see org.eclipse.capra.GenericArtifactMetamodel.GenericArtifactMetamodelPackage#getArtifactWrapperContainer()
 * @model
 * @generated
 */
public interface ArtifactWrapperContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Artifacts</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.capra.GenericArtifactMetamodel.ArtifactWrapper}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Artifacts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Artifacts</em>' containment reference list.
	 * @see org.eclipse.capra.GenericArtifactMetamodel.GenericArtifactMetamodelPackage#getArtifactWrapperContainer_Artifacts()
	 * @model containment="true"
	 * @generated
	 */
	EList<ArtifactWrapper> getArtifacts();

} // ArtifactWrapperContainer
