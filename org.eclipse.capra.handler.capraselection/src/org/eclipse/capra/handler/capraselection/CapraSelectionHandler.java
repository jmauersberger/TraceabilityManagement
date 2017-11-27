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
package org.eclipse.capra.handler.capraselection;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.capra.ui.tracelinkview.views.ObjectWithHandler;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;

/**
 * This handler allows selecting ObjectWithHandler instances from the Capra
 * views. This is useful when PlantUML is running.
 */
public class CapraSelectionHandler extends AbstractArtifactHandler {

	public boolean canHandleSelection(Object selection) {
		if (selection instanceof ObjectWithHandler) {
			Object obj = ((ObjectWithHandler) selection).getObj();
			try {
				ArtifactHandler artifactHandler = ExtensionPointUtil.getArtifactWrapperMetaModelAdapter()
						.getArtifactHandler(obj);
				if (artifactHandler instanceof CapraSelectionHandler) {
					return false;
				} else {
					return artifactHandler.canHandleSelection(obj);
				}
			} catch (CapraException e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public EObject getEObjectForSelection(Object selection, EObject artifactModel) {
		if (selection instanceof ObjectWithHandler) {
			Object obj = ((ObjectWithHandler) selection).getObj();
			try {
				ArtifactHandler artifactHandler = ExtensionPointUtil.getArtifactWrapperMetaModelAdapter()
						.getArtifactHandler(obj);
				if (artifactHandler instanceof CapraSelectionHandler) {
					return null;
				} else {
					return artifactHandler.getEObjectForSelection(obj, artifactModel);
				}
			} catch (CapraException e) {
				return null;
			}
		}
		return null;
	}

	@Override
	public String getName(Object selection) throws CapraException {
		if (selection instanceof ObjectWithHandler) {
			Object obj = ((ObjectWithHandler) selection).getObj();
			try {
				ArtifactHandler artifactHandler = ExtensionPointUtil.getArtifactWrapperMetaModelAdapter()
						.getArtifactHandler(obj);
				if (artifactHandler instanceof CapraSelectionHandler) {
					return "";
				} else {
					return artifactHandler.getName(obj);
				}
			} catch (CapraException e) {
				return "";
			}
		}
		return "";
	}

	@Override
	public String getURI(Object selection) {
		if (selection instanceof ObjectWithHandler) {
			Object obj = ((ObjectWithHandler) selection).getObj();
			try {
				ArtifactHandler artifactHandler = ExtensionPointUtil.getArtifactWrapperMetaModelAdapter()
						.getArtifactHandler(obj);
				if (artifactHandler instanceof CapraSelectionHandler) {
					return null;
				} else {
					return artifactHandler.getURI(obj);
				}
			} catch (CapraException e) {
				return null;
			}
		}
		return null;
	}

	@Override
	public Object resolveArtifact(EObject artifact) throws CapraException {
		throw new CapraException("CapraSelectionHandler does not implement resolveArtifact().");
	}

	@Override
	public Image getIcon(Object selection) throws CapraException {
		if (selection instanceof ObjectWithHandler) {
			Object obj = ((ObjectWithHandler) selection).getObj();
			ArtifactHandler artifactHandler = ExtensionPointUtil.getArtifactWrapperMetaModelAdapter()
					.getArtifactHandler(obj);
			if (!(artifactHandler instanceof CapraSelectionHandler)) {
				return artifactHandler.getIcon(obj);
			}
			return super.getIcon(obj);
		}

		return null;
	}
}
