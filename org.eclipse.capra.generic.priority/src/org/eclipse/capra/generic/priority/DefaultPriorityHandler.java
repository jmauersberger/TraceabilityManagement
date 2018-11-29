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
package org.eclipse.capra.generic.priority;

import java.util.Collection;

import org.eclipse.capra.GenericArtifactMetaModel.ArtifactWrapper;
import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.capra.core.handlers.PriorityHandler;
import org.eclipse.capra.handler.cdo.CdoHandler;
import org.eclipse.capra.handler.emf.EMFHandler;
import org.eclipse.capra.handler.file.IResourceHandler;
import org.eclipse.capra.handler.gef.GEFHandler;
import org.eclipse.capra.handler.papyrus.PapyrusHandler;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.impl.EObjectTreeElementImpl;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.resource.UMLResource;

//import org.eclipse.capra.core.handlers.ArtifactHandler;
//import org.eclipse.capra.core.handlers.PriorityHandler;
//import org.eclipse.capra.handler.hudson.HudsonHandler;

/**
 * Provides a simple default policy for selecting an {@link ArtifactHandler} in
 * cases where tests or builds from Hudson are selected by returning the first
 * available {@link HudsonHandler}.
 */
public class DefaultPriorityHandler implements PriorityHandler {

	@Override
	public ArtifactHandler getSelectedHandler(Collection<ArtifactHandler> handlers, Object selectedElement) {
		// if (selectedElement instanceof TestElement || selectedElement instanceof
		// BuildElement) {
		// return handlers.stream().filter(h -> h instanceof
		// HudsonHandler).findAny().get();
		//
		// }
		
		try {
				
			if (selectedElement instanceof ArtifactWrapper){
				return handlers.stream().filter(h -> h instanceof IResourceHandler).findAny().get();
			}
			
		
			if (selectedElement instanceof EditPart) {
				
				EObject eobj = EMFHelper.getEObject(selectedElement);
				if (eobj instanceof NamedElement){
					return handlers.stream().filter(h -> h instanceof PapyrusHandler).findAny().get();
				}
				
				Resource res = eobj.eResource();
				if (res instanceof CDOResource){
					return handlers.stream().filter(h -> h instanceof CdoHandler).findAny().get();
				}
				
				return handlers.stream().filter(h -> h instanceof GEFHandler).findAny().get();
					
			}
			
			if (selectedElement instanceof EObject){
				EObject eobj = (EObject) selectedElement;
				if (eobj.eResource() instanceof UMLResource)
					return handlers.stream().filter(h -> h instanceof PapyrusHandler).findAny().get();
				else if (eobj.eResource() instanceof CDOResource || eobj instanceof CDOObject){
					return handlers.stream().filter(h -> h instanceof CdoHandler).findAny().get();
				}
			}
			
			if (selectedElement instanceof EObjectTreeElementImpl){
				return handlers.stream().filter(h -> h instanceof PapyrusHandler).findAny().get();
			}
			
			if (selectedElement instanceof EObject){
				return handlers.stream().filter(h -> h instanceof EMFHandler).findAny().get();
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		return null;
	}

}
