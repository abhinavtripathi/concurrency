/**
 * 
 */
package org.concurrency.graphs;

import java.util.List;

/**
 * @author Abhinav Tripathi
 */
public interface Traverser {

	public <T> List<Node<T>> traverse(Graph<T> graph);

}
