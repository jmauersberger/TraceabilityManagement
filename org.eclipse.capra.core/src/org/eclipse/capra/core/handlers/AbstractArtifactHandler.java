package org.eclipse.capra.core.handlers;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.adapters.ArtifactMetaModelAdapter;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public abstract class AbstractArtifactHandler implements ArtifactHandler {
	@Override
	public EObject getEObjectForSelection(Object obj, EObject artifactModel) throws CapraException {
		ArtifactMetaModelAdapter adapter = ExtensionPointUtil.getArtifactWrapperMetaModelAdapter();
		String className = this.getClass().getName();
		String uri = getURI(obj);
		String name = getName(obj);

		EObject wrapper = adapter.createArtifact(artifactModel, className, uri, name);
		return wrapper;
	}

	@Override
	public String getName(Object obj) throws CapraException {
		return obj.toString();
	}

	@Override
	public Image getIcon(Object obj) throws CapraException {
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
	}

	@Override
	public String getObjectTypeName(Object obj) throws CapraException {
		
		try{
			if (obj == null || obj.getClass() == null)
				return "";
			Class<?>[] interfaces = obj.getClass().getInterfaces();
			if (interfaces.length > 0) {
				Class<?> interface1 = interfaces[0];
				return interface1.getSimpleName();
			}
	
			return obj.getClass().getSimpleName();
		}catch (Exception ex){
			ex.printStackTrace();
			return "";
		}
	}
}
