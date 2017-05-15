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
package org.eclipse.capra.generic.tracemodels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.capra.GenericTraceMetaModel.GenericTraceMetaModelFactory;
import org.eclipse.capra.GenericTraceMetaModel.GenericTraceModel;
import org.eclipse.capra.core.adapters.TraceLinkAdapter;
import org.eclipse.capra.core.adapters.TraceMetaModelAdapter;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper;

/**
 * Provides generic functionality to deal with traceability meta models.
 */
public class GenericTraceMetaModelAdapter implements TraceMetaModelAdapter {

	@Override
	public EObject createModel() {
		// TODO: Make this more dynamic.
		return GenericTraceMetaModelFactory.eINSTANCE.createGenericTraceModel();
	}

	@Override
	public List<TraceLinkAdapter> getAvailableTraceTypes(List<EObject> sources, List<EObject> targets) {
		if (!sources.isEmpty() && !targets.isEmpty()) {
			return Collections.singletonList(new GenericTraceLinkAdapter());
		} else {
			return Collections.<TraceLinkAdapter>emptyList();
		}
	}

	@Override
	public List<EObject> getTracesBetween(EObject source, EObject target, EObject traceModel) {
		List<EObject> resultTraces = new ArrayList<>();

		EcoreUtil.EqualityHelper helper = new EqualityHelper();
		if (traceModel instanceof GenericTraceModel) {
			GenericTraceModel m = (GenericTraceModel) traceModel;
			List<TraceLinkAdapter> traceLinkAdapters = ExtensionPointUtil.getTraceLinkAdapters();

			for (EObject trace : m.getTraces()) {
				for (TraceLinkAdapter traceLinkAdapter : traceLinkAdapters) {
					if (traceLinkAdapter.canAdapt(trace.eClass())) {
						for (EObject traceSource : traceLinkAdapter.getSources(trace)) {
							if (helper.equals(traceSource, source)) {
								for (EObject traceTarget : traceLinkAdapter.getTargets(trace)) {
									if (helper.equals(traceTarget, target)) {
										resultTraces.add(trace);
									}
								}
							}
						}

						// if
						// (traceLinkAdapter.getSources(trace).contains(source)
						// &&
						// traceLinkAdapter.getTargets(trace).contains(target))
						// {
						// resultTraces.add(trace);
						// }
					}
				}
			}
		}

		return resultTraces;
	}

	@Override
	public List<EObject> getTracesFromSource(EObject source, EObject traceModel) {
		List<EObject> resultTraces = new ArrayList<>();
		if (traceModel instanceof GenericTraceModel) {
			GenericTraceModel m = (GenericTraceModel) traceModel;
			List<TraceLinkAdapter> traceLinkAdapters = ExtensionPointUtil.getTraceLinkAdapters();

			for (EObject trace : m.getTraces()) {
				for (TraceLinkAdapter traceLinkAdapter : traceLinkAdapters) {
					if (traceLinkAdapter.canAdapt(trace.eClass())) {
						if (traceLinkAdapter.getSources(trace).contains(source)) {
							resultTraces.add(trace);
						}
					}
				}
			}
		}

		return resultTraces;
	}

	@Override
	public List<EObject> getTracesToTarget(EObject target, EObject traceModel) {
		List<EObject> resultTraces = new ArrayList<>();
		if (traceModel instanceof GenericTraceModel) {
			GenericTraceModel m = (GenericTraceModel) traceModel;
			List<TraceLinkAdapter> traceLinkAdapters = ExtensionPointUtil.getTraceLinkAdapters();

			for (EObject trace : m.getTraces()) {
				for (TraceLinkAdapter traceLinkAdapter : traceLinkAdapters) {
					if (traceLinkAdapter.canAdapt(trace.eClass())) {
						if (traceLinkAdapter.getTargets(trace).contains(target)) {
							resultTraces.add(trace);
						}
					}
				}
			}
		}

		return resultTraces;
	}

	@Override
	public void addTrace(EObject trace, EObject traceModel) {
		GenericTraceModel genericTraceModel = (GenericTraceModel) traceModel;
		genericTraceModel.getTraces().add(trace);
	}

	@Override
	public void deleteTrace(EObject traceToDelete, EObject traceModel) {
		GenericTraceModel genericTraceModel = (GenericTraceModel) traceModel;
		genericTraceModel.getTraces().remove(traceToDelete);
	}

	@Override
	public TraceLinkAdapter getTraceLinkAdapter(EObject trace) {
		List<TraceLinkAdapter> traceLinkAdapters = ExtensionPointUtil.getTraceLinkAdapters();
		for (TraceLinkAdapter adapter : traceLinkAdapters) {
			if (adapter.canAdapt(trace.eClass())) {
				return adapter;
			}
		}
		return null;
	}

}
