package org.eclipse.capra.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.capra.core.handlers.PriorityHandler;

public class CapraUtils {

	public static ArtifactHandler getHandler(Object target) throws CapraException {
		Collection<ArtifactHandler> artifactHandlers = ExtensionPointUtil.getArtifactHandlers();

		List<ArtifactHandler> availableHandlers = new ArrayList<>();
		for (ArtifactHandler artifactHandler : artifactHandlers) {
			if (artifactHandler.canHandleSelection(target)) {
				availableHandlers.add(artifactHandler);
			}
		}

		PriorityHandler priorityHandler = ExtensionPointUtil.getPriorityHandler();
		if (availableHandlers.size() == 0) {
			throw new CapraException("There is no handler for " + target );
			
		} else if (availableHandlers.size() > 1 && priorityHandler == null) {
			
			throw new CapraException("There are multiple handlers for " + target + " and no priority handler registered.");
			
		} else if (availableHandlers.size() > 1 && priorityHandler != null) {
			return priorityHandler.getSelectedHandler(availableHandlers, target);
		} else
			return availableHandlers.get(0);
	}
	
}
