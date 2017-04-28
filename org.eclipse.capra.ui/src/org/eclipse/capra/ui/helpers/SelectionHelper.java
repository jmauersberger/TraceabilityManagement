/*******************************************************************************
 * Copyright (c) 2016 Chalmers | University of Gothenburg, rt-labs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 *   Contributors:
 *      Chalmers | University of Gothenburg and rt-labs - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.capra.ui.helpers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.capra.core.CapraException;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Contains supporting functionality required when creating trace links.
 */
public class SelectionHelper {

	/**
	 * Extract selected elements from a selection event.
	 * 
	 * @param event
	 *            This is the click event to create a trace
	 * @return A list of all the selected elements
	 * @throws CapraException
	 */
	public static List<Object> extractSelectedElements(ExecutionEvent event) {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			ArrayList<Object> result = new ArrayList<>();
			List<?> list = structuredSelection.toList();
			result.addAll(list);
			return result;
		}
		return new ArrayList<>();
	}

	/**
	 * Extract selected elements from an {@link ISelection}.
	 * 
	 * @param selection
	 * @return A list of all the selected elements
	 * @throws CapraException
	 */
	public static List<Object> extractSelectedElements(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			ArrayList<Object> result = new ArrayList<>();
			List<?> list = structuredSelection.toList();
			result.addAll(list);
			return result;
		}
		return new ArrayList<>();
	}
}