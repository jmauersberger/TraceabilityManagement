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
import java.util.Optional;

import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.capra.core.util.UIStringUtil;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * Handler to allow tracing to and from arbitrary model elements handled by EMF.
 */
public class EMFHandler extends AbstractArtifactHandler {

	public boolean canHandleSelection(Object selection) {
		return selection instanceof EObject;
	}

	@Override
	public EObject getEObjectForSelection(Object selection, EObject artifactModel) {
		return EObject.class.cast(selection);
	}

	@Override
	public String getName(Object selection) {
		EObject eObject = EObject.class.cast(selection);
		return UIStringUtil.createUIString(eObject);
	}

	@Override
	public String getURI(Object selection) {
		EObject eObject = EObject.class.cast(selection);
		return EcoreUtil.getURI(eObject).toString();
	}

	@Override
	public Object resolveArtifact(EObject artifact) {
		return artifact;
	}

	@Override
	public Image getIcon(Object obj) {
		String extensionID = "org.eclipse.emf.edit.itemProviderAdapterFactories";

		Optional<Image> optional = ExtensionPointUtil.getExtensions(extensionID, "class").stream()
				.filter(ext -> ext instanceof AdapterFactory).map(ext -> (AdapterFactory) ext)
				.filter(adapter -> adapter.isFactoryForType(obj))
				.map(adapter -> (IItemLabelProvider) adapter.adapt(obj, IItemLabelProvider.class))
				.filter(prov -> prov != null).map(prov -> (URL) prov.getImage(obj))
				.map(url -> ExtendedImageRegistry.INSTANCE.getImage(url)).findFirst();
		return optional.orElse(super.getIcon(obj));

		// List<Object> extensions =
		// ExtensionPointUtil.getExtensions(extensionID, "class");
		// for (Object extension : extensions) {
		// if (extension instanceof AdapterFactory) {
		// AdapterFactory adapterFactory = (AdapterFactory) extension;
		// if (adapterFactory.isFactoryForType(obj)) {
		// IItemLabelProvider provider = (IItemLabelProvider)
		// adapterFactory.adapt(obj,
		// IItemLabelProvider.class);
		// if (provider != null) {
		// URL url = (URL) provider.getImage(obj);
		// return ExtendedImageRegistry.INSTANCE.getImage(url);
		// }
		// }
		// }
		// }
		//
		// return super.getIcon(obj);
	}
}
