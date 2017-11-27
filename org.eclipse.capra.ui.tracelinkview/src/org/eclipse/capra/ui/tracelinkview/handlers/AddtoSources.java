package org.eclipse.capra.ui.tracelinkview.handlers;

import java.util.List;

import org.eclipse.capra.ui.tracelinkview.views.TracelinkView;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class AddtoSources extends AbstractHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<Object> selection;
		selection = SelectionHelper.extractSelectedElements(event);
		TracelinkView.getOpenedView().addToSources(selection);
		return null;
	}
}