package org.eclipse.capra.ui.linkview.views;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.util.CapraExceptionUtil;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;

class CapraSelectionDropAdapter extends ViewerDropAdapter {
	private CapraTableViewer viewer;

	public CapraSelectionDropAdapter(CapraTableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
	}

	@Override
	public boolean performDrop(Object data) {
		try {
			viewer.addToSelection(data);
		} catch (CapraException e) {
			CapraExceptionUtil.handleException(e, "Exception while DND operation");
		}
		return true;
	}

	@Override
	public boolean validateDrop(Object target, int operation, TransferData transferType) {
		return true;
	}
}