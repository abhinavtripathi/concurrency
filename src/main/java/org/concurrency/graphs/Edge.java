/**
 * 
 */
package org.concurrency.graphs;

/**
 * @author Abhinav Tripathi
 */
public class Edge<T> extends Connection<T> {

	private Node<T> refNode;

	public Edge(Node<T> refNode, Node<T> otherNode, Direction edgeDirection) {
		super(otherNode, edgeDirection);
		this.refNode = refNode;
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
		result = prime * result + ((otherNode == null) ? 0 : otherNode.hashCode());
		result += ((refNode == null) ? 0 : refNode.hashCode());
		result *= prime;
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
		if (!(obj instanceof Edge))
			return false;
		Edge<T> other = (Edge<T>) obj;
		if (refNode == null) {
			if (other.refNode != null)
				return false;
		} else if (!refNode.equals(other.refNode)) {
			if (!refNode.equals(other.otherNode))
				return false;
			else {
				if (!otherNode.equals(other.refNode))
					return false;
				else if (edgeDirection != Direction.BIDIRECTIONAL && edgeDirection == other.edgeDirection)
					return false;
			}
		} else {
			if (!otherNode.equals(other.otherNode))
				return false;
			else if (edgeDirection != other.edgeDirection)
				return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[refNode=" + refNode.getName() + ", otherNode=" + otherNode.getName() + ", edgeDirection=" + edgeDirection.toString() + "]";
	}

}
