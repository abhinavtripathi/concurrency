/**
 * 
 */
package org.concurrency.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Abhinav Tripathi
 */
public class BreadthFirstTraverser implements Traverser, Runnable {

	private Graph graph;

	public BreadthFirstTraverser(Graph graph) {
		this.graph = graph;
	}

	@Override
	public <T> List<Node<T>> traverse(Graph<T> graph) {
		List<Node<T>> nodes = new ArrayList<Node<T>>();
		if (graph == null)
			throw new IllegalArgumentException("Graph to traverse cannot be null!");

		Node<T> startNode = graph.getAllNodes().get(0);
		

		return nodes;
	}

	@Override
	public void run() {
		traverse(this.graph);
	}

}
