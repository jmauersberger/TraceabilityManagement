package org.eclipse.capra.core.handlers;

import org.eclipse.capra.core.adapters.ArtifactMetaModelAdapter;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public abstract class AbstractArtifactHandler implements ArtifactHandler {
	@Override
	public EObject getEObjectForSelection(Object obj, EObject artifactModel) {
		ArtifactMetaModelAdapter adapter = ExtensionPointUtil.getArtifactWrapperMetaModelAdapter().get();
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
	public String getObjectTypeName(Object obj) {
		Class<?>[] interfaces = obj.getClass().getInterfaces();
		if (interfaces.length > 0) {
			Class<?> interface1 = interfaces[0];
			return interface1.getSimpleName();
		}

		return obj.getClass().getSimpleName();
	}
}
