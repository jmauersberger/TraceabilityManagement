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

import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.capra.core.handlers.PriorityHandler;
import org.eclipse.capra.handler.emf.EMFHandler;
import org.eclipse.capra.handler.gef.GEFHandler;
import org.eclipse.capra.handler.papyrus.PapyrusHandler;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.impl.EObjectTreeElementImpl;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.uml2.uml.NamedElement;

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
		
			if (selectedElement instanceof EditPart) {
				
				EObject obj = EMFHelper.getEObject(selectedElement);
				if (obj instanceof NamedElement){
					return handlers.stream().filter(h -> h instanceof PapyrusHandler).findAny().get();
				}
				
				return handlers.stream().filter(h -> h instanceof GEFHandler).findAny().get();
					
			}
			
			if (selectedElement instanceof EObjectTreeElementImpl){
				return handlers.stream().filter(h -> h instanceof PapyrusHandler).findAny().get();
			}
			
			if (selectedElement instanceof EObject)
				return handlers.stream().filter(h -> h instanceof EMFHandler).findAny().get();
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		return null;
	}

}
