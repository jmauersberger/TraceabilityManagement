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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * Handler to allow tracing to and from arbitrary model elements handled by EMF.
 */
public class EMFHandler extends AbstractArtifactHandler {

	public boolean canHandleSelection(Object selection) {
		if (selection instanceof EObject && !(selection instanceof Resource)) {
			// We can find out if we have a CDO object by getting the URI. Local
			// resources start with platform://... while CDO resources start
			// with cdo://...
			// getURI(selection);
			// if (getURI(selection).startsWith("cdo")) {
			// return false;
			// }
			return true;
		} else if (selection instanceof IAdaptable) {
			IAdaptable a = (IAdaptable) selection;
			if (a.getAdapter(EObject.class) != null) {
				EObject adapter = a.getAdapter(EObject.class);
				return canHandleSelection(adapter);
			}
		} 
		
//		else if (selection instanceof Resource) {
//			return true;
//		}

		return false;
	}

	@Override
	public EObject getEObjectForSelection(Object selection, EObject artifactModel) {
		if (selection instanceof EObject) {
			return EObject.class.cast(selection);
		} else if (selection instanceof IAdaptable) {
			IAdaptable a = (IAdaptable) selection;
			if (a.getAdapter(EObject.class) != null) {
				return a.getAdapter(EObject.class);
			}
		} 
		
//		else if (selection instanceof Resource) {
//			System.out.println();
//		}

		return null;
	}

	@Override
	public String getName(Object selection) throws CapraException {
		if (selection instanceof EObject) {
			EObject eObject = EObject.class.cast(selection);
			return UIStringUtil.createUIString(eObject);
		} else if (selection instanceof IAdaptable) {
			IAdaptable a = (IAdaptable) selection;
			if (a.getAdapter(EObject.class) != null) {
				EObject eObject = a.getAdapter(EObject.class);
				return UIStringUtil.createUIString(eObject);
			}
		} 
		
//		else if (selection instanceof Resource) {
//			((Resource) selection).getURI().toString();
//		}

		return null;
	}

	@Override
	public String getURI(Object selection) {
		if (selection instanceof EObject) {
			EObject eObject = EObject.class.cast(selection);
			return EcoreUtil.getURI(eObject).toString();
		} else if (selection instanceof IAdaptable) {
			IAdaptable a = (IAdaptable) selection;
			if (a.getAdapter(EObject.class) != null) {
				EObject eObject = a.getAdapter(EObject.class);
				return EcoreUtil.getURI(eObject).toString();
			}
		} 
		
//		else if (selection instanceof Resource) {
//			return ((Resource) selection).getURI().toString();
//		}

		return null;
	}

	@Override
	public Object resolveArtifact(EObject artifact) {
		return artifact;
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
							URL url = (URL) prov.getImage(eobj);
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
