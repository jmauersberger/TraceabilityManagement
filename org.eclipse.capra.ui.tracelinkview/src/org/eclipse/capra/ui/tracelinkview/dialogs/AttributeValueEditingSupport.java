package org.eclipse.capra.ui.tracelinkview.dialogs;

import org.eclipse.capra.core.util.TraceLinkAttribute;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

public class AttributeValueEditingSupport extends EditingSupport {
	
	private final TableViewer viewer;
    private final CellEditor editor;

	public AttributeValueEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
        this.editor = new TextCellEditor(viewer.getTable());
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return editor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		return ((TraceLinkAttribute) element).getValue();
	}

	@Override
	protected void setValue(Object element, Object inputValue) {
		((TraceLinkAttribute) element).setValue(String.valueOf(inputValue));
		 viewer.update(element, null);
	}

}
