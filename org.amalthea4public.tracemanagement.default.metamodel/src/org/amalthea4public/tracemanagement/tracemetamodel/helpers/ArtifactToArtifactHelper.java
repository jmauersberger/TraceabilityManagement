package org.amalthea4public.tracemanagement.tracemetamodel.helpers;

import java.util.List;
import java.util.Map;

import org.amalthea4public.tracemanagement.generic.artifacts.ArtifactWrapper;
import org.amalthea4public.tracemanagement.tracemetamodel.ArtifactToArtifact;
import org.amalthea4public.tracemanagement.tracemetamodel.TraceElement;
import org.amalthea4public.tracemanagement.tracemetamodel.TracemetamodelPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

public class ArtifactToArtifactHelper extends TraceTypeHelper {

	@Override
	public boolean fitsSelection(List<EObject> selection) {
		return selection.size() == 2 
			&& selection.get(0) instanceof ArtifactWrapper 
			&& selection.get(1) instanceof ArtifactWrapper;
	}

	@Override
	public EClass getType() {
		return TracemetamodelPackage.eINSTANCE.getArtifactToArtifact();
	}

	@Override
	public void initialise(EObject trace, List<EObject> selection) {
		if(trace instanceof ArtifactToArtifact){
			ArtifactToArtifact a2a = (ArtifactToArtifact) trace;
			a2a.setSource((ArtifactWrapper) selection.get(0));
			a2a.setTarget((ArtifactWrapper) selection.get(1));
		}
	}

	@Override
	public void addConnectedElements(EObject element, TraceElement trace, Map<EObject, List<EObject>> traces) {
		if (trace instanceof ArtifactToArtifact) {
			ArtifactToArtifact t = (ArtifactToArtifact) trace;
			addConnectedElementsToSourceAndTarget(element, trace, t.getSource(), t.getTarget(), traces);
		}
	}

	@Override
	public boolean isRelevant(TraceElement trace, EObject firstElement, EObject secondElement) {
		if (trace instanceof ArtifactToArtifact) {
			ArtifactToArtifact t = (ArtifactToArtifact) trace;
			return isRelevantForSourceAndTarget(t.getSource(), t.getTarget(), firstElement, secondElement);
		}
		
		return false;
	}
} 