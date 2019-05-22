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
package org.eclipse.capra.handler.cdo;

import java.util.List;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.handlers.AbstractArtifactHandler;
import org.eclipse.capra.core.util.ExtensionPointUtil;
import org.eclipse.capra.handler.emf.EMFHandler;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.dawn.preferences.DawnRemotePreferencePage;
import org.eclipse.emf.cdo.dawn.preferences.PreferenceConstants;
import org.eclipse.emf.cdo.dawn.ui.DawnEditorInput;
import org.eclipse.emf.cdo.dawn.util.connection.CDOConnectionUtil;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.eresource.impl.CDOResourceFolderImpl;
import org.eclipse.emf.cdo.internal.net4j.CDONet4jSessionImpl;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.ui.CDOEditorUtil;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.internal.cdo.view.CDOViewImpl;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;


/**
 * Handler to allow tracing to and from arbitrary files in the file system.
 */
@SuppressWarnings("restriction")
public class CdoHandler extends EMFHandler {

	@Override
	public boolean canHandleSelection(Object obj) {
		
		if (obj instanceof EditPart) {
			
			EObject eobj = EMFHelper.getEObject(obj);
			Resource res = eobj.eResource();
			if (res instanceof CDOResource)
				return true;
		}
		
		if (obj instanceof EObject){
			if (obj instanceof CDOObject){
				return true;
			}
			return ((EObject)obj).eResource() instanceof CDOResource;
		}
		
		return obj instanceof CDOViewImpl || obj instanceof CDONet4jSessionImpl || obj instanceof CDOResourceFolderImpl;
	}

	@Override
	public String getName(Object obj) throws CapraException {
		if (obj instanceof EditPart) {
			EObject eobj = EMFHelper.getEObject(obj);
			return super.getName(eobj);
		}
		if (obj instanceof EObject){
			return super.getName(obj);
		}
		
		if (obj instanceof CDOViewImpl) {
			CDOViewImpl cdoView = (CDOViewImpl) obj;
			return cdoView.toString();
		} else if (obj instanceof CDONet4jSessionImpl) {
			CDONet4jSessionImpl session = (CDONet4jSessionImpl) obj;
			return session.getRepositoryName();
		} else if (obj instanceof CDOResourceFolderImpl) {
			CDOResourceFolderImpl folder = (CDOResourceFolderImpl) obj;
			return folder.getName();
		}
		return "";
	}

	@Override
	public String getURI(Object obj) {
		
		if (obj instanceof EditPart) {
			EObject eobj = EMFHelper.getEObject(obj);
			return super.getURI(eobj);
		}
		
		if (obj instanceof EObject)
			return super.getURI(obj);
		
		if (obj instanceof CDOViewImpl) {
			CDOViewImpl cdoView = (CDOViewImpl) obj;
			return "cdoview://" + cdoView.getViewID();
		} else if (obj instanceof CDONet4jSessionImpl) {
			CDONet4jSessionImpl session = (CDONet4jSessionImpl) obj;
			return session.getRepositoryName();
		}
		if (obj instanceof CDOResourceFolderImpl) {
			CDOResourceFolderImpl folder = (CDOResourceFolderImpl) obj;
			return folder.getURI().toString();
		}
		return "";
	}

	@Override
	public Image getIcon(Object obj) throws CapraException {

		return super.getIcon(obj);
	}

	@Override
	public String getObjectTypeName(Object obj) throws CapraException {
		return super.getObjectTypeName(obj);
	}

	@Override
	public Object resolveArtifact(EObject artifact) {
		return null;
	}
	
	@Override
	public EObject getEObjectForSelection(Object selection, EObject artifactModel) {
		// Returns the EObject corresponding to the input object if the input is
		// an EObject, or if it is Adaptable to an EObject
		
		if (selection instanceof EditPart) {
			return EMFHelper.getEObject(selection);
		}
		return super.getEObjectForSelection(selection, artifactModel);
	}
	
	
	@Override
	public void openEditor(Object obj){
		if (obj instanceof CDOObject){
			CDOObject cdoobj = (CDOObject) obj;
			
			if (cdoobj.cdoDirectResource() == null){
				//try getting the resource
				String server = PreferenceConstants.getServerName();
				String repoName = PreferenceConstants.getRepositoryName();
				CDOConnectionUtil.instance.init(repoName,PreferenceConstants.getProtocol(),	server);
				CDOSession session = CDOConnectionUtil.instance.getCurrentSession();
				CDOView view = CDOConnectionUtil.instance.openView(session);
				
				URI selURI = EcoreUtil.getURI(cdoobj);
				
				//URI uri = URI.createURI("cdo.net4j.tcp://"+server+"/"+repoName+selURI.path()+"_diagram");
				
				URI uri = null;
				ResourceSet resourceSet = new ResourceSetImpl();
				CDOResource resource = null;
				
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				boolean editorFound = false;
				
				if (!selURI.path().endsWith("_diagram")){
					//try to see if a diagram exists
					uri = URI.createURI("cdo://"+repoName+selURI.path()+"_diagram");
					resource = (CDOResource)resourceSet.getResource(uri, true);
					if (resource.isExisting()){
						String[] editors = CDOEditorUtil.getAllEditorIDs(resource);
						for (String e : editors){
							if (e.equals("org.eclipse.opencert.sam.arg.arg.diagram.part.DawnArgDiagramEditor")){
								editorFound = true;
								openEditor(page, view, uri.path(), uri, "org.eclipse.opencert.sam.arg.arg.diagram.part.DawnArgDiagramEditor");
								break;
							}
						}
					}
				}
				
				if (! editorFound) {
					//open the default editor
					uri = URI.createURI("cdo://"+repoName+selURI.path());
					resource = (CDOResource)resourceSet.getResource(uri, true);
					CDOEditorUtil.openEditor(page, resource);
				
				}
				
				return;
			}
			
		}
	}
	
	
	public static void openEditor(final IWorkbenchPage page, final CDOView view, final String resourcePath, URI uri, String editorID)
	  {
	    Display display = page.getWorkbenchWindow().getShell().getDisplay();
	    display.asyncExec(new Runnable()
	    {
	      public void run()
	      {
	        try
	        {
	          IEditorReference[] references = CDOEditorUtil.findEditor(page, view, resourcePath);
	          if (references.length != 0)
	          {
	            IEditorPart editor = references[0].getEditor(true);
	            page.activate(editor);
	          }
	          else
	          {
	            IEditorInput input = new DawnEditorInput(uri);//createCDOEditorInput(view, resourcePath, false);
	            page.openEditor(input, editorID);
	          }
	        }
	        catch (Exception ex)
	        {
	        	ex.printStackTrace();
	        }
	      }
	    });
	  }
	
}
