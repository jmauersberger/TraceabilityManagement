package org.amalthea4public.tracemanagement.generic.adapters;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

public interface TraceMetamodelAdapter {

	/**
	 * Used to retrieve a set of types of traces that can be created for the
	 * given selection of objects in the Eclipse workspace
	 * 
	 * @param selection
	 *            The selection of objects the user has made and wants to create
	 *            a trace for in the Eclipse workspace
	 * @return A collection of possible types of traces that can be created for
	 *         the given selection
	 */
	Collection<EClass> getAvailableTraceTypes(List<EObject> selection);

	/**
	 * Used to create a trace of the given type
	 * 
	 * @param traceType
	 *            The type of the trace to be created
	 * @param traceModel
	 *            The root of the trace model that should contain the trace
	 *            type. If this is empty, then a new root is to be created and
	 *            returned.
	 * @param selection
	 *            Objects to create the trace for
	 * @return root of trace model that now contains the newly created trace
	 */
	EObject createTrace(EClass traceType, Optional<EObject> traceModel, List<EObject> selection);
	
	//FIXME[Salome]:  Document me
	boolean isThereATraceBetween(EObject first, EObject second, Optional<EObject> traceModel);
	
	//FIXME[Salome]:  Document me
	Map<EObject, List<EObject>> getConnectedElements(EObject element, Optional<EObject> traceModel); 
}