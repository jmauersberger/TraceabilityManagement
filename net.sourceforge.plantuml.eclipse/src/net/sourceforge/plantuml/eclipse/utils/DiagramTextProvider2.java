package net.sourceforge.plantuml.eclipse.utils;

import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

public interface DiagramTextProvider2 extends DiagramTextProvider {
	public boolean supportsPath(IPath path);
	public String getDiagramText(IWorkbenchPart workbenchPart, ISelection selection, Map<String, Object> markerAttributes);
	public String getDiagramText(IPath path);
}
