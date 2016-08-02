/*******************************************************************************
 *  Copyright (c) {2016} Chalmers|Gothenburg University and rt-labs.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *   Contributors:
 *      Chalmers|Gothenburg University, rt-labs - initial API and implementation and initial documentation
 *******************************************************************************/
package org.eclipse.app4mc.capra.handlers;

import org.eclipse.app4mc.capra.core.adapters.ArtifactMetaModelAdapter;
import org.eclipse.app4mc.capra.core.handlers.ArtifactHandler;
import org.eclipse.app4mc.capra.core.helpers.ExtensionPointHelper;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.emf.ecore.EObject;


public class CDTHandler implements ArtifactHandler {

	@Override
	public boolean canHandleSelection(Object selection) {
		return selection instanceof ICElement; 
	}

	@Override
	public EObject getEObjectForSelection(Object selection, EObject  artifactModel) {
		ICElement cu = (ICElement)selection;
		ArtifactMetaModelAdapter adapter = ExtensionPointHelper.getArtifactWrapperMetaModelAdapter().get();
		EObject wrapper = adapter.createArtifact(
				artifactModel,
				this.getClass().getName(), 
				cu.getHandleIdentifier(), 
				cu.getElementName());
		return wrapper;
	}

}
