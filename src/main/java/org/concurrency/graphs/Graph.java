/**
 * 
 */
package org.concurrency.graphs;

import java.util.List;
import java.util.Set;

/**
 * Represents the graph data structure in general.
 * 
 * @author Abhinav Tripathi
 */
public interface Graph<T> {

	/**
	 * Returns all nodes in the graph.
	 * 
	 * @return list of nodes
	 */
	public List<Node<T>> getAllNodes();

	/**
	 * Returns the type of this graph e.g. cyclic, acyclic etc.
	 * 
	 * @return {@link GraphType}
	 */
	public GraphType getGraphType();

	/**
	 * Adds a {@link Node} to this graph. No {@link Connection}s need to be
	 * provided for this {@link Node}.
	 * 
	 * @param node
	 */
	public void addNode(Node<T> node);

	/**
	 * Adds a {@link Node} to this graph, making the {@link Connection}s
	 * provided for this {@link Node}.
	 * 
	 * @param node
	 * @param connections
	 */
	public void addNode(Node<T> node, Set<Connection<T>> connections);

	/**
	 * Removes the given {@link Node} from this graph.
	 * 
	 * @param node
	 */
	public void destroyNode(Node<T> node);

	/**
	 * Displays this graph, textually/graphically depending on how this is
	 * implemented.
	 */
	public void display();

	/**
	 * Returns the set of edges in this graph.
	 * 
	 * @return set of edges
	 */
	public Set<Edge<T>> getAllEdges();

}
