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
package org.eclipse.capra.handler.jdt;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.adapters.ArtifactMetaModelAdapter;
import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.ui.internal.WorkingSet;

/**
 * A handler to allow creating traces to and from java elements such as classes
 * and methods based on JDT.
 */
@SuppressWarnings("restriction")
public class JavaElementHandler extends AbstractArtifactHandler {

	@Override
	public boolean canHandleSelection(Object selection) {
		if (selection instanceof IJavaElement) {
			return true;
		} else if (selection instanceof WorkingSet) {
			return true;
		} 

		return false;
	}

	@Override
	public IJavaElement resolveArtifact(EObject artifact) throws CapraException {
		ArtifactMetaModelAdapter adapter = ExtensionPointUtil.getArtifactWrapperMetaModelAdapter();
		String uri = adapter.getArtifactUri(artifact);
		return JavaCore.create(uri);
	}

	@Override
	public String getName(Object selection) {
		if (selection instanceof IJavaElement) {
			IJavaElement cu = (IJavaElement) selection;
			return cu.getElementName();
		} else if (selection instanceof WorkingSet) {
			WorkingSet ws = (WorkingSet) selection;
			return ws.getName();
		}

		return "";
	}

	@Override
	public String getURI(Object selection) {
		if (selection instanceof IJavaElement) {
			IJavaElement cu = (IJavaElement) selection;
			return cu.getHandleIdentifier();
		} else if (selection instanceof WorkingSet) {
			WorkingSet ws = (WorkingSet) selection;
			return ws.getId();
		}

		return "";
	}

}
