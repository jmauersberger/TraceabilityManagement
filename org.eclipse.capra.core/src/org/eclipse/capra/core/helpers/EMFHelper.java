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
package org.eclipse.capra.core.helpers;

import java.util.Collection;
import java.util.List;

import org.eclipse.capra.core.handlers.ArtifactHandler;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * Contains methods to work with {@link EObject} instances encountered when
 * handling EMF models.
 */
public class EMFHelper {

	/**
	 * Builds an identifier String for the given EObject. This identifier starts
	 * with
	 * <ul>
	 * <li>the attribute of the EObject as a String, if the EObject does only
	 * have one attribute.</li>
	 * <li>the attribute called 'name' of the EObject, if it has such an
	 * attribute</li>
	 * <li>any attribute of the EObject, but String attributes are preferred
	 * </li>
	 * </ul>
	 * The identifier ends with " : " followed by the type of the EObject. <br>
	 * Example: A Node with the name "foo" will result in "foo : Node" <br>
	 * If the EObject does not have any attributes or all attributes have the
	 * value null, this function will only return the type of the EObject.
	 */
	public static String createUIString(final EObject eObject) {
		if (eObject == null)
			return "<null>";
		if (eObject.eClass() == null)
			return eObject.toString();

		boolean success = false;

		List<EAttribute> attributes = eObject.eClass().getEAllAttributes();
		StringBuilder identifier = new StringBuilder();

		success = tryGetSingleAttribute(eObject, attributes, identifier);

		if (!success)
			success = tryGetNameAttribute(eObject, attributes, identifier);

		if (!success)
			success = tryGetAnyAttribute(eObject, attributes, identifier);

		if (success)
			identifier.append(" : ");

		boolean customHandlerName = false;
		Collection<ArtifactHandler> artifactHandlers = ExtensionPointHelper.getArtifactHandlers();
		for (ArtifactHandler handler : artifactHandlers) {
			Object obj = handler.resolveArtifact(eObject);
			if (obj != null && handler.canHandleSelection(obj)) {
				if (handler.getObjectTypeName(obj) != null) {
					identifier.append(handler.getObjectTypeName(obj));
					customHandlerName = true;
					break;
				}

			}
		}

		if (!customHandlerName) {
			identifier.append(eObject.eClass().getName());
		}

		return identifier.toString();
	}

	/**
	 * @param name
	 *            Use an empty StringBuilder as input. If this function returns
	 *            true, this parameter has been filled, if it returns false,
	 *            nothing happened.
	 * @return Indicates the success of this function and if the last parameter
	 *         contains output.
	 */
	private static boolean tryGetSingleAttribute(final EObject eObject, final List<EAttribute> attributes,
			final StringBuilder name) {
		boolean success = false;
		if (attributes.size() == 1) {
			Object obj = eObject.eGet(attributes.get(0));
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
	 *            true, this parameter has been filled, if it returns false,
	 *            nothing happened.
	 * @return Indicates the success of this function and if the last parameter
	 *         contains output.
	 */
	private static boolean tryGetNameAttribute(final EObject eObject, final List<EAttribute> attributes,
			final StringBuilder name) {
		boolean success = false;
		for (EAttribute feature : attributes) {
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
	 *            true, this parameter has been filled, if it returns false,
	 *            nothing happened.
	 * @return Indicates the success of this function and if the last parameter
	 *         contains output.
	 */
	private static boolean tryGetAnyAttribute(final EObject eObject, final List<EAttribute> attributes,
			final StringBuilder name) {
		boolean success = false;
		String nonStringName = null;
		String stringName = null;
		for (EAttribute feature : attributes) {
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
