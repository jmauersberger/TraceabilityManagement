package org.eclipse.capra.handler.emf;

import java.util.List;

import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;

public class XMIResourceHandler extends AbstractArtifactHandler {

	@Override
	public boolean canHandleSelection(Object selection) {
		return selection instanceof XMIResource;
	}

	@Override
	public String getName(Object selection) {
		XMIResource res = (XMIResource) selection;
		return res.getURI().lastSegment();
	}

	@Override
	public String getObjectTypeName(Object obj) {
		return XMIResource.class.getName();
	}

	@Override
	public String getURI(Object selection) {
		XMIResource res = (XMIResource) selection;
		return res.getURI().toString();
	}

	@Override
	public Image getIcon(Object obj) {
		List<Object> extensions = ExtensionPointUtil.getExtensions("org.eclipse.ui.navigator.navigatorContent",
				"labelProvider");

		XMIResource res = (XMIResource) obj;
		URI uri = res.getURI();
		String[] segments = uri.segments();

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject project = root.getProject(segments[1]);

		IFolder folder = null;
		for (int i = 1; i < segments.length - 2; i++) {
			System.out.println(segments[i]);
			if (folder == null) {
				folder = project.getFolder(segments[i]);
			} else {
				folder = folder.getFolder(segments[i]);
			}
		}

		IFile file = null;
		if (folder == null) {
			file = project.getFile(segments[segments.length - 1]);
		} else {
			file = folder.getFile(segments[segments.length - 1]);
		}

		for (Object ext : extensions) {
			if (ext instanceof ILabelProvider) {
				try {
					Image icon = ((ILabelProvider) ext).getImage(file);
					if (icon != null) {
						return icon;
					}
				} catch (Exception ex) {
					System.out.println();
				}
			}
		}

		return super.getIcon(obj);
	}

	@Override
	public Object resolveArtifact(EObject artifact) {
		return null;
	}
}
