package org.eclipse.capra.ui.linkview.views;

import org.eclipse.capra.core.handlers.ArtifactHandler;

public class ObjectWithHandler {
	private Object obj;
	private ArtifactHandler handler;

	public ObjectWithHandler(Object obj, ArtifactHandler handler) {
		this.obj = obj;
		this.handler = handler;
	}

	public Object getObj() {
		return obj;
	}

	public ArtifactHandler getHandler() {
		return handler;
	}
}