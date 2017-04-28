package org.eclipse.capra.handler.doors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

import org.eclipse.capra.GenericArtifactMetaModel.ArtifactWrapper;
import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.capra.handler.doors.util.DxlRunner;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Handler to allow tracing to and from IBM Doors.
 */
public class DoorsHandler extends AbstractArtifactHandler {
	// TODO: Dispose icon
	private final static Image icon;
	static {
		Bundle bundle = FrameworkUtil.getBundle(DoorsHandler.class);
		URL url = FileLocator.find(bundle, new Path("icons/doors_icon.png"), null);
		ImageDescriptor descriptor = ImageDescriptor.createFromURL(url);
		icon = descriptor.createImage();
	}

	private final static String dxl;
	static {
		String urlString = "dxl/getRequirementDescription.dxl";
		dxl = readTextfile(urlString).toString();
	}

	@Override
	public boolean canHandleSelection(Object obj) {
		if (obj instanceof String) {
			try {
				URI uri = new URI((String) obj);
				if (uri.getScheme().equalsIgnoreCase("doors")) {
					return true;
				}
			} catch (Exception ex) {
				return false;
			}
		}
		return false;
	}

	@Override
	public String getURI(Object obj) {
		return (String) obj;
	}

	@Override
	public String getName(Object obj) {
		// dxl.append("string url =
		// \"doors://doors.ikv-intern.local:36677/?version=2&prodID=0&urn=urn:telelogic::1-589c794e772f66ea-O-3-00000040\"");

		StringBuffer script = new StringBuffer(dxl);
		script.append("string url = \"");
		script.append(obj);
		script.append("\"\n");
		script.append("string result = getRequirementDescription(url)\n");
		script.append("oleSetResult(result)\n");
		String result = DxlRunner.getInstance().run(script.toString());

		return result;
	}

	@Override
	public Image getIcon(Object obj) {
		return DoorsHandler.icon;
	}

	@Override
	public Object resolveArtifact(EObject artifact) {
		if (artifact instanceof ArtifactWrapper) {
			String uri = ((ArtifactWrapper) artifact).getUri();
			if (uri.toLowerCase().startsWith("doors")) {
				return uri;
			}
		}
		return null;
	}

	private static StringBuffer readTextfile(String path) {
		Bundle bundle = FrameworkUtil.getBundle(DoorsHandler.class);
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		URL url;
		try {
			url = FileLocator.find(bundle, new Path(path), null);
			InputStream inputStream = url.openConnection().getInputStream();

			in = new BufferedReader(new InputStreamReader(inputStream));

			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
				sb.append('\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb;
	}

	@Override
	public String getObjectTypeName(Object obj) {
		return "Doors";
	}
}
