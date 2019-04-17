package org.eclipse.capra.handler.reqif;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.handler.emf.EMFHandler;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.AttributeValueString;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.swt.graphics.Image;

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
		}
		return test;
	}
	
	@Override
	public String getName(Object selection) throws CapraException {
		String name = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object obj = structuredSelection.getFirstElement();
			if(obj instanceof SpecHierarchy){
				SpecHierarchy spec = (SpecHierarchy) obj;
				SpecObject specObj = spec.getObject();
				specObj.getValues();
				for (AttributeValue value : specObj.getValues()) {
					if(value instanceof AttributeValueString){
						AttributeValueString stringValue = (AttributeValueString) value;
						if(stringValue.getDefinition().getLongName().equalsIgnoreCase("Description")){
							name = stringValue.getTheValue();
						}
					}
				}
				if(name == null){
					name = spec.getObject().getIdentifier();
				}
			}
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
		}
		return super.getURI(selection);
	}

}
