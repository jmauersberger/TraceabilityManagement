package org.eclipse.capra.handler.web;

import java.net.URI;
import java.net.URL;

import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class WebHandler extends AbstractArtifactHandler {
	// TODO: Dispose icon
	private final static Image icon;
	static {
		Bundle bundle = FrameworkUtil.getBundle(WebHandler.class);
		URL url = FileLocator.find(bundle, new Path("icons/famfamfam/page_white_world.png"), null);
		ImageDescriptor descriptor = ImageDescriptor.createFromURL(url);
		icon = descriptor.createImage();
	}

	@Override
	public boolean canHandleSelection(Object obj) {
		if (obj instanceof String) {
			try {
				URI uri = new URI((String) obj);
				if (uri.getScheme().equalsIgnoreCase("http") || uri.getScheme().equalsIgnoreCase("https")) {
					return true;
				}
			} catch (Exception ex) {
				return false;
			}
		}
		return false;
	}

	@Override
	public Image getIcon(Object selection) {
		return icon;
	}

	@Override
	public String getURI(Object selection) {
		return (String) selection;
	}

	@Override
	public Object resolveArtifact(EObject artifact) {
		return null;
	}

	@Override
	public String getObjectTypeName(Object obj) {
		return "Webpage";
	}

}
