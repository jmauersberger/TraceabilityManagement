/*******************************************************************************
 *  Copyright (c) {2016} Chalmers|Gothenburg University, rt-labs and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *   Contributors:
 *      Chalmers|Gothenburg University and rt-labs - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.app4mc.capra.generic.handlers.selection;

import org.eclipse.app4mc.capra.generic.views.SelectionView;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 * Action to remove all objects from the selection view
 * 
 * @author Anthony Anjorin, Salome Maro
 */
public class ClearSelectionHandler extends AbstractHandler  {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		SelectionView.getOpenedView().clearSelection();
		return null;
	}
}
