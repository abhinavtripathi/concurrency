/**
 * 
 */
package org.concurrency.graphs;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Abhinav Tripathi
 */
public class DepthFirstTraverserTest {

	private static Logger logger = Logger.getLogger(DepthFirstTraverserTest.class);

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	// TODO: needs manual verification, automate this
	@Test
	public void testTraverseOnAnUndirectedGraph() {
		Graph<String> graph = new UndirectedGraph<String>();
		Node<String> nodeA = new GraphNode<String>("A");
		Node<String> nodeB = new GraphNode<String>("B");
		Node<String> nodeC = new GraphNode<String>("C");
		Node<String> nodeD = new GraphNode<String>("D");
		Node<String> nodeE = new GraphNode<String>("E");
		Node<String> nodeF = new GraphNode<String>("F");
		Node<String> nodeG = new GraphNode<String>("G");
		Node<String> nodeH = new GraphNode<String>("H");
		Node<String> nodeI = new GraphNode<String>("I");
		graph.addNode(nodeA);
		graph.addNode(nodeB);
		graph.addNode(nodeC);
		graph.addNode(nodeD);
		graph.addNode(nodeE);
		graph.addNode(nodeF);
		graph.addNode(nodeG);
		graph.addNode(nodeH);
		graph.addNode(nodeI);
		nodeA.connectTo(nodeB, Direction.BIDIRECTIONAL);
		nodeA.connectTo(nodeD, Direction.BIDIRECTIONAL);
		nodeB.connectTo(nodeC, Direction.BIDIRECTIONAL);
		nodeD.connectTo(nodeE, Direction.BIDIRECTIONAL);
		nodeC.connectTo(nodeF, Direction.BIDIRECTIONAL);
		nodeC.connectTo(nodeG, Direction.BIDIRECTIONAL);
		nodeD.connectTo(nodeH, Direction.BIDIRECTIONAL);
		nodeH.connectTo(nodeI, Direction.BIDIRECTIONAL);

		graph.display();

		DepthFirstTraverser traverser = new DepthFirstTraverser();
		List<Node<String>> traversedNodes = traverser.traverse(graph);
		StringBuffer buffer = new StringBuffer();
		for (Node<String> traversedNode : traversedNodes)
			buffer.append(traversedNode.toString() + " ");
		logger.info("Depth First traversal order: ( " + buffer.toString() + ")");
		// assertTrue(traversedNodes.get(0).getName().equals("A"));
		// assertTrue(traversedNodes.get(1).getName().equals("B"));
		// assertTrue(traversedNodes.get(2).getName().equals("C"));
		// assertTrue(traversedNodes.get(3).getName().equals("D"));
		// assertTrue(traversedNodes.get(4).getName().equals("E"));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
