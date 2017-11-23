package org.eclipse.capra.core.adapters;

import java.util.List;

import org.eclipse.capra.core.CapraException;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * Defines the common functionality for a tracelink adapter.
 * <p>
 * There can be many different types of tracelink with different base classes.
 * There should be one tracelink adapter for every type of tracelink.
 * 
 * @author Sascha Baumgart
 */
public interface TraceLinkAdapter {
	/**
	 * Gets whether this adapter can adapt the trace type.
	 * 
	 * @param traceType
	 * @return true or false
	 */
	boolean canAdapt(EClass traceType);

	/**
	 * Gets the list of sources in the tracelink.
	 * 
	 * @param the
	 *            trace
	 * @return the list of sources
	 * @throws CapraException Something went wrong
	 */
	List<EObject> getSources(EObject trace) throws CapraException;

	/**
	 * Gets the list of targets in the tracelink.
	 * 
	 * @param the
	 *            trace
	 * @return the list of targets
	 * @throws CapraException Something went wrong
	 */
	List<EObject> getTargets(EObject trace) throws CapraException;

	/**
	 * Gets the tracelink type that this adapter can adapt as a string.
	 * 
	 * @return the tracelink type
	 */
	String getLinkType();

	/**
	 * Gets whether the underlying tracelink type is able to create a link between
	 * the sources and the targets.
	 * 
	 * @param sources
	 * @param targets
	 * @return true or false
	 */
	boolean canCreateLink(List<EObject> sources, List<EObject> targets);

	/**
	 * Creates a tracelink between the sources and the target.
	 * 
	 * @param sources
	 *            the sources
	 * @param targets
	 *            the targets
	 * @return the tracelink
	 * @throws CapraException Something went wrong
	 */
	EObject createLink(List<EObject> sources, List<EObject> targets) throws CapraException;
}
