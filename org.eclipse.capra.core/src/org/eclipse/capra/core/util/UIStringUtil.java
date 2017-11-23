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

import java.util.Optional;

import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.adapters.ArtifactMetaModelAdapter;
import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * Contains methods to work with {@link EObject} instances encountered when
 * handling EMF models.
 */
public class UIStringUtil {

	/**
	 * Builds an identifier String for the given EObject. This identifier starts
	 * with
	 * <ul>
	 * <li>the attribute of the EObject as a String, if the EObject does only have
	 * one attribute.</li>
	 * <li>the attribute called 'name' of the EObject, if it has such an
	 * attribute</li>
	 * <li>any attribute of the EObject, but String attributes are preferred</li>
	 * </ul>
	 * The identifier ends with " : " followed by the type of the EObject. <br>
	 * Example: A Node with the name "foo" will result in "foo : Node" <br>
	 * If the EObject does not have any attributes or all attributes have the value
	 * null, this function will only return the type of the EObject.
	 * 
	 * @throws CapraException
	 *             Something went wrong
	 */
	public static String createUIString(final EObject eObject) throws CapraException {
		if (eObject == null)
			return "<null>";
		if (eObject.eClass() == null)
			return eObject.toString();

		boolean success = false;

		StringBuilder identifier = new StringBuilder();

		success = tryGetSingleAttribute(eObject, identifier);

		if (!success)
			success = tryGetNameAttribute(eObject, identifier);

		if (!success)
			success = tryGetAnyAttribute(eObject, identifier);

		if (success)
			identifier.append(" : ");

		boolean customHandlerName = false;
		ArtifactMetaModelAdapter adapter = ExtensionPointUtil.getArtifactWrapperMetaModelAdapter();
		ArtifactHandler artifactHandler = adapter.getArtifactHandler(eObject);
		Object resolvedArtifact = artifactHandler.resolveArtifact(eObject);
		String objectTypeName = artifactHandler.getObjectTypeName(resolvedArtifact);
		identifier.append(objectTypeName);
		customHandlerName = true;

		if (!customHandlerName) {
			identifier.append(eObject.eClass().getName());
		}

		return identifier.toString();
	}

	/**
	 * @param name
	 *            Use an empty StringBuilder as input. If this function returns
	 *            true, this parameter has been filled, if it returns false, nothing
	 *            happened.
	 * @return Indicates the success of this function and if the last parameter
	 *         contains output.
	 */
	private static boolean tryGetSingleAttribute(final EObject eObject, final StringBuilder name) {
		boolean success = false;
		EList<EAttribute> allAttributes = eObject.eClass().getEAllAttributes();
		if (allAttributes.size() == 1) {
			Object obj = eObject.eGet(allAttributes.get(0));
			if (obj != null) {
				name.append(obj.toString());
				success = true;
			}
		}
		return success;
	}

	/**
	 * @param name
	 *            Use an empty StringBuilder as input. If this function returns
	 *            true, this parameter has been filled, if it returns false, nothing
	 *            happened.
	 * @return Indicates the success of this function and if the last parameter
	 *         contains output.
	 */
	private static boolean tryGetNameAttribute(final EObject eObject, final StringBuilder name) {
		boolean success = false;
		for (EAttribute feature : eObject.eClass().getEAllAttributes()) {
			if (feature.getName().equals("name")) {
				Object obj = eObject.eGet(feature);
				if (obj != null) {
					name.append(obj.toString());
					success = true;
					break;
				}
			}
		}
		return success;
	}

	/**
	 * @param name
	 *            Use an empty StringBuilder as input. If this function returns
	 *            true, this parameter has been filled, if it returns false, nothing
	 *            happened.
	 * @return Indicates the success of this function and if the last parameter
	 *         contains output.
	 */
	private static boolean tryGetAnyAttribute(final EObject eObject, final StringBuilder name) {
		boolean success = false;
		String nonStringName = null;
		String stringName = null;
		for (EAttribute feature : eObject.eClass().getEAllAttributes()) {
			Object obj = eObject.eGet(feature);
			if (obj == null)
				continue;
			if (obj instanceof String) {
				stringName = (String) obj;
				break;
			} else {
				nonStringName = obj.toString();
			}
		}
		if (stringName != null && !stringName.equals("null")) {
			name.append(stringName);
			success = true;
		} else if (nonStringName != null && !nonStringName.equals("null")) {
			name.append(nonStringName);
			success = true;
		}
		return success;
	}

}
