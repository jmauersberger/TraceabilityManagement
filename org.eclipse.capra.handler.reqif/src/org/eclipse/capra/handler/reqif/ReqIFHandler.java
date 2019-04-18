package org.eclipse.capra.handler.reqif;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.handler.emf.EMFHandler;
import org.eclipse.capra.handler.reqif.preferences.ReqIFPreferencePage;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.AttributeValueString;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class ReqIFHandler extends EMFHandler {

	public ReqIFHandler() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean canHandleSelection(Object selection) {
		
		boolean test = false;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object obj = structuredSelection.getFirstElement();
			test = (obj instanceof SpecHierarchy || obj instanceof SpecObject)? true : false;
		}else if(selection instanceof SpecHierarchy || selection instanceof SpecObject){
			test = true;
		}
		return test;
	}
	
	@Override
	public String getName(Object selection) throws CapraException {
		String name = null;
		SpecObject specObj = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object obj = structuredSelection.getFirstElement();
			if(obj instanceof SpecHierarchy){
				SpecHierarchy spec = (SpecHierarchy) obj;
				specObj = spec.getObject();
			}
		}else if(selection instanceof SpecHierarchy){
			specObj = ((SpecHierarchy) selection).getObject();
		}else if(selection instanceof SpecObject){
			specObj = (SpecObject)selection;
		}
		if(specObj != null){
			specObj.getValues();
			String idAttribute = Activator.getDefault().getPreferenceStore().getString(ReqIFPreferencePage.IDATTRIBUTE);
//			System.out.println("### " + idAttribute);
			if(idAttribute != null && !idAttribute.isEmpty()){
				for (AttributeValue value : specObj.getValues()) {
					if(value instanceof AttributeValueString){
						AttributeValueString stringValue = (AttributeValueString) value;
						if( stringValue.getDefinition().getLongName().equalsIgnoreCase(idAttribute)){
							name = stringValue.getTheValue();
						}
					}
				}
			}
		}
		if(name == null){
			name = specObj.getIdentifier();
		}
		return name;
	}
	
	@Override
	public Image getIcon(Object selection) throws CapraException {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object obj = structuredSelection.getFirstElement();
			if(obj instanceof SpecHierarchy){
				return super.getIcon(((SpecHierarchy)obj).getObject());
			}
		}else if(selection instanceof SpecHierarchy){
			return super.getIcon(((SpecHierarchy)selection).getObject());
		}else if(selection instanceof SpecObject){
			return super.getIcon((SpecObject)selection);
		}
		return super.getIcon(selection);
	}
	
	@Override
	public EObject getEObjectForSelection(Object selection, EObject artifactModel) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object obj = structuredSelection.getFirstElement();
			if(obj instanceof SpecHierarchy){
				return super.getEObjectForSelection(((SpecHierarchy)obj).getObject(), artifactModel);
			}
		}else if(selection instanceof SpecHierarchy){
			return super.getEObjectForSelection(((SpecHierarchy)selection).getObject(), artifactModel);
		}else if(selection instanceof SpecObject){
			return super.getEObjectForSelection((SpecObject)selection, artifactModel);
		}
		return super.getEObjectForSelection(selection, artifactModel);
	}

	@Override
	public String getURI(Object selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object obj = structuredSelection.getFirstElement();
			if(obj instanceof SpecHierarchy){
				return super.getURI(((SpecHierarchy)obj).getObject());
			}
		}else if(selection instanceof SpecHierarchy){
			return super.getURI(((SpecHierarchy)selection).getObject());
		}else if(selection instanceof SpecObject){
			return super.getURI((SpecObject)selection);
		}
		return super.getURI(selection);
	}
	
	@Override
	public void openEditor(Object obj){
		if (!(obj instanceof EObject))
			return;
		EObject eobj = (EObject) obj;

		try {

			IWorkbench wb = PlatformUI.getWorkbench();
			IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
			IWorkbenchPage page = win.getActivePage();

			String platformUri = getURI(eobj);

			platformUri = platformUri.substring(0, platformUri.indexOf("#"));
//
			platformUri = platformUri.replace("resource/", "");

			Path path = new Path(platformUri);
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path); 

			file.exists();

			IDE.openEditor(page, file);

		} catch (PartInitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
