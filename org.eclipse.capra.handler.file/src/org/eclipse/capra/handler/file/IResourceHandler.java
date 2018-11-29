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
package org.eclipse.capra.handler.file;

import java.io.File;
import java.util.List;

import org.eclipse.capra.GenericArtifactMetaModel.ArtifactWrapper;
import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.core.internal.resources.Resource;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Handler to allow tracing to and from arbitrary files in the file system.
 */
public class IResourceHandler extends AbstractArtifactHandler {

	@Override
	public boolean canHandleSelection(Object obj) {
		return obj instanceof IResource || obj instanceof ArtifactWrapper;
	}

	@Override
	public String getName(Object obj) {
		
		if (obj instanceof ArtifactWrapper)
			return ((ArtifactWrapper)obj).getName();
		
		IResource selectionAsIResource = (IResource) obj;
		return selectionAsIResource.getName();
	}

	@Override
	public String getURI(Object obj) {
		
		if (obj instanceof ArtifactWrapper){
			ArtifactWrapper aw = (ArtifactWrapper) obj;
			return aw.getUri();
		}
		
		IResource selectionAsIResource = (IResource) obj;
		return selectionAsIResource.getFullPath().toString();
	}

	@Override
	public Image getIcon(Object obj) throws CapraException {
		List<Object> extensions = ExtensionPointUtil.getExtensions("org.eclipse.ui.navigator.navigatorContent",
				"labelProvider");
		
		if (obj instanceof ArtifactWrapper){
			
			try{
				String uri = ((ArtifactWrapper) obj).getUri();
				File f = new File(uri);
				obj = f;
			}catch (Exception ex){
				ex.printStackTrace();
			}
		}

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
		if (obj instanceof IFile) {
			return "File";
		} else if (obj instanceof IProject) {
			return "Project";
		} else if (obj instanceof IFolder) {
			return "Folder";
		}

		return super.getObjectTypeName(obj);
	}

	@Override
	public Object resolveArtifact(EObject artifact) {
		if (artifact instanceof ArtifactWrapper) {
			String uri = ((ArtifactWrapper) artifact).getUri();

			IPath path = null;
			try {
				path = new Path(uri);
			} catch (Exception ex) {
			}

			if (path != null) {
				try {
					IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
					if (file != null && file.exists()) {
						return file;
					}
				} catch (Exception ex) {
				}
			}

			if (path != null) {
				try {
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(uri);
					if (project != null && project.exists()) {
						return project;
					}
				} catch (Exception ex) {
				}
			}

			if (path != null) {
				try {
					IFolder folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(path);
					if (folder != null && folder.exists()) {
						return folder;
					}
				} catch (Exception ex) {
				}
			}
		}
		return null;
	}
}
