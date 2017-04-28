package org.eclipse.capra.ui.plantuml.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.capra.core.adapters.TraceLinkAdapter;
import org.eclipse.capra.core.adapters.TraceMetaModelAdapter;
import org.eclipse.emf.ecore.EObject;

public class TransitiveTracesUtil {

	public static List<EObject> transitiveTraces(EObject selectedEObj, EObject traceModel,
			TraceMetaModelAdapter traceModelAdapter) {
		List<EObject> transitiveTracesFromSource = transitiveTracesFromSource(selectedEObj, traceModel,
				traceModelAdapter);
		List<EObject> transitiveTracesToTarget = transitiveTracesToTarget(selectedEObj, traceModel, traceModelAdapter);
		Set<EObject> set = new HashSet<>();
		set.addAll(transitiveTracesFromSource);
		set.addAll(transitiveTracesToTarget);
		ArrayList<EObject> result = new ArrayList<EObject>();
		result.addAll(set);
		return result;
	}

	private static List<EObject> transitiveTracesFromSource(EObject source, EObject traceModel,
			TraceMetaModelAdapter traceMetaModelAdapter) {
		List<EObject> traces = new ArrayList<>();
		transitiveFromSource(source, traceModel, traceMetaModelAdapter, traces);
		return traces;
	}

	private static void transitiveFromSource(EObject source, EObject traceModel,
			TraceMetaModelAdapter traceMetaModelAdapter, List<EObject> traces) {
		List<EObject> directTraces = traceMetaModelAdapter.getTracesFromSource(source, traceModel);
		for (EObject trace : directTraces) {
			if (!traces.contains(trace)) {
				traces.add(trace);
				TraceLinkAdapter traceLinkAdapter = traceMetaModelAdapter.getTraceLinkAdapter(trace);
				List<EObject> targets = traceLinkAdapter.getTargets(trace);
				for (EObject target : targets) {
					transitiveFromSource(target, traceModel, traceMetaModelAdapter, traces);
				}
			}
		}
	}

	private static List<EObject> transitiveTracesToTarget(EObject target, EObject traceModel,
			TraceMetaModelAdapter tradeMetaModelAdapter) {
		List<EObject> accumulator = new ArrayList<>();
		transitiveToTarget(target, traceModel, tradeMetaModelAdapter, accumulator);
		return accumulator;
	}

	private static void transitiveToTarget(EObject target, EObject traceModel,
			TraceMetaModelAdapter traceMetaModelAdapter, List<EObject> traces) {
		List<EObject> directTraces = traceMetaModelAdapter.getTracesToTarget(target, traceModel);
		for (EObject trace : directTraces) {
			if (!traces.contains(trace)) {
				traces.add(trace);
				TraceLinkAdapter traceLinkAdapter = traceMetaModelAdapter.getTraceLinkAdapter(trace);
				List<EObject> sources = traceLinkAdapter.getSources(trace);
				for (EObject source : sources) {
					transitiveToTarget(source, traceModel, traceMetaModelAdapter, traces);
				}
			}
		}
	}

}
