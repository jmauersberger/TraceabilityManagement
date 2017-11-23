/**
 */
package org.eclipse.capra.GenericTraceLinkMetaModel.impl;

import org.eclipse.capra.GenericTraceLinkMetaModel.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class GenericTraceLinkMetaModelFactoryImpl extends EFactoryImpl implements GenericTraceLinkMetaModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static GenericTraceLinkMetaModelFactory init() {
		try {
			GenericTraceLinkMetaModelFactory theGenericTraceLinkMetaModelFactory = (GenericTraceLinkMetaModelFactory)EPackage.Registry.INSTANCE.getEFactory(GenericTraceLinkMetaModelPackage.eNS_URI);
			if (theGenericTraceLinkMetaModelFactory != null) {
				return theGenericTraceLinkMetaModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new GenericTraceLinkMetaModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericTraceLinkMetaModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case GenericTraceLinkMetaModelPackage.GENERIC_TRACE_LINK: return createGenericTraceLink();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericTraceLink createGenericTraceLink() {
		GenericTraceLinkImpl genericTraceLink = new GenericTraceLinkImpl();
		return genericTraceLink;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenericTraceLinkMetaModelPackage getGenericTraceLinkMetaModelPackage() {
		return (GenericTraceLinkMetaModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static GenericTraceLinkMetaModelPackage getPackage() {
		return GenericTraceLinkMetaModelPackage.eINSTANCE;
	}

} //GenericTraceLinkMetaModelFactoryImpl
