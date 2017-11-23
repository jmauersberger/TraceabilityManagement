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
package org.eclipse.capra.handler.gef;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;

/**
 * Handler to allow tracing to and from visual model representations handled by
 * editors built with GEF.
 */
public class GEFHandler extends AbstractArtifactHandler {

	@Override
	public boolean canHandleSelection(Object selection) {
		return selection instanceof EditPart;
	}

	@Override
	public EObject getEObjectForSelection(Object selection, EObject artifactModel) {
		EditPart sel = (EditPart) selection;
		// TODO: Kick Papyrus EMFHelper
		return EMFHelper.getEObject(sel);
	}

	@Override
	public String getName(Object selection) throws CapraException {
		EditPart sel = (EditPart) selection;
		EObject eObject = EMFHelper.getEObject(sel);
		return org.eclipse.capra.core.util.UIStringUtil.createUIString(eObject);
	}

	@Override
	public String getURI(Object selection) {
		EditPart sel = (EditPart) selection;
		EObject eObject = EMFHelper.getEObject(sel);
		return EcoreUtil.getURI(eObject).toString();
	}

	@Override
	public Object resolveArtifact(EObject artifact) {
		return null;
	}

}
