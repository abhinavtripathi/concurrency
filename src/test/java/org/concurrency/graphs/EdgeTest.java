/**
 * 
 */
package org.concurrency.graphs;

import org.apache.log4j.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Abhinav Tripathi
 */
public class EdgeTest {

	private static Logger logger = Logger.getLogger(EdgeTest.class);
	private Node<String> nodeA, nodeB;
	private Edge<String> edge1, edge2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		nodeA = new GraphNode<String>("A");
		nodeB = new GraphNode<String>("B");
	}

	@Test
	public void testEdgeComparisons() {
		edge1 = new Edge<String>(nodeA, nodeB, Direction.INCOMING);
		edge2 = new Edge<String>(nodeB, nodeA, Direction.OUTGOING);
		assertTrue(edge1.hashCode() == edge2.hashCode());
		assertTrue(edge1.equals(edge2));
		
		edge1 = new Edge<String>(nodeA, nodeB, Direction.INCOMING);
		edge2 = new Edge<String>(nodeB, nodeA, Direction.INCOMING);
		assertTrue(edge1.hashCode() == edge2.hashCode());
		assertTrue(!edge1.equals(edge2));
		
		edge1 = new Edge<String>(nodeA, nodeB, Direction.INCOMING);
		edge2 = new Edge<String>(nodeA, nodeB, Direction.OUTGOING);
		assertTrue(edge1.hashCode() == edge2.hashCode());
		assertTrue(!edge1.equals(edge2));
		
		edge1 = new Edge<String>(nodeA, nodeB, Direction.BIDIRECTIONAL);
		edge2 = new Edge<String>(nodeB, nodeA, Direction.BIDIRECTIONAL);
		assertTrue(edge1.hashCode() == edge2.hashCode());
		assertTrue(edge1.equals(edge2));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
