package org.eclipse.capra.core.util;

import org.eclipse.capra.core.CapraException;
import org.eclipse.ui.PlatformUI;

/**
 * Utility functions for exception handling.
 * 
 * @author Sascha Baumgart
 */
public class CapraExceptionUtil {
	/**
	 * Opens a message dialog on the gui thread using the active shell.
	 * 
	 * @param ex
	 *            the exception
	 * @param context
	 *            a bit of context to be used as the dialog title
	 */
	public static void handleException(CapraException ex, String context) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				// Shell activeShell =
				// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				// MessageDialog.openError(activeShell, context, "Reason:\n\n\n"
				// + ex.getMessage());

				System.err.println(ex);
			}
		});
	}
}
