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
package org.eclipse.capra.handler.cdt;

import org.eclipse.capra.core.adapters.ArtifactMetaModelAdapter;
import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.emf.ecore.EObject;

/**
 * Handler to allow tracing to and from elements of C such as files and
 * functions. Uses CDT as the foundation.
 */
public class CDTHandler extends AbstractArtifactHandler {

	@Override
	public boolean canHandleSelection(Object selection) {
		return selection instanceof ICElement;
	}

	@Override
	public ICElement resolveArtifact(EObject artifact) {
		ArtifactMetaModelAdapter adapter = ExtensionPointUtil.getArtifactWrapperMetaModelAdapter().get();
		String uri = adapter.getArtifactUri(artifact);
		return CoreModel.create(uri);
	}

	@Override
	public String getName(Object selection) {
		ICElement cu = (ICElement) selection;
		return cu.getElementName();
	}

	@Override
	public String getURI(Object selection) {
		ICElement cu = (ICElement) selection;
		return cu.getHandleIdentifier();
	}

}
