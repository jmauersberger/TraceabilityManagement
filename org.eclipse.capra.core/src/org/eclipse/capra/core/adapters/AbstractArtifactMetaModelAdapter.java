package org.eclipse.capra.core.adapters;

import java.util.Collection;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.emf.ecore.EObject;

/**
 * Base class for the definition of a custom {@link ArtifactMetaModelAdapter}.
 * Implements a simple strategy to retrieve artifact handlers through the
 * registered extensions.
 */
public abstract class AbstractArtifactMetaModelAdapter implements ArtifactMetaModelAdapter {

	@Override
	public ArtifactHandler getArtifactHandler(EObject artifact) throws CapraException {
		Collection<ArtifactHandler> artifactHandlers = ExtensionPointUtil.getArtifactHandlers();
		for (ArtifactHandler artifactHandler : artifactHandlers) {
			if (artifactHandler.canHandleSelection(artifact)) {
				return artifactHandler;
			}
		}

		return null;
	}

}
