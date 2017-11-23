package org.eclipse.capra.generic.tracemodels;

import java.util.List;

import org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLink;
import org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLinkMetaModelFactory;
import org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLinkMetaModelPackage;
import org.eclipse.capra.core.adapters.TraceLinkAdapter;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * This adapter provides access to GenericTraceLink traces.
 * 
 * @author Sascha Baumgart
 */
public class GenericTraceLinkAdapter implements TraceLinkAdapter {
	@Override
	public boolean canAdapt(EClass traceType) {
		if (traceType.equals(GenericTraceLinkMetaModelPackage.eINSTANCE.getGenericTraceLink())) {
			return true;
		}
		return false;
	}

	@Override
	public List<EObject> getSources(EObject trace) {
		assert trace instanceof GenericTraceLink;
		GenericTraceLink genericTraceLink = (GenericTraceLink) trace;
		return genericTraceLink.getSources();
	}

	@Override
	public List<EObject> getTargets(EObject trace) {
		assert trace instanceof GenericTraceLink;
		GenericTraceLink genericTraceLink = (GenericTraceLink) trace;
		return genericTraceLink.getTargets();
	}

	@Override
	public String getLinkType() {
		return "Generic Trace";
	}

	@Override
	public boolean canCreateLink(List<EObject> sources, List<EObject> targets) {
		return !sources.isEmpty() && !targets.isEmpty();
	}

	@Override
	public EObject createLink(List<EObject> sources, List<EObject> targets) {
		GenericTraceLink traceLink = GenericTraceLinkMetaModelFactory.eINSTANCE.createGenericTraceLink();
		getSources(traceLink).addAll(sources);
		getTargets(traceLink).addAll(targets);
		return traceLink;
	}
}
