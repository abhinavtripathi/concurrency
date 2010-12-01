/**
 * 
 */
package org.concurrency.graphs;

import java.util.Map;
import java.util.Set;

/**
 * Represents a node in the {@link Graph}.
 * 
 * @author Abhinav Tripathi
 */
public interface Node<T> {

	public String getName();

	public Map<Node<T>, Set<Connection<T>>> getConnections();

	public Map<Node<T>, Set<Connection<T>>> getOutgoingConnections();

	public Map<Node<T>, Set<Connection<T>>> getIncomingConnections();

	public Set<Edge<T>> getEdges();

	public void connectTo(Node<T> node, Direction direction);

	public void acceptConnection(Node<T> node, Direction direction);

	public void disconnectFrom(Node<T> node);

	public boolean isConnectedTo(Node<T> node);

}
