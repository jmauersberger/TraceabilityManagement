package org.eclipse.capra.core.operations;

import java.util.Collection;
import java.util.List;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.adapters.ArtifactMetaModelAdapter;
import org.eclipse.capra.core.adapters.PersistenceAdapter;
import org.eclipse.capra.core.adapters.TraceLinkAdapter;
import org.eclipse.capra.core.adapters.TraceMetaModelAdapter;
import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.capra.core.handlers.PriorityHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public abstract class AbstractCapraOperation {
	protected ResourceSet resourceSet = new ResourceSetImpl();
	protected Collection<ArtifactHandler> artifactHandlers = ExtensionPointUtil.getArtifactHandlers();
	protected ArtifactMetaModelAdapter artifactMetaModelAdapter = ExtensionPointUtil
			.getArtifactWrapperMetaModelAdapter();
	protected TraceMetaModelAdapter traceMetaModelAdapter = ExtensionPointUtil.getTraceMetamodelAdapter();
	protected PersistenceAdapter persistenceAdapter = ExtensionPointUtil.getTracePersistenceAdapter();
	protected PriorityHandler priorityHandler = ExtensionPointUtil.getPriorityHandler();
	protected List<TraceLinkAdapter> traceLinkAdapter = ExtensionPointUtil.getTraceLinkAdapters();

	protected EObject traceModel;
	protected EObject artifactWrapperModel;

	public AbstractCapraOperation() throws CapraException {
		traceModel = persistenceAdapter.getTraceModel(resourceSet);
		artifactWrapperModel = persistenceAdapter.getArtifactWrapperModel(resourceSet);
	}

	public abstract void execute() throws CapraException;
}
