package org.eclipse.capra.core.operations;

import java.util.List;
import java.util.Map;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.adapters.TraceLinkAdapter;
import org.eclipse.capra.core.adapters.TraceMetaModelAdapter;
import org.eclipse.capra.core.util.ArtifactWrappingUtil;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.capra.core.util.TraceLinkAttribute;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.eresource.CDOResourceFactory;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

//modified by Intecs: attributes

public class CreateConnection extends AbstractCapraOperation {
	private List<Object> sources;
	private List<Object> targets;
	private String traceType;
	private List<TraceLinkAttribute> attributes;
	private EObject traced; 
	private EObject traceModel;
	private EObject artifactModel;

	public CreateConnection(List<Object> sources, List<Object> targets, String traceType) throws CapraException {
		this.sources = sources;
		this.targets = targets;
		this.traceType = traceType;
		this.attributes = null;
	}
	
	public CreateConnection(List<Object> sources, List<Object> targets, String traceType, List<TraceLinkAttribute> attributes) throws CapraException {
		this.sources = sources;
		this.targets = targets;
		this.traceType = traceType;
		this.attributes = attributes;
	}

	@Override
	public void execute() throws CapraException {
		traceModel = persistenceAdapter.getTraceModel(resourceSet);
		artifactModel = persistenceAdapter.getArtifactWrapperModel(resourceSet);

		List<EObject> wrappedSources = ArtifactWrappingUtil.wrap(sources, artifactHandlers, artifactModel);
		List<EObject> wrappedTargets = ArtifactWrappingUtil.wrap(targets, artifactHandlers, artifactModel);
		
		Map<String, Object> map = 
				Resource.Factory.Registry.INSTANCE.getProtocolToFactoryMap();
				if (!map.containsKey(CDONet4jUtil.PROTOCOL_TCP))
				{
				map.put(CDONet4jUtil.PROTOCOL_TCP, CDOResourceFactory.INSTANCE);
				}
		
		EObject obj = wrappedTargets.get(0);
		Resource res = obj.eResource();
		//CDOResource cdores = (CDOResource) res;
//		URI uri = res.getURI();

		EObject link = null;
		for (TraceLinkAdapter adapter : traceLinkAdapter) {
			if (adapter.getLinkType().equalsIgnoreCase(traceType)) {
				if (adapter.canCreateLink(wrappedSources, wrappedTargets)) {							
					link = adapter.createLink(wrappedSources, wrappedTargets, attributes);
					break;
				}
			}
		}

		if (link != null) {
			traceMetaModelAdapter.addTrace(link, traceModel);
			persistenceAdapter.saveTracesAndArtifacts(traceModel, artifactModel);
			traced = link;
		} else {
			throw new CapraException("Could not create the trace.");
		}
	}
	
	public void undo() throws CapraException {
		
		traceMetaModelAdapter.deleteTrace(traced, traceModel);
		persistenceAdapter.saveTracesAndArtifacts(traceModel, artifactModel);
	}
	
	public void redo() throws CapraException {
		
		traceMetaModelAdapter.addTrace(traced, traceModel);
		persistenceAdapter.saveTracesAndArtifacts(traceModel, artifactModel);
	}

}
