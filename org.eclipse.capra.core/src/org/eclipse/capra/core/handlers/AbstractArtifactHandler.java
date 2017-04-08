package org.eclipse.capra.core.handlers;

import org.eclipse.capra.core.adapters.ArtifactMetaModelAdapter;
import org.eclipse.capra.core.helpers.ExtensionPointHelper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public abstract class AbstractArtifactHandler implements ArtifactHandler {
	@Override
	public EObject getEObjectForSelection(Object obj, EObject artifactModel) {
		ArtifactMetaModelAdapter adapter = ExtensionPointHelper.getArtifactWrapperMetaModelAdapter().get();
		EObject wrapper = adapter.createArtifact(artifactModel, this.getClass().getName(), getURI(obj), getName(obj));
		return wrapper;
	}

	@Override
	public String getName(Object obj) {
		return obj.toString();
	}

	@Override
	public Image getIcon(Object obj) {
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
	}

	@Override
	public String getURI(Object selection) {
		return null;
	}

	@Override
	public Object resolveArtifact(EObject artifact) {
		return null;
	}

	@Override
	public String getObjectTypeName(Object obj) {
		return null;
	}
}
