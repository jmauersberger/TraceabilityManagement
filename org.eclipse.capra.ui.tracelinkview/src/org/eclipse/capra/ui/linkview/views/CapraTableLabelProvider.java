package org.eclipse.capra.ui.linkview.views;

import org.eclipse.capra.core.CapraException;
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
		String result = null;
		try {
			result = objWitHandler.getHandler().getName(objWitHandler.getObj());
		} catch (CapraException e) {
			e.printStackTrace();
		}
		if (result == null) {
			result = "";
		}

		return result;
	}

	private Image doGetImage(ObjectWithHandler objWithHandler) {
		Image result = null;
		try {
			return objWithHandler.getHandler().getIcon(objWithHandler.getObj());
		} catch (CapraException e) {
			e.printStackTrace();
		}
		return result;
	}
}