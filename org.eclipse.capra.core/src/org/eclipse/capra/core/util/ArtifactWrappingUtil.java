package org.eclipse.capra.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.adapters.PersistenceAdapter;
import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.capra.core.handlers.PriorityHandler;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

public class ArtifactWrappingUtil {
	public static List<EObject> wrap(List<Object> objects, Collection<ArtifactHandler> artifactHandlers,
			EObject artifactWrapperModel) throws CapraException {
		List<EObject> result = new ArrayList<>();
		for (Object obj : objects) {
			EObject eObj = wrap(obj, artifactWrapperModel, artifactHandlers);
			result.add(eObj);
		}

		return result;
	}

	public static List<EObject> wrap(List<Object> objects,ResourceSet resourceSet) throws CapraException {
		Collection<ArtifactHandler> artifactHandlers = ExtensionPointUtil.getArtifactHandlers();
		PersistenceAdapter persistenceAdapter = ExtensionPointUtil.getTracePersistenceAdapter().get();
		EObject artifactWrapperModel = persistenceAdapter.getArtifactWrapperModel(resourceSet);

		return wrap(objects, artifactHandlers, artifactWrapperModel);
	}

	public static EObject wrap(Object obj, EObject artifactWrapperModel, Collection<ArtifactHandler> artifactHandlers)
			throws CapraException {
		List<ArtifactHandler> validArtifactHandlers = artifactHandlers.stream()
				.filter(handler -> handler.canHandleSelection(obj)).collect(Collectors.toList());
		Optional<PriorityHandler> priorityHandler = ExtensionPointUtil.getPriorityHandler();

		if (validArtifactHandlers.size() == 1) {
			return validArtifactHandlers.get(0).getEObjectForSelection(obj, artifactWrapperModel);
		} else if (validArtifactHandlers.size() > 1 && priorityHandler.isPresent()) {
			ArtifactHandler selectedHandler = priorityHandler.get().getSelectedHandler(validArtifactHandlers, obj);
			return selectedHandler.getEObjectForSelection(obj, artifactWrapperModel);
		} else {
			throw new CapraException("No handler for item " + obj + " so it will be ignored.");
		}
	}
}
