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
	 * The meta object id for the '{@link org.eclipse.capra.GenericTraceLinkMetaModel.impl.RelatedToImpl <em>Related To</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.impl.RelatedToImpl
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.impl.GenericTraceLinkMetaModelPackageImpl#getRelatedTo()
	 * @generated
	 */
	int RELATED_TO = 0;

	/**
	 * The feature id for the '<em><b>Sources</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_TO__SOURCES = 0;

	/**
	 * The feature id for the '<em><b>Targets</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_TO__TARGETS = 1;

	/**
	 * The number of structural features of the '<em>Related To</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_TO_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Related To</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATED_TO_OPERATION_COUNT = 0;


	/**
	 * The meta object id for the '{@link org.eclipse.capra.GenericTraceLinkMetaModel.impl.SatisfyImpl <em>Satisfy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.impl.SatisfyImpl
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.impl.GenericTraceLinkMetaModelPackageImpl#getSatisfy()
	 * @generated
	 */
	int SATISFY = 1;

	/**
	 * The feature id for the '<em><b>Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SATISFY__ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Requirement</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SATISFY__REQUIREMENT = 1;

	/**
	 * The number of structural features of the '<em>Satisfy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SATISFY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Satisfy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SATISFY_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.capra.GenericTraceLinkMetaModel.RelatedTo <em>Related To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Related To</em>'.
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.RelatedTo
	 * @generated
	 */
	EClass getRelatedTo();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.capra.GenericTraceLinkMetaModel.RelatedTo#getSources <em>Sources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Sources</em>'.
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.RelatedTo#getSources()
	 * @see #getRelatedTo()
	 * @generated
	 */
	EReference getRelatedTo_Sources();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.capra.GenericTraceLinkMetaModel.RelatedTo#getTargets <em>Targets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Targets</em>'.
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.RelatedTo#getTargets()
	 * @see #getRelatedTo()
	 * @generated
	 */
	EReference getRelatedTo_Targets();

	/**
	 * Returns the meta object for class '{@link org.eclipse.capra.GenericTraceLinkMetaModel.Satisfy <em>Satisfy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Satisfy</em>'.
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.Satisfy
	 * @generated
	 */
	EClass getSatisfy();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.capra.GenericTraceLinkMetaModel.Satisfy#getElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Element</em>'.
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.Satisfy#getElement()
	 * @see #getSatisfy()
	 * @generated
	 */
	EReference getSatisfy_Element();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.capra.GenericTraceLinkMetaModel.Satisfy#getRequirement <em>Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Requirement</em>'.
	 * @see org.eclipse.capra.GenericTraceLinkMetaModel.Satisfy#getRequirement()
	 * @see #getSatisfy()
	 * @generated
	 */
	EReference getSatisfy_Requirement();

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
		 * The meta object literal for the '{@link org.eclipse.capra.GenericTraceLinkMetaModel.impl.RelatedToImpl <em>Related To</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.capra.GenericTraceLinkMetaModel.impl.RelatedToImpl
		 * @see org.eclipse.capra.GenericTraceLinkMetaModel.impl.GenericTraceLinkMetaModelPackageImpl#getRelatedTo()
		 * @generated
		 */
		EClass RELATED_TO = eINSTANCE.getRelatedTo();

		/**
		 * The meta object literal for the '<em><b>Sources</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RELATED_TO__SOURCES = eINSTANCE.getRelatedTo_Sources();

		/**
		 * The meta object literal for the '<em><b>Targets</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RELATED_TO__TARGETS = eINSTANCE.getRelatedTo_Targets();

		/**
		 * The meta object literal for the '{@link org.eclipse.capra.GenericTraceLinkMetaModel.impl.SatisfyImpl <em>Satisfy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.capra.GenericTraceLinkMetaModel.impl.SatisfyImpl
		 * @see org.eclipse.capra.GenericTraceLinkMetaModel.impl.GenericTraceLinkMetaModelPackageImpl#getSatisfy()
		 * @generated
		 */
		EClass SATISFY = eINSTANCE.getSatisfy();

		/**
		 * The meta object literal for the '<em><b>Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SATISFY__ELEMENT = eINSTANCE.getSatisfy_Element();

		/**
		 * The meta object literal for the '<em><b>Requirement</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SATISFY__REQUIREMENT = eINSTANCE.getSatisfy_Requirement();

	}

} //GenericTraceLinkMetaModelPackage
