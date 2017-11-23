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
package org.eclipse.capra.handler.emf;

import java.net.URL;
import java.util.List;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.capra.core.util.UIStringUtil;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * Handler to allow tracing to and from arbitrary model elements handled by EMF.
 */
public class EMFHandler extends AbstractArtifactHandler {

	public boolean canHandleSelection(Object selection) {
		if (selection instanceof EObjectImpl) {
			return true;
		} else if (selection instanceof IAdaptable) {
			IAdaptable a = (IAdaptable) selection;
			if (a.getAdapter(EObject.class) != null) {
				EObject adapter = a.getAdapter(EObject.class);
				return canHandleSelection(adapter);
			}
		}

		return false;
	}

	@Override
	public EObject getEObjectForSelection(Object selection, EObject artifactModel) {
		if (selection instanceof EObjectImpl) {
			return EObject.class.cast(selection);
		} else if (selection instanceof IAdaptable) {
			IAdaptable a = (IAdaptable) selection;
			if (a.getAdapter(EObject.class) != null) {
				return a.getAdapter(EObject.class);
			}
		}

		return null;
	}

	@Override
	public String getName(Object selection) throws CapraException {
		if (selection instanceof EObjectImpl) {
			EObject eObject = EObject.class.cast(selection);
			return UIStringUtil.createUIString(eObject);
		} else if (selection instanceof IAdaptable) {
			IAdaptable a = (IAdaptable) selection;
			if (a.getAdapter(EObject.class) != null) {
				EObject eObject = a.getAdapter(EObject.class);
				return UIStringUtil.createUIString(eObject);
			}
		}

		return null;
	}

	@Override
	public String getURI(Object selection) {
		if (selection instanceof EObjectImpl) {
			EObject eObject = EObject.class.cast(selection);
			return EcoreUtil.getURI(eObject).toString();
		} else if (selection instanceof IAdaptable) {
			IAdaptable a = (IAdaptable) selection;
			if (a.getAdapter(EObject.class) != null) {
				EObject eObject = a.getAdapter(EObject.class);
				return EcoreUtil.getURI(eObject).toString();
			}
		}

		return null;
	}

	@Override
	public Object resolveArtifact(EObject artifact) {
		return artifact;
	}

	@Override
	public Image getIcon(Object obj) throws CapraException {
		String extensionID = "org.eclipse.emf.edit.itemProviderAdapterFactories";

		EObject eobj1 = null;

		if (obj instanceof EObjectImpl) {
			eobj1 = (EObject) obj;
		} else if (obj instanceof IAdaptable) {
			IAdaptable a = (IAdaptable) obj;
			if (a.getAdapter(EObject.class) != null) {
				eobj1 = a.getAdapter(EObject.class);
			}
		}
		EObject eobj2 = eobj1;
		// Optional<Image> optional = ExtensionPointUtil.getExtensions(extensionID,
		// "class").stream()
		// .filter(ext -> ext instanceof AdapterFactory).map(ext -> (AdapterFactory)
		// ext)
		// .filter(adapter -> adapter.isFactoryForType(eobj2))
		// .map(adapter -> (IItemLabelProvider) adapter.adapt(eobj2,
		// IItemLabelProvider.class))
		// .filter(prov -> prov != null).map(prov -> (URL) prov.getImage(eobj2))
		// .map(url -> ExtendedImageRegistry.INSTANCE.getImage(url)).findFirst();

		List<Object> extensions = ExtensionPointUtil.getExtensions(extensionID, "class");
		for (Object ext : extensions) {
			if (ext instanceof AdapterFactory) {
				AdapterFactory factory = (AdapterFactory) ext;
				if (factory.isFactoryForType(eobj2)) {
					IItemLabelProvider prov = (IItemLabelProvider) factory.adapt(eobj2, IItemLabelProvider.class);
					if (prov != null) {
						URL url = (URL) prov.getImage(eobj2);
						Image image = ExtendedImageRegistry.INSTANCE.getImage(url);
						if (image != null) {
							return image;
						}
					}
				}
			}
		}

		return super.getIcon(eobj2);
	}
}
