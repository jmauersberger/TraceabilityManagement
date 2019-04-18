package org.eclipse.capra.handler.reqif.preferences;

import org.eclipse.capra.handler.reqif.Activator;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class ReqIFPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	public static final String IDATTRIBUTE = "Attribute for ID";

	public ReqIFPreferencePage() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Preferences page for ReqIf display in traceability view");

	}

	@Override
	protected void createFieldEditors() {
		StringFieldEditor idField = new StringFieldEditor(IDATTRIBUTE, IDATTRIBUTE,
		getFieldEditorParent());	
		addField(idField);
	}

}
