/**
 * 
 */
package org.concurrency.graphs;

/**
 * @author Abhinav Tripathi
 */
public enum Direction {

	INCOMING("incoming"), OUTGOING("outgoing"), BIDIRECTIONAL("bidirectional");

	private final String type;

	private Direction(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}

}
