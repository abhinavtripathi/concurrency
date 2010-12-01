/**
 * 
 */
package org.concurrency.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * @author Abhinav Tripathi
 */
public class UndirectedGraph<T> implements Graph<T> {

	public static final GraphType type = GraphType.DIRECTED;
	private static Logger logger = Logger.getLogger(UndirectedGraph.class);

	private List<Node<T>> nodes = new ArrayList<Node<T>>();

	@Override
	public List<Node<T>> getAllNodes() {
		return Collections.unmodifiableList(nodes);
	}

	@Override
	public GraphType getGraphType() {
		return type;
	}

	@Override
	public void addNode(Node<T> node) {
		this.nodes.add(node);
	}

	@Override
	public void addNode(Node<T> node, Set<Connection<T>> connections) {
		for (Connection<T> connection : connections) {
			node.connectTo(connection.getNode(), connection.getDirection());
		}
		this.nodes.add(node);
	}

	@Override
	public void destroyNode(Node<T> node) {
		for (Node<T> adjacentNode : node.getConnections().keySet()) {
			adjacentNode.disconnectFrom(node);
			node.disconnectFrom(adjacentNode);
		}
		node = null;
	}

	@Override
	public void display() {
		Set<Edge<T>> edges = new HashSet<Edge<T>>();
		StringBuffer nodeBuffer = new StringBuffer();
		for (Node<T> node : getAllNodes()) {
			nodeBuffer.append(node.getName() + " ");
			edges.addAll(node.getEdges());
		}
		StringBuffer edgeBuffer = new StringBuffer();
		for (Edge<T> edge : edges) {
			edgeBuffer.append(edge.toString() + " ");
		}
		logger.info("Graph G(V, E): V = ( " + nodeBuffer.toString() + "), E = ( " + edgeBuffer.toString() + ")");
	}

	@Override
	public Set<Edge<T>> getAllEdges() {
		Set<Edge<T>> edges = new HashSet<Edge<T>>();
		for (Node<T> node : nodes) {
			edges.addAll(node.getEdges());
		}
		return edges;
	}

}
