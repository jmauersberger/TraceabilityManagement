package org.eclipse.capra.core.adapters;

import java.util.List;
import org.eclipse.capra.core.CapraException;
import org.eclipse.capra.core.util.TraceLinkAttribute;
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

	
	//Modified by Intecs
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
	EObject createLink(List<EObject> sources, List<EObject> targets, List<TraceLinkAttribute> attributes) throws CapraException;
	
	//Added by Intecs
	/**
	 * Gets the attributes of the tracelink type
	 * @return the attributes
	 */
	List<TraceLinkAttribute> getAttributes();
	
	//Added by Intecs
	/**
	 * Computes the label for the eobject parameter
	 * @param eobject
	 * @return a String representation (Label) of the eobject parameter
	 */
	String getLabelForEObject(EObject eobject);
	
}
