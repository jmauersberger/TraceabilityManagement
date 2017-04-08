package org.eclipse.capra.ui.plantuml;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

public class Util {

	/**
	 * Linearizes a tree to a list. The function checks if the provided
	 * parameter is of type {@link EObject} and linearizes only if that is the
	 * case.
	 * 
	 * @param object
	 *            the object to linearize
	 * @return a list of {@link EObject}s originally contained in the tree
	 *         structure of the parameter or an empty list if the paramter was
	 *         not an {@link EObject}
	 */
	public static List<EObject> linearize(Object object) {
		ArrayList<EObject> elementList = new ArrayList<EObject>();
		if (object instanceof EObject) {
			EObject root = (EObject) object;
			root.eAllContents().forEachRemaining(element -> elementList.add(element));
			elementList.add(root);
		}
		return elementList;
	}

}
