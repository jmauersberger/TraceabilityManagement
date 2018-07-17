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
package org.eclipse.capra.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.adapters.ArtifactMetaModelAdapter;
import org.eclipse.capra.core.adapters.PersistenceAdapter;
import org.eclipse.capra.core.adapters.TraceLinkAdapter;
import org.eclipse.capra.core.adapters.TraceMetaModelAdapter;
import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.capra.core.handlers.PriorityHandler;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * Provides functionality to work with relevant Capra extension points.
 */
public class ExtensionPointUtil {

	private static final String TRACE_ID = "org.eclipse.capra.configuration.TraceLinkMetaModelAdapter";
	private static final String TRACE_CONFIG = "class";
	private static final String TRACE_LINK_ID = "org.eclipse.capra.configuration.TraceLinkAdapter";
	private static final String TRACE__LINK_CONFIG = "class";
	private static final String PERSISTENCE_ID = "org.eclipse.capra.configuration.persistenceHandler";
	private static final String PERSISTENCE_CONFIG = "class";
	private static final String ARTIFACT_ID = "org.eclipse.capra.configuration.ArtifactMetaModel";
	private static final String ARTIFACT_CONFIG = "class";
	private static final String ARTIFACT_HANDLER_ID = "org.eclipse.capra.configuration.artifactHandler";
	private static final String ARTIFACT_HANDLER_CONFIG = "class";
	private static final String PRIORITY_HANDLER_ID = "org.eclipse.capra.configuration.priorityHandler";
	private static final String PRIORITY_HANDLER_CONFIG = "class";

	/**
	 * Gets all extensions from the extension point ID and attribute passed.
	 * 
	 * @param ID
	 *            the ID of the extension point
	 * @param CONFIG
	 *            the name of the attribute
	 * @return List of extensions
	 * @throws CapraException
	 *             Something went wrong.
	 */
	public static List<Object> getExtensions(final String ID, final String CONFIG) throws CapraException {
		List<Object> extensions = new ArrayList<>();

		try {
			IConfigurationElement[] configs = Platform.getExtensionRegistry().getConfigurationElementsFor(ID);

			for (IConfigurationElement config : configs) {
				try {
					extensions.add(config.createExecutableExtension(CONFIG));
				} catch (Exception ex) {
				}
			}

			return extensions;
		} catch (Exception ex) {
			throw new CapraException(ex);
		}
	}

	/**
	 * Get the executable extension for the extension ID.
	 * 
	 * @param extensionID
	 *            The ID of the extension
	 * @return extension
	 * @throws CapraException
	 *             Something went wrong.
	 */
	public static ArtifactHandler getExtension(String extensionID, String ID, String CONFIG) throws CapraException {
		try {
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IExtension extension = registry.getExtension(ID, extensionID);
			IConfigurationElement[] elements = extension.getConfigurationElements();
			return (ArtifactHandler) elements[0].createExecutableExtension(CONFIG);
		} catch (Exception e) {
			throw new CapraException(e);
		}
	}

	/**
	 * Gets the configured {@link TraceMetaModelAdapter}.
	 * 
	 * @return The configured {@code TraceMetaModelAdapter}
	 * @throws CapraException
	 *             Something went wrong
	 */
	public static TraceMetaModelAdapter getTraceMetamodelAdapter() throws CapraException {
		try {
			Object extension = getExtensions(TRACE_ID, TRACE_CONFIG).get(0);
			return (TraceMetaModelAdapter) extension;
		} catch (Exception e) {
			throw new CapraException(e);
		}
	}

	/**
	 * Gets the configured {@link PersistenceAdapter}.
	 * 
	 * @return The configured {@code TracePersistenceAdapter}
	 * @throws CapraException
	 *             Something went wrong.
	 */
	public static PersistenceAdapter getTracePersistenceAdapter() throws CapraException {
		try {
			Object extension = getExtensions(PERSISTENCE_ID, PERSISTENCE_CONFIG).get(0);
			return (PersistenceAdapter) extension;
		} catch (Exception e) {
			throw new CapraException(e);
		}
	}

	/**
	 * Gets the configured {@link ArtifactMetaModelAdapter}.
	 * 
	 * @return The configured {@code ArtifactMetaModelAdapter}
	 * @throws CapraException
	 *             Something went wrong.
	 */
	public static ArtifactMetaModelAdapter getArtifactWrapperMetaModelAdapter() throws CapraException {
		try {
			Object extension = getExtensions(ARTIFACT_ID, ARTIFACT_CONFIG).get(0);
			return (ArtifactMetaModelAdapter) extension;
		} catch (Exception e) {
			throw new CapraException(e);
		}
	}

	/**
	 * Gets the available {@link ArtifactHandler} instances.
	 * 
	 * @return A collection of all the artifact handlers available. This method
	 *         collects all plugins that have an extension to the ArtifactHandler
	 *         Extension point
	 * @throws CapraException
	 *             Something went wrong.
	 */
	public static Collection<ArtifactHandler> getArtifactHandlers() throws CapraException {
		try {
			return getExtensions(ARTIFACT_HANDLER_ID, ARTIFACT_HANDLER_CONFIG).stream().map(ArtifactHandler.class::cast)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new CapraException(e);
		}
	}

	/**
	 * Return the artifact handler with the given ID.
	 * 
	 * @param ID
	 * @return ArtifactHandler
	 * @throws CapraException
	 *             Something went wrong.
	 */
	public static ArtifactHandler getArtifactHandler(String ID) throws CapraException {
		return getExtension(ID, ARTIFACT_HANDLER_ID, ARTIFACT_CONFIG);
	}

	/**
	 * Gets the configured {@link PriorityHandler}.
	 * 
	 * @return The configured {@code PriorityHandler} or null
	 * @throws CapraException
	 *             Something went wrong.
	 */
	public static PriorityHandler getPriorityHandler() throws CapraException {
		Object extension = getExtensions(PRIORITY_HANDLER_ID, PRIORITY_HANDLER_CONFIG).get(0);
		return (PriorityHandler) extension;
	}

	/**
	 * Gets the available {@link TraceLinkAdapter} instances.
	 * 
	 * @return A collection of all the tracelink adapters available. This method
	 *         collects all plugins that have an extension to the TraceLinkAdapter
	 *         Extension point
	 * @throws CapraException
	 */
	public static List<TraceLinkAdapter> getTraceLinkAdapters() throws CapraException {
		try {
			List<Object> extensions = getExtensions(TRACE_LINK_ID, TRACE__LINK_CONFIG);
			return extensions.stream().map(obj -> (TraceLinkAdapter) obj).collect(Collectors.toList());
		} catch (Exception e) {
			throw new CapraException(e);
		}
	}
	
	
	//added by Intecs
	public static TraceLinkAdapter getTraceLinkAdapter(String linkType) throws CapraException {
		try {
			TraceLinkAdapter adapter = null;
			List<Object> extensions = getExtensions(TRACE_LINK_ID, TRACE__LINK_CONFIG);
			for(Object obj : extensions){
				if(obj instanceof TraceLinkAdapter){
					TraceLinkAdapter tmp = (TraceLinkAdapter)obj;
					if(tmp.getLinkType().equals(linkType)){
						adapter = tmp;
					}
				}
			}
			return adapter;
		} catch (Exception e) {
			throw new CapraException(e);
		}
	}
}
