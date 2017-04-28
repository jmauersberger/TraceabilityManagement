/**
 */
package org.eclipse.capra.GenericTraceLinkMetaModel;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLinkMetaModelPackage
 * @generated
 */
public interface GenericTraceLinkMetaModelFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GenericTraceLinkMetaModelFactory eINSTANCE = org.eclipse.capra.GenericTraceLinkMetaModel.impl.GenericTraceLinkMetaModelFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Generic Trace Link</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Generic Trace Link</em>'.
	 * @generated
	 */
	GenericTraceLink createGenericTraceLink();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	GenericTraceLinkMetaModelPackage getGenericTraceLinkMetaModelPackage();

} //GenericTraceLinkMetaModelFactory
