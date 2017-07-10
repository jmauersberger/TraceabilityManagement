package org.eclipse.capra.ui.linkview.views;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

public class CapraTableLabelProvider extends LabelProvider implements ITableLabelProvider {
	@Override
	public String getText(Object element) {
		return doGetText((ObjectWithHandler) element);
	};

	@Override
	public String getColumnText(Object element, int index) {
		return doGetText((ObjectWithHandler) element);
	}

	@Override
	public Image getColumnImage(Object element, int index) {
		return doGetImage((ObjectWithHandler) element);
	}

	@Override
	public Image getImage(Object element) {
		return doGetImage((ObjectWithHandler) element);
	}

	private String doGetText(ObjectWithHandler objWitHandler) {
		return objWitHandler.handler.getName(objWitHandler.o);
	}

	private Image doGetImage(ObjectWithHandler objWithHandler) {
		return objWithHandler.handler.getIcon(objWithHandler.o);
	}
}