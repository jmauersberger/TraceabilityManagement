/**
 */
package org.eclipse.capra.GenericTraceLinkMetaModel;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLinkMetaModelFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/GenModel basePackage='org.eclipse.capra'"
 * @generated
 */
public interface GenericTraceLinkMetaModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "GenericTraceLinkMetaModel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "org.eclipse.capra.GenericTraceLinkMetaModel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "GenericTraceLinkMetaModel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GenericTraceLinkMetaModelPackage eINSTANCE = org.eclipse.capra.GenericTraceLinkMetaModel.impl.GenericTraceLinkMetaModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.capra.GenericTraceLinkMetaModel.impl.GenericTraceLinkImpl <em>Generic Trace Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.impl.GenericTraceLinkImpl
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.impl.GenericTraceLinkMetaModelPackageImpl#getGenericTraceLink()
	 * @generated
	 */
	int GENERIC_TRACE_LINK = 0;

	/**
	 * The feature id for the '<em><b>Sources</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TRACE_LINK__SOURCES = 0;

	/**
	 * The feature id for the '<em><b>Targets</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TRACE_LINK__TARGETS = 1;

	/**
	 * The number of structural features of the '<em>Generic Trace Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TRACE_LINK_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Generic Trace Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GENERIC_TRACE_LINK_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLink <em>Generic Trace Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Generic Trace Link</em>'.
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLink
	 * @generated
	 */
	EClass getGenericTraceLink();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLink#getSources <em>Sources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sources</em>'.
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLink#getSources()
	 * @see #getGenericTraceLink()
	 * @generated
	 */
	EReference getGenericTraceLink_Sources();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLink#getTargets <em>Targets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Targets</em>'.
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLink#getTargets()
	 * @see #getGenericTraceLink()
	 * @generated
	 */
	EReference getGenericTraceLink_Targets();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	GenericTraceLinkMetaModelFactory getGenericTraceLinkMetaModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.capra.GenericTraceLinkMetaModel.impl.GenericTraceLinkImpl <em>Generic Trace Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.capra.GenericTraceLinkMetaModel.impl.GenericTraceLinkImpl
		 * @see org.eclipse.capra.GenericTraceLinkMetaModel.impl.GenericTraceLinkMetaModelPackageImpl#getGenericTraceLink()
		 * @generated
		 */
		EClass GENERIC_TRACE_LINK = eINSTANCE.getGenericTraceLink();

		/**
		 * The meta object literal for the '<em><b>Sources</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERIC_TRACE_LINK__SOURCES = eINSTANCE.getGenericTraceLink_Sources();

		/**
		 * The meta object literal for the '<em><b>Targets</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GENERIC_TRACE_LINK__TARGETS = eINSTANCE.getGenericTraceLink_Targets();

	}

} //GenericTraceLinkMetaModelPackage
