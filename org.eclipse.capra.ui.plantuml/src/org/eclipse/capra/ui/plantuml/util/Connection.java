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
package org.eclipse.capra.ui.plantuml.util;

import org.eclipse.capra.core.adapters.TraceMetaModelAdapter;
import org.eclipse.emf.ecore.EObject;

/**
 * An minimal abstraction of a traceability link used in
 * {@link TraceMetaModelAdapter}, to retain independence of a concrete trace
 * metamodel.
 * 
 * @author Anthony Anjorin, Salome Maro
 */
public class Connection {
	private EObject source;
	private EObject target;
	private String linkType;

	public Connection(EObject source, EObject target, String linkType) {
		this.source = source;
		this.target = target;
		this.linkType = linkType;
	}

	public EObject getTarget() {
		return target;
	}

	public EObject getSource() {
		return source;
	}

	public String getLinkType() {
		return linkType;
	}
}
