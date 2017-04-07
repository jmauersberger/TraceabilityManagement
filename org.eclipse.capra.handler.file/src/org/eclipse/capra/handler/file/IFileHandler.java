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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.capra.core.helpers.ExtensionPointHelper;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Handler to allow tracing to and from arbitrary files in the file system.
 */
public class IFileHandler extends AbstractArtifactHandler {

	@Override
	public boolean canHandleSelection(Object obj) {
		return obj instanceof IFile;
	}

	@Override
	public String getName(Object obj) {
		IFile selectionAsFile = (IFile) obj;
		return selectionAsFile.getName();
	}

	@Override
	public String getURI(Object obj) {
		IFile selectionAsFile = (IFile) obj;
		return selectionAsFile.getFullPath().toString();
	}

	@Override
	public Image getIcon(Object obj) {
		List<Object> extensions = ExtensionPointHelper.getExtensions("org.eclipse.ui.navigator.navigatorContent", "labelProvider");
		
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
}
