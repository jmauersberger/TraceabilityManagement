package org.eclipse.capra.core.operations;

import java.util.List;
import java.util.Optional;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.util.ArtifactWrappingUtil;
import org.eclipse.emf.ecore.EObject;

public class CreateConnection extends AbstractCapraOperation {
	private List<Object> sources;
	private List<Object> targets;
	private String traceType;

	public CreateConnection(List<Object> sources, List<Object> targets, String traceType) throws CapraException {
		this.sources = sources;
		this.targets = targets;
		this.traceType = traceType;
	}

	@Override
	public void execute() throws CapraException {
		EObject traceModel = persistenceAdapter.getTraceModel(resourceSet);
		EObject artifactModel = persistenceAdapter.getArtifactWrapperModel(resourceSet);

		List<EObject> wrappedSources = ArtifactWrappingUtil.wrap(sources, artifactHandlers, artifactModel);
		List<EObject> wrappedTargets = ArtifactWrappingUtil.wrap(targets, artifactHandlers, artifactModel);

		Optional<EObject> traceOpt = traceLinkMetaModelAdapters.stream()
				.filter(adapter -> adapter.canCreateLink(wrappedSources, wrappedTargets))
				.map(adapter -> adapter.createLink(traceType, wrappedSources, wrappedTargets)).findFirst();

		if (traceOpt.isPresent()) {
			traceMetaModelAdapter.addTrace(traceOpt.get(), traceModel);
			persistenceAdapter.saveTracesAndArtifacts(traceModel, artifactModel);
		} else {
			throw new CapraException("Could not create the trace.");
		}
	}

}
