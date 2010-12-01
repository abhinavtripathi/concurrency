/**
 * 
 */
package org.concurrency.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Abhinav Tripathi
 */
public class DepthFirstTraverser implements Traverser, Runnable {

	private Graph graph;

	public DepthFirstTraverser() {

	}

	public DepthFirstTraverser(Graph graph) {
		this.graph = graph;
	}

	@Override
	public <T> List<Node<T>> traverse(Graph<T> graph) {
		List<Node<T>> nodes = new ArrayList<Node<T>>();
		if (graph == null)
			throw new IllegalArgumentException("Graph to traverse cannot be null!");

		return depthFirstTraverse(graph.getAllNodes().get(0), nodes);
	}

	private <T> List<Node<T>> depthFirstTraverse(Node<T> node, List<Node<T>> seenNodes) {
		seenNodes.add(node);
		for (Node<T> adjacentNode : node.getConnections().keySet()) {
			if (!seenNodes.contains(adjacentNode))
				seenNodes = depthFirstTraverse(adjacentNode, seenNodes);
		}

		return seenNodes;
	}

	@Override
	public void run() {
		traverse(this.graph);
	}

}
