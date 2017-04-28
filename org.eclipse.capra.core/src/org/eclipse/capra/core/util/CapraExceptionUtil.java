package org.eclipse.capra.core.util;

import org.eclipse.capra.core.CapraException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

public class CapraExceptionUtil {
	public static void handleException(Shell shell, CapraException ex, String context) {
		MessageDialog.openError(shell, context, ex.getMessage());
	}
}
