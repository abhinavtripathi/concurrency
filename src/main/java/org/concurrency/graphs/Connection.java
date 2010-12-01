/**
 * 
 */
package org.concurrency.graphs;

/**
 * @author Abhinav Tripathi
 */
public class Connection<T> {

	protected Node<T> otherNode;
	protected Direction edgeDirection;

	/**
	 * @param node
	 * @param edgeDirection
	 */
	public Connection(Node<T> node, Direction edgeDirection) {
		super();
		this.otherNode = node;
		this.edgeDirection = edgeDirection;
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
		result = prime * result + ((edgeDirection == null) ? 0 : edgeDirection.hashCode());
		result = prime * result + ((otherNode == null) ? 0 : otherNode.hashCode());
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
		if (!(obj instanceof Connection))
			return false;
		Connection<T> other = (Connection<T>) obj;
		if (edgeDirection != other.edgeDirection)
			return false;
		if (otherNode == null) {
			if (other.otherNode != null)
				return false;
		} else if (!otherNode.equals(other.otherNode))
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
		return "Connection [node=" + otherNode.getName() + ", edgeDirection=" + edgeDirection.toString() + "]";
	}

	public void reverse() {
		if (!this.edgeDirection.equals(Direction.BIDIRECTIONAL))
			this.edgeDirection = this.edgeDirection.equals(Direction.INCOMING) ? Direction.OUTGOING : Direction.INCOMING;
	}

	public Node<T> getNode() {
		return otherNode;
	}

	public Direction getDirection() {
		return edgeDirection;
	}

}
