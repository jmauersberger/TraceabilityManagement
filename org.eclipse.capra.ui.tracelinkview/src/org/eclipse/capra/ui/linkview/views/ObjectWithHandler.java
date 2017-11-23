package org.eclipse.capra.ui.linkview.views;

import org.eclipse.capra.core.handlers.ArtifactHandler;

class ObjectWithHandler {
	Object o;
	ArtifactHandler handler;

	public ObjectWithHandler(Object o, ArtifactHandler handler) {
		this.o = o;
		this.handler = handler;
	}
}