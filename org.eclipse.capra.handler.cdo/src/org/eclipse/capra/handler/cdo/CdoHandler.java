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
package org.eclipse.capra.handler.cdo;

import java.util.List;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.emf.cdo.eresource.impl.CDOResourceFolderImpl;
import org.eclipse.emf.cdo.internal.net4j.CDONet4jSessionImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.internal.cdo.view.CDOViewImpl;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Handler to allow tracing to and from arbitrary files in the file system.
 */
@SuppressWarnings("restriction")
public class CdoHandler extends AbstractArtifactHandler {

	@Override
	public boolean canHandleSelection(Object obj) {
		return obj instanceof CDOViewImpl || obj instanceof CDONet4jSessionImpl || obj instanceof CDOResourceFolderImpl;
	}

	@Override
	public String getName(Object obj) {
		if (obj instanceof CDOViewImpl) {
			CDOViewImpl cdoView = (CDOViewImpl) obj;
			return cdoView.toString();
		} else if (obj instanceof CDONet4jSessionImpl) {
			CDONet4jSessionImpl session = (CDONet4jSessionImpl) obj;
			return session.getRepositoryName();
		} else if (obj instanceof CDOResourceFolderImpl) {
			CDOResourceFolderImpl folder = (CDOResourceFolderImpl) obj;
			return folder.getName();
		}
		return "";
	}

	@Override
	public String getURI(Object obj) {
		if (obj instanceof CDOViewImpl) {
			CDOViewImpl cdoView = (CDOViewImpl) obj;
			return "cdoview://" + cdoView.getViewID();
		} else if (obj instanceof CDONet4jSessionImpl) {
			CDONet4jSessionImpl session = (CDONet4jSessionImpl) obj;
			return session.getRepositoryName();
		}
		if (obj instanceof CDOResourceFolderImpl) {
			CDOResourceFolderImpl folder = (CDOResourceFolderImpl) obj;
			return folder.getURI().toString();
		}
		return "";
	}

	@Override
	public Image getIcon(Object obj) throws CapraException {
		List<Object> extensions = ExtensionPointUtil.getExtensions("org.eclipse.ui.navigator.navigatorContent",
				"labelProvider");

		for (Object ext : extensions) {
			if (ext instanceof ILabelProvider) {
				try {
					Image icon = ((ILabelProvider) ext).getImage(obj);
					if (icon != null) {
						return icon;
					}
				} catch (Exception ex) {
				}
			}
		}

		return super.getIcon(obj);
	}

	@Override
	public String getObjectTypeName(Object obj) throws CapraException {
		return super.getObjectTypeName(obj);
	}

	@Override
	public Object resolveArtifact(EObject artifact) {
		return null;
	}
}
