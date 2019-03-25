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
package org.eclipse.capra.handler.papyrus;

import java.net.URL;
import java.util.List;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.capra.core.util.UIStringUtil;
import org.eclipse.capra.handler.emf.EMFHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.URIConverterImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.impl.EObjectTreeElementImpl;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ResourceUtils;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Stereotype;
import org.polarsys.chess.contracts.profile.chesscontract.util.ContractEntityUtil;

/**
 * A handler to create trace links from and to model elements created in
 * Papyrus.
 */
public class PapyrusHandler extends EMFHandler {

	@Override
	public boolean canHandleSelection(Object selection) {
		boolean test = false;
		
		if (selection instanceof EditPart) {
			
			EObject obj = EMFHelper.getEObject(selection);
			if (obj instanceof NamedElement){
				return true;
			}
			
		}
		
		if (selection instanceof EObject){
			
			try{
				String uri = ((EObject) selection).eResource().getURI().toPlatformString(false).replaceAll(".uml", ".di");
				URI diURI = URI.createFileURI(uri);
				if (URIConverterImpl.INSTANCE.exists(diURI, null)){
					return true;
				}
			}
			catch(Exception ex){
				
			}
		}
		
		
		if(selection instanceof EObjectTreeElementImpl || selection instanceof NamedElement){
			test = true;
		}
		return test;
	}

	@Override
	public EObject getEObjectForSelection(Object selection, EObject artifactModel) {
		// Returns the EObject corresponding to the input object if the input is
		// an EObject, or if it is Adaptable to an EObject
		
		if (selection instanceof EditPart) {
			EObject eobj = EMFHelper.getEObject(selection);
			if (eobj instanceof Element){
				if (ContractEntityUtil.getInstance().isContract((Element) eobj)){
					 return  (EObject) ContractEntityUtil.getInstance().getContract((Class) eobj);
				}
				return eobj;
			}
		}
		
		if(selection instanceof EObjectTreeElementImpl){
			EObject sel = ((EObjectTreeElementImpl) selection).getEObject();
			if (sel instanceof Element){
				if (ContractEntityUtil.getInstance().isContract((Element) sel)){
					 return  (EObject) ContractEntityUtil.getInstance().getContract((Class) sel);
				}
			}
			return sel;
		}else{
			
			if (selection instanceof EObject) {
				return EObject.class.cast(selection);
			} else if (selection instanceof IAdaptable) {
				IAdaptable a = (IAdaptable) selection;
				if (a.getAdapter(EObject.class) != null) {
					return a.getAdapter(EObject.class);
				}
			} 
			
			
		}
		return null;
	}

	@Override
	public String getName(Object selection) throws CapraException {
		if(selection instanceof EObjectTreeElementImpl){
			EObject eobj = ((EObjectTreeElementImpl) selection).getEObject();
			//return UIStringUtil.createUIString(eObject);
			if (eobj instanceof NamedElement){
				return getText((NamedElement) eobj);
			}
			
			
		}else{
			if (selection instanceof EditPart) {
				EObject eobj = EMFHelper.getEObject(selection);
				if (eobj instanceof NamedElement){
//					if (ContractEntityUtil.getInstance().isContract((Element) eobj)){
//						 return  ((NamedElement) eobj).getName();
//					}
					//return ((NamedElement) eobj).getName();
					return getText((NamedElement) eobj);
				}
			}
		}
		return super.getName(selection);
	}

	@Override
	public String getURI(Object selection) {
		if(selection instanceof EObjectTreeElementImpl){
			EObject eObject = ((EObjectTreeElementImpl) selection).getEObject();
			return EcoreUtil.getURI(eObject).toString();
		}else{
			if (selection instanceof EditPart) {
				EObject eobj = EMFHelper.getEObject(selection);
				return EcoreUtil.getURI(eobj).toString();
			}
		}
		
		return super.getURI(selection);
	
	}

	@Override
	public Object resolveArtifact(EObject artifact) {
		return artifact;
	}
	
	public String getText(NamedElement element) {

		//LabelProviderService service = (LabelProviderService) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getService(LabelProviderService.class);
		ILabelProvider labelProvider = null;
		try {
			labelProvider = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class,element).getLabelProvider();
		} catch (ServiceException e) {
			System.out.println("catched exception:");
			e.printStackTrace();
		}

		String stereos = "";
		
		//display applied stereotypes
		EList<Stereotype> listStereo = element.getAppliedStereotypes();
		for (Stereotype stereo : listStereo){
			stereos += "<<"+stereo.getName()+">>";
		}
		
		
		if (labelProvider != null){
			String full = labelProvider.getText(element);

			if (full.contains("."))
				return stereos +" "+full.substring(full.lastIndexOf(".")+1);
			return stereos +" " +full;
		}
		return "";
	}
	
	@Override
	public Image getIcon(Object obj) throws CapraException {
		if (obj == null || !(obj instanceof EObject))
			return super.getIcon(obj);
		ILabelProvider labelProvider = null;
		try {
			labelProvider = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class,(EObject) obj).getLabelProvider();
		} catch (ServiceException e) {
			
			return super.getIcon(obj);
		}
		if (labelProvider != null){
			return labelProvider.getImage(obj);
		}
		return null;
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
		  
		  platformUri = platformUri.replace(".uml", ".di");
		  
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
