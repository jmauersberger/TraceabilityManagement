/*******************************************************************************
 * Copyright (c) 2016 Chalmers | University of Gothenburg, rt-labs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *  
 *   Contributors:
 *      Chalmers | University of Gothenburg and rt-labs - initial API and implementation and/or initial documentation
 *******************************************************************************/
package org.eclipse.capra.core.adapters;

import java.util.List;

import org.eclipse.capra.core.CapraException;
import org.eclipse.emf.ecore.EObject;

/**
 * This interface defines all functionality that must be implemented to support
 * a specific trace metamodel. This enables swapping the concept of what a
 * "trace" is, as long as these methods can be implemented.
 * 
 * @author Anthony Anjorin, Salome Maro, Sascha Baumgart
 *
 */
public interface TraceMetaModelAdapter {

	/**
	 * Creates a new instance of the tracing model.
	 * 
	 * @return the model
	 */
	EObject createModel();

	/**
	 * Used to retrieve a set of types of traces that can be created for the given
	 * selection of objects in the Eclipse workspace
	 * 
	 * @param selection
	 *            The selection of objects the user has made and wants to create a
	 *            trace for in the Eclipse workspace
	 * @return A collection of possible types of traces that can be created for the
	 *         given selection
	 * @throws CapraException Something went wrong
	 */
	List<TraceLinkAdapter> getAvailableTraceTypes(List<EObject> sources, List<EObject> targets) throws CapraException;

	/**
	 * Gets the tracelink adapter for the specified trace.
	 * 
	 * @param the
	 *            trace
	 * @return the tracelink adapter
	 * @throws CapraException Something went wrong
	 */
	TraceLinkAdapter getTraceLinkAdapter(EObject trace) throws CapraException;

	/**
	 * Used to create a trace of the given type
	 * 
	 * @param traceType
	 *            The type of the trace to be created
	 * @param traceModel
	 *            The root of the trace model that should contain the trace type. If
	 *            this is empty, then a new root is to be created and returned.
	 * @param selection
	 *            Objects to create the trace for
	 * @return root of trace model that now contains the newly created trace
	 * @throws CapraException Something went wrong
	 */
	void addTrace(EObject trace, EObject traceModel) throws CapraException;

	/**
	 * Used to delete a trace
	 * 
	 * @param traceModel
	 *            Trace model to delete from
	 * @param first
	 *            First object
	 * @param second
	 *            Second object
	 * @throws CapraException Something went wrong
	 */
	void deleteTrace(EObject traceToDelete, EObject traceModel) throws CapraException;

	/**
	 * Decide if two objects are connected according to the given trace model
	 * 
	 * @param source
	 *            First object
	 * @param target
	 *            Second object
	 * @param traceModel
	 *            Trace model to base decision on
	 * @return <code>true</code> if object are connected, <code>false</code>
	 *         otherwise
	 * @throws CapraException Something went wrong
	 */
	List<EObject> getTracesBetween(EObject source, EObject target, EObject traceModel) throws CapraException;

	/**
	 * Get all traces with the specified source as the source element.
	 * 
	 * @param source
	 *            the source element
	 * @param traceModel
	 *            the tracemodel containing the traces
	 * @return list of traces
	 * @throws CapraException Something went wrong
	 */
	List<EObject> getTracesFromSource(EObject source, EObject traceModel) throws CapraException;

	/**
	 * Get all traces with the specified target as the target element.
	 * 
	 * @param target
	 *            the target element
	 * @param traceModel
	 *            the tracemodel containing the traces
	 * @return list of traces
	 * @throws CapraException Something went wrong
	 */
	List<EObject> getTracesToTarget(EObject target, EObject traceModel) throws CapraException;
	
	/**
	 * Get all traces related to the given adapter
	 * 
	 * @param adapter: the TraceLinkAdapter
	 * @param traceModel: the tracemodel containing the traces
	 * @return list of traces
	 * @throws CapraException
	 *
	List<EObject> getTraces(TraceLinkAdapter adapter, EObject traceModel) throws CapraException;*/
	
	/**
	 * Get all the traces
	 * 
	 * @param traceModel
	 * @return list of traces
	 * @throws CapraException
	 */
	List<EObject> getTraces(EObject traceModel) throws CapraException;
	
}
