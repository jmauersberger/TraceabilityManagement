/**
 */
package org.eclipse.capra.GenericTraceLinkMetaModel.impl;

import java.util.Collection;

import org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLink;
import org.eclipse.capra.GenericTraceLinkMetaModel.GenericTraceLinkMetaModelPackage;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Generic Trace Link</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.capra.GenericTraceLinkMetaModel.impl.GenericTraceLinkImpl#getSources <em>Sources</em>}</li>
 *   <li>{@link org.eclipse.capra.GenericTraceLinkMetaModel.impl.GenericTraceLinkImpl#getTargets <em>Targets</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenericTraceLinkImpl extends MinimalEObjectImpl.Container implements GenericTraceLink {
	/**
	 * The cached value of the '{@link #getSources() <em>Sources</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSources()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> sources;

	/**
	 * The cached value of the '{@link #getTargets() <em>Targets</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargets()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> targets;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenericTraceLinkImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GenericTraceLinkMetaModelPackage.Literals.GENERIC_TRACE_LINK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getSources() {
		if (sources == null) {
			sources = new EObjectResolvingEList<EObject>(EObject.class, this, GenericTraceLinkMetaModelPackage.GENERIC_TRACE_LINK__SOURCES);
		}
		return sources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getTargets() {
		if (targets == null) {
			targets = new EObjectResolvingEList<EObject>(EObject.class, this, GenericTraceLinkMetaModelPackage.GENERIC_TRACE_LINK__TARGETS);
		}
		return targets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GenericTraceLinkMetaModelPackage.GENERIC_TRACE_LINK__SOURCES:
				return getSources();
			case GenericTraceLinkMetaModelPackage.GENERIC_TRACE_LINK__TARGETS:
				return getTargets();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case GenericTraceLinkMetaModelPackage.GENERIC_TRACE_LINK__SOURCES:
				getSources().clear();
				getSources().addAll((Collection<? extends EObject>)newValue);
				return;
			case GenericTraceLinkMetaModelPackage.GENERIC_TRACE_LINK__TARGETS:
				getTargets().clear();
				getTargets().addAll((Collection<? extends EObject>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case GenericTraceLinkMetaModelPackage.GENERIC_TRACE_LINK__SOURCES:
				getSources().clear();
				return;
			case GenericTraceLinkMetaModelPackage.GENERIC_TRACE_LINK__TARGETS:
				getTargets().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case GenericTraceLinkMetaModelPackage.GENERIC_TRACE_LINK__SOURCES:
				return sources != null && !sources.isEmpty();
			case GenericTraceLinkMetaModelPackage.GENERIC_TRACE_LINK__TARGETS:
				return targets != null && !targets.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //GenericTraceLinkImpl
