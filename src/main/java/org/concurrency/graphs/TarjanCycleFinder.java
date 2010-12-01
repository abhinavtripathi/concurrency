/**
 * 
 */
package org.concurrency.graphs;

import java.util.Set;

/**
 * An implementation of {@link CycleFinder} using <a href=
 * "http://en.wikipedia.org/wiki/Tarjan%E2%80%99s_strongly_connected_components_algorithm"
 * >Tarjan's algorithm</a>.
 * 
 * @author Abhinav Tripathi
 */
public class TarjanCycleFinder<T> implements CycleFinder<T> {

	private TarjanSCCFinder<T> finder = new TarjanSCCFinder<T>();

	@Override
	public boolean hasCycle(Graph<T> graph) {
		boolean hasCycle = true;
		Set<Set<Node<T>>> components = finder.findAllSronglyConnectedComponents(graph);
		if (components.isEmpty())
			hasCycle = false;
		for (Set<Node<T>> component : components) {
			if (component.size() > 1) {
				hasCycle = true;
				break;
			} else
				hasCycle = false;
		}
		return hasCycle;
	}

	@Override
	public Set<Set<Node<T>>> findCycles(Graph<T> graph) {
		return finder.findAllSronglyConnectedComponents(graph);
	}

}
