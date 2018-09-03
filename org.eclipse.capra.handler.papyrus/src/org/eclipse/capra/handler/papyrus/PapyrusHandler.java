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
package org.eclipse.capra.handler.papyrus;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.capra.core.util.UIStringUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.TreeElement;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.impl.EObjectTreeElementImpl;

/**
 * A handler to create trace links from and to model elements created in
 * Papyrus.
 */
public class PapyrusHandler extends AbstractArtifactHandler {

	@Override
	public boolean canHandleSelection(Object selection) {
		boolean test = false;
		if(selection instanceof EObjectTreeElementImpl || selection instanceof EObject){
			test = true;
		}
		return test;
	}

	@Override
	public EObject getEObjectForSelection(Object selection, EObject artifactModel) {
		// Returns the EObject corresponding to the input object if the input is
		// an EObject, or if it is Adaptable to an EObject
		if(selection instanceof EObjectTreeElementImpl){
			return ((EObjectTreeElementImpl) selection).getEObject();
		}else{
			return null;
		}
	}

	@Override
	public String getName(Object selection) throws CapraException {
		if(selection instanceof EObjectTreeElementImpl){
			EObject eObject = ((EObjectTreeElementImpl) selection).getEObject();
			return UIStringUtil.createUIString(eObject);
		}else{
			return null;
		}
	}

	@Override
	public String getURI(Object selection) {
		if(selection instanceof EObjectTreeElementImpl){
			EObject eObject = ((EObjectTreeElementImpl) selection).getEObject();
			return EcoreUtil.getURI(eObject).toString();
		}else{
			return null;
		}

	}

	@Override
	public Object resolveArtifact(EObject artifact) {
		return artifact;
	}
}
