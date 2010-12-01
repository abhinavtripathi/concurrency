/**
 * 
 */
package org.concurrency.graphs;

import java.util.Set;

/**
 * Finds cycles in a given {@link Graph}, if any.
 * 
 * @author Abhinav Tripathi
 */
public interface CycleFinder<T> {

	/**
	 * Finds whether the provided {@link Graph} has cycle(s).
	 * 
	 * @param graph
	 * @return true/false
	 */
	public boolean hasCycle(Graph<T> graph);

	/**
	 * Finds all cycle(s) in the provided {@link Graph}, if any.
	 * 
	 * @param graph
	 * @return
	 */
	public Set<Set<Node<T>>> findCycles(Graph<T> graph);

}
