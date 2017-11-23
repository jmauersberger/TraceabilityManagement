package org.eclipse.capra.core.operations;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.util.ArtifactWrappingUtil;
import org.eclipse.emf.ecore.EObject;

public class DeleteTrace extends AbstractCapraOperation {
	private Object source;
	private Object target;

	public DeleteTrace(Object source, Object target) throws CapraException{
		this.source = source;
		this.target = target;
	}

	@Override
	public void execute() throws CapraException {
//		EObject sourceEObj = ArtifactWrappingUtil.wrap(source, artifactWrapperModel, artifactHandlers);
//		EObject targetEObj = ArtifactWrappingUtil.wrap(target, artifactWrapperModel, artifactHandlers);
//		
//		traceAdapter.deleteTrace(sourceEObj, targetEObj, traceModel);
	}

}
