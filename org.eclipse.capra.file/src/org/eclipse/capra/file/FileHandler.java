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
package org.eclipse.capra.file;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

import org.eclipse.capra.core.adapters.ArtifactMetaModelAdapter;
import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * Handler to allow tracing to and from arbitrary files in the file system.
 */
public class FileHandler extends AbstractArtifactHandler {
	@Override
	public EObject getEObjectForSelection(Object obj, EObject artifactModel) {
		if (obj instanceof String[]) {
			String[] strings = (String[]) obj;
			if (strings.length > 0) {
				ArtifactMetaModelAdapter adapter = ExtensionPointUtil.getArtifactWrapperMetaModelAdapter().get();
				EObject wrapper = adapter.createArtifact(artifactModel, this.getClass().getName(), getURI(obj),
						getName(obj));
				return wrapper;
			}
		}

		return null;
	}

	@Override
	public boolean canHandleSelection(Object obj) {
		if (obj instanceof String[]) {
			String[] strings = (String[]) obj;
			if (strings.length > 0) {
				File file = new File(strings[0]);
				if (file.exists()){
					return true;
				} else {
					for (File root : File.listRoots()) {
						if (file.equals(root)){
							return true;
						}
					}
				}
				return false;
			}
		}
		return false;
	}

	@Override
	public String getName(Object obj) {
		if (obj instanceof String[]) {
			String[] strings = (String[]) obj;
			if (strings.length > 0) {
				File file = new File(strings[0]);
				if (file.isFile()) {
					return file.getName();
				} else {
					return file.getAbsolutePath();
				}
			}
		}
		return "";
	}

	@Override
	public String getURI(Object obj) {
		if (obj instanceof String[]) {
			String[] strings = (String[]) obj;
			if (strings.length > 0) {
				File file = new File(strings[0]);
				return file.getAbsoluteFile().getAbsolutePath();
			}
		}
		return "";
	}

	@Override
	public Image getIcon(Object obj) {
		if (obj instanceof String[]) {
			String[] strings = (String[]) obj;
			if (strings.length > 0) {
				File file = new File(strings[0]);
				return getImage(file);
			}
		}

		return super.getIcon(obj);
	}

	@Override
	public String getObjectTypeName(Object obj) {
		if (obj instanceof File) {
			return "File";
		}

		return super.getObjectTypeName(obj);
	}

	@Override
	public Object resolveArtifact(EObject artifact) {
		return null;
	}

	// TODO: Dispose images
	private static Image getImage(File file) {
		ImageIcon systemIcon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(file);
		java.awt.Image image = systemIcon.getImage();
		if (image instanceof BufferedImage) {
			return new Image(Display.getCurrent(), convertToSWT((BufferedImage) image));
		}
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		return new Image(Display.getCurrent(), convertToSWT(bufferedImage));
	}

	private static ImageData convertToSWT(BufferedImage bufferedImage) {
		if (bufferedImage.getColorModel() instanceof DirectColorModel) {
			DirectColorModel colorModel = (DirectColorModel) bufferedImage.getColorModel();
			PaletteData palette = new PaletteData(colorModel.getRedMask(), colorModel.getGreenMask(),
					colorModel.getBlueMask());
			ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
					colorModel.getPixelSize(), palette);
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					int rgb = bufferedImage.getRGB(x, y);
					int pixel = palette.getPixel(new RGB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF));
					data.setPixel(x, y, pixel);
					if (colorModel.hasAlpha()) {
						data.setAlpha(x, y, (rgb >> 24) & 0xFF);
					}
				}
			}
			return data;
		} else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
			IndexColorModel colorModel = (IndexColorModel) bufferedImage.getColorModel();
			int size = colorModel.getMapSize();
			byte[] reds = new byte[size];
			byte[] greens = new byte[size];
			byte[] blues = new byte[size];
			colorModel.getReds(reds);
			colorModel.getGreens(greens);
			colorModel.getBlues(blues);
			RGB[] rgbs = new RGB[size];
			for (int i = 0; i < rgbs.length; i++) {
				rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF, blues[i] & 0xFF);
			}
			PaletteData palette = new PaletteData(rgbs);
			ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
					colorModel.getPixelSize(), palette);
			data.transparentPixel = colorModel.getTransparentPixel();
			WritableRaster raster = bufferedImage.getRaster();
			int[] pixelArray = new int[1];
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					raster.getPixel(x, y, pixelArray);
					data.setPixel(x, y, pixelArray[0]);
				}
			}
			return data;
		}
		return null;
	}
}
