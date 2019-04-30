/**
 */
package org.eclipse.capra.GenericTraceLinkMetaModel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Satisfy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.capra.GenericTraceLinkMetaModel.Satisfy#getElement <em>Element</em>}</li>
 *   <li>{@link org.eclipse.capra.GenericTraceLinkMetaModel.Satisfy#getRequirement <em>Requirement</em>}</li>
 * </ul>
 *
 * @see org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLinkMetaModelPackage#getSatisfy()
 * @model
 * @generated
 */
public interface Satisfy extends EObject {
	/**
	 * Returns the value of the '<em><b>Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Element</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Element</em>' reference.
	 * @see #setElement(EObject)
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLinkMetaModelPackage#getSatisfy_Element()
	 * @model
	 * @generated
	 */
	EObject getElement();

	/**
	 * Sets the value of the '{@link org.eclipse.capra.GenericTraceLinkMetaModel.Satisfy#getElement <em>Element</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element</em>' reference.
	 * @see #getElement()
	 * @generated
	 */
	void setElement(EObject value);

	/**
	 * Returns the value of the '<em><b>Requirement</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Requirement</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Requirement</em>' reference.
	 * @see #setRequirement(EObject)
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLinkMetaModelPackage#getSatisfy_Requirement()
	 * @model
	 * @generated
	 */
	EObject getRequirement();

	/**
	 * Sets the value of the '{@link org.eclipse.capra.GenericTraceLinkMetaModel.Satisfy#getRequirement <em>Requirement</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Requirement</em>' reference.
	 * @see #getRequirement()
	 * @generated
	 */
	void setRequirement(EObject value);

} // Satisfy
