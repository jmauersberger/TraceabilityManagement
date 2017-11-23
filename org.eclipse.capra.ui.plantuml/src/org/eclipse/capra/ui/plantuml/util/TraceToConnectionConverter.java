package org.eclipse.capra.ui.plantuml.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.adapters.TraceLinkAdapter;
import org.eclipse.capra.core.adapters.TraceMetaModelAdapter;
import org.eclipse.emf.ecore.EObject;

public class TraceToConnectionConverter {
	public static List<Connection> convert(List<EObject> traces, TraceMetaModelAdapter traceModelAdapter) throws CapraException {
		List<Connection> result = new ArrayList<Connection>();
		for (EObject trace : traces) {
			TraceLinkAdapter traceLinkAdapter = traceModelAdapter.getTraceLinkAdapter(trace);
			for (EObject source : traceLinkAdapter.getSources(trace)) {
				for (EObject target : traceLinkAdapter.getTargets(trace)) {
					Connection connection = new Connection(source, target, traceLinkAdapter.getLinkType());
					result.add(connection);
				}
			}
		}
		return result;
	}
}
