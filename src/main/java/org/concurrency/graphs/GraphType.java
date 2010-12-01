/**
 * 
 */
package org.concurrency.graphs;

/**
 * @author Abhinav Tripathi
 */
public enum GraphType {

	DIRECTED(""), UNDIRECTED("");

	private final String type;

	private GraphType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return type;
	}

}
