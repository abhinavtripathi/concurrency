/**
 * 
 */
package org.concurrency.graphs;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of {@link Node}.
 * 
 * @author Abhinav Tripathi
 */
public class GraphNode<T> implements Node<T> {

	private final String name;
	private Map<Node<T>, Set<Connection<T>>> connections = new HashMap<Node<T>, Set<Connection<T>>>();

	public GraphNode() {
		this.name = "";
	}

	public GraphNode(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Map<Node<T>, Set<Connection<T>>> getConnections() {
		return Collections.unmodifiableMap(connections);
	}

	@Override
	public Map<Node<T>, Set<Connection<T>>> getOutgoingConnections() {
		Map<Node<T>, Set<Connection<T>>> outgoing = new HashMap<Node<T>, Set<Connection<T>>>();
		for (Node<T> node : connections.keySet()) {
			Set<Connection<T>> connectionsForThisNode = new HashSet<Connection<T>>();
			outgoing.put(node, connectionsForThisNode);
			for (Connection<T> connection : connections.get(node))
				if (connection.getDirection().equals(Direction.OUTGOING))
					connectionsForThisNode.add(connection);
			if (connectionsForThisNode.isEmpty())
				outgoing.remove(node);
		}
		return Collections.unmodifiableMap(outgoing);
	}

	@Override
	public Map<Node<T>, Set<Connection<T>>> getIncomingConnections() {
		Map<Node<T>, Set<Connection<T>>> incoming = new HashMap<Node<T>, Set<Connection<T>>>();
		for (Node<T> node : connections.keySet()) {
			Set<Connection<T>> connectionsForThisNode = new HashSet<Connection<T>>();
			incoming.put(node, connectionsForThisNode);
			for (Connection<T> connection : connections.get(node))
				if (connection.getDirection().equals(Direction.INCOMING))
					connectionsForThisNode.add(connection);
			if (connectionsForThisNode.isEmpty())
				incoming.remove(node);
		}
		return Collections.unmodifiableMap(incoming);
	}

	@Override
	public void connectTo(Node<T> node, Direction direction) {
		if (node == null)
			throw new IllegalArgumentException("Node to connect to cannot be null!");
		if (!connections.containsKey(node))
			connections.put(node, new HashSet<Connection<T>>());
		connections.get(node).add(new Connection<T>(node, direction));
		node.acceptConnection(this, direction);
	}

	@Override
	public void acceptConnection(Node<T> node, Direction direction) {
		if (node == null)
			throw new IllegalArgumentException("Node to connect to cannot be null!");
		if (!direction.equals(Direction.BIDIRECTIONAL))
			direction = direction.equals(Direction.INCOMING) ? Direction.OUTGOING : Direction.INCOMING;
		if (!connections.containsKey(node))
			connections.put(node, new HashSet<Connection<T>>());
		connections.get(node).add(new Connection<T>(node, direction));
	}

	@Override
	public void disconnectFrom(Node<T> node) {
		if (node == null)
			throw new IllegalArgumentException("Node to disconnect from cannot be null!");
		connections.remove(node);
		node.disconnectFrom(this);
	}

	@Override
	public Set<Edge<T>> getEdges() {
		Set<Edge<T>> edges = new HashSet<Edge<T>>();
		for (Node<T> node : connections.keySet()) {
			for (Connection<T> connection : connections.get(node))
				edges.add(new Edge<T>(this, connection.getNode(), connection.getDirection()));
		}

		return edges;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof GraphNode))
			return false;
		GraphNode<T> other = (GraphNode<T>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean isConnectedTo(Node<T> node) {
		return node.getConnections().containsKey(this);
	}

}
