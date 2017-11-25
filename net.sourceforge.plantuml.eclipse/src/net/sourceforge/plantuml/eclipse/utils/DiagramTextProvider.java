package net.sourceforge.plantuml.eclipse.utils;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

public interface DiagramTextProvider {
	public boolean supportsWorkbenchPart(IWorkbenchPart workbenchPart);
	public boolean supportsSelection(ISelection selection);
	public String getDiagramText(IWorkbenchPart workbenchPart, ISelection selection);
}
