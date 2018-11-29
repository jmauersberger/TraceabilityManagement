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

import java.net.URL;
import java.util.List;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedImage;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.swt.graphics.Image;

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
	
	@Override
	public Image getIcon(Object obj) throws CapraException {
		String extensionID = "org.eclipse.emf.edit.itemProviderAdapterFactories";

		EObject eobj = null;

		if (obj instanceof EObject) {
			eobj = (EObject) obj;
		} else if (obj instanceof IAdaptable) {
			IAdaptable a = (IAdaptable) obj;
			if (a.getAdapter(EObject.class) != null) {
				eobj = a.getAdapter(EObject.class);
			}
		}
		if (eobj != null) {
			List<Object> extensions = ExtensionPointUtil.getExtensions(extensionID, "class");
			for (Object ext : extensions) {
				if (ext instanceof AdapterFactory) {
					AdapterFactory factory = (AdapterFactory) ext;
					if (factory.isFactoryForType(eobj)) {
						IItemLabelProvider prov = (IItemLabelProvider) factory.adapt(eobj, IItemLabelProvider.class);
						if (prov != null) {
							URL url = null;
							if(prov.getImage(eobj) instanceof URL){
								url = (URL) prov.getImage(eobj);
							}else if(prov.getImage(eobj) instanceof ComposedImage){
								ComposedImage cimage = (ComposedImage) prov.getImage(eobj);
								url = (URL) cimage.getImages().get(0);
							}
							Image image = ExtendedImageRegistry.INSTANCE.getImage(url);
							if (image != null) {
								return image;
							}
						}
					}
				}
			}
		}

		return super.getIcon(obj);
	}

}
