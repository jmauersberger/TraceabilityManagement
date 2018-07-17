package org.eclipse.capra.ui.tracelinkview.dialogs;

import java.util.List;
import org.eclipse.capra.core.util.TraceLinkAttribute;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class TraceLinkAttributesDialog extends TitleAreaDialog {
	
	private List<TraceLinkAttribute> attributes;
	private TableViewer viewer;
	
	public TraceLinkAttributesDialog(Shell parentShell, List<TraceLinkAttribute> attributes) {
		super(parentShell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		this.attributes = attributes;
	}
	
	@Override
    public void create() {
        setHelpAvailable(false);
        super.create();
        setTitle("Trace Link Attributes");
        setMessage("Enter/modify values and click OK to save them, cancel otherwise", IMessageProvider.INFORMATION);
    }

	@Override
	protected Control createDialogArea(final Composite parent){

		viewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		createColumns(viewer);

		// make lines and header visible
		final Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		viewer.setContentProvider(new ArrayContentProvider());
		viewer.setInput(attributes);

		// define layout for the viewer
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 2;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		viewer.getControl().setLayoutData(gridData);

		return super.createDialogArea(parent);
	}

	private void createColumns(TableViewer viewer) {
		
		int[] bounds = {150, 150, 150};
		String[] titles = {"Attribute Name", "Attribute Type", "Attribute Value"};
		//first column
		TableViewerColumn col = createTableViewerColumn(titles[0], bounds[0], 0);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object obj) {
				return ((TraceLinkAttribute)obj).getName();
			}
		});

		//second column
		col = createTableViewerColumn(titles[1], bounds[1], 1);
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object obj) {
				((TraceLinkAttribute)obj).getName();
				return ((TraceLinkAttribute)obj).getType();
			}
		});
		
		//third column
		col = createTableViewerColumn(titles[2], bounds[2], 2);
		col.setEditingSupport(new AttributeValueEditingSupport(viewer));
		col.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object obj) {
				return ((TraceLinkAttribute)obj).getValue();
			}
		});

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, final int colNumber) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}

	public List<TraceLinkAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<TraceLinkAttribute> attributes) {
		this.attributes = attributes;
	}
}
