/**
 */
package org.eclipse.capra.GenericTraceLinkMetaModel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Related To</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.capra.GenericTraceLinkMetaModel.RelatedTo#getSources <em>Sources</em>}</li>
 *   <li>{@link org.eclipse.capra.GenericTraceLinkMetaModel.RelatedTo#getTargets <em>Targets</em>}</li>
 * </ul>
 *
 * @see org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLinkMetaModelPackage#getRelatedTo()
 * @model
 * @generated
 */
public interface RelatedTo extends EObject {
	/**
	 * Returns the value of the '<em><b>Sources</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sources</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sources</em>' reference list.
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLinkMetaModelPackage#getRelatedTo_Sources()
	 * @model
	 * @generated
	 */
	EList<EObject> getSources();

	/**
	 * Returns the value of the '<em><b>Targets</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Targets</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Targets</em>' reference list.
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLinkMetaModelPackage#getRelatedTo_Targets()
	 * @model
	 * @generated
	 */
	EList<EObject> getTargets();

} // RelatedTo
