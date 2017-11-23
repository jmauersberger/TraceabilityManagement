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
package org.eclipse.capra.ui.plantuml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.util.UIStringUtil;
import org.eclipse.capra.ui.plantuml.util.Connection;
import org.eclipse.emf.ecore.EObject;

/**
 * Helper class for generating PlantUML diagrams from a collection of
 * {@link Connection}
 * 
 * @author Anthony Anjorin, Salome Maro
 */
public class Connections {

	private List<Connection> connections;
	private EObject origin;

	private Set<EObject> allObjects;
	private Map<EObject, String> object2Id;
	private Map<String, String> id2Label;

	Connections(List<Connection> connections, EObject selectedObject) {
		this.connections = connections;
		origin = selectedObject;

		allObjects = new LinkedHashSet<>();
		allObjects.add(selectedObject);
		connections.forEach(c -> allObjects.add(c.getTarget()));
		connections.forEach(c -> allObjects.add(c.getSource()));

		object2Id = new LinkedHashMap<>();
		int i = 0;
		for (EObject o : allObjects) {
			object2Id.put(o, "o" + i++);
		}

		id2Label = new LinkedHashMap<>();
		allObjects.forEach(o -> {
			String id = object2Id.get(o);
			String label;
			try {
				label = UIStringUtil.createUIString(o);
			} catch (CapraException e) {
				label = "error while creating label";
				e.printStackTrace();
			}
			id2Label.put(id, label);
		});
	}

	public String originLabel() {
		return id2Label.get(object2Id.get(origin));
	}

	public String originId() {
		return object2Id.get(origin);
	}

	public Collection<String> objectIdsWithoutOrigin() {
		Collection<String> all = new ArrayList<>();
		all.addAll(object2Id.values());
		all.remove(originId());
		return all;
	}

	public String label(String id) {
		return id2Label.get(id);
	}

	private String createArrowString(String sourceID, String targetID, String linkType) {
		StringBuilder sb = new StringBuilder();
		sb.append(sourceID);
		sb.append("-->");
		sb.append(targetID);
		sb.append(':');
		sb.append(linkType);
		return sb.toString();
	}

	public List<String> arrows() {
		List<String> arrows = new ArrayList<>();
		for (Connection connection : connections) {
			EObject source = connection.getSource();
			EObject target = connection.getTarget();
			if (!target.equals(connection.getSource())) {
				String sourceID = object2Id.get(source);
				String targetID = object2Id.get(target);
				String linkType = connection.getLinkType();
				String arrowString = createArrowString(sourceID, targetID, linkType);
				arrows.add(arrowString);
			}
		}
		return arrows;
	}
}
