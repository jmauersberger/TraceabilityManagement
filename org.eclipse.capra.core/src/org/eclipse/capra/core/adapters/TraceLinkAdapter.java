package org.eclipse.capra.core.adapters;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

public interface TraceLinkAdapter {
	boolean canAdapt(EClass traceType);

	List<EObject> getSources(EObject trace);

	List<EObject> getTargets(EObject trace);

	String getLinkType();

	boolean canCreateLink(List<EObject> sources, List<EObject> targets);

	EObject createLink(String type, List<EObject> sources, List<EObject> targets);
}
