/**
 * 
 */
package org.concurrency.graphs;

import static org.junit.Assert.*;

import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Abhinav Tripathi
 */
public class TarjanCycleFinderTest {

	private TarjanCycleFinder<String> cycleFinder;
	private static Logger logger = Logger.getLogger(TarjanCycleFinderTest.class);

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		cycleFinder = new TarjanCycleFinder<String>();
	}

	@Test
	public void testFindCyclesOnAnAcyclicGraph() {
		Graph<String> graph = new DirectedGraph<String>();

		Node<String> nodeA = new GraphNode<String>("A");
		Node<String> nodeB = new GraphNode<String>("B");
		Node<String> nodeC = new GraphNode<String>("C");
		Node<String> nodeD = new GraphNode<String>("D");
		Node<String> nodeE = new GraphNode<String>("E");
		Node<String> nodeF = new GraphNode<String>("F");
		Node<String> nodeG = new GraphNode<String>("G");
		graph.addNode(nodeA);
		graph.addNode(nodeB);
		graph.addNode(nodeC);
		graph.addNode(nodeD);
		graph.addNode(nodeE);
		graph.addNode(nodeF);
		graph.addNode(nodeG);

		nodeA.connectTo(nodeB, Direction.OUTGOING);
		nodeA.connectTo(nodeC, Direction.INCOMING);

		nodeB.connectTo(nodeD, Direction.OUTGOING);
		nodeB.connectTo(nodeE, Direction.OUTGOING);

		nodeC.connectTo(nodeF, Direction.OUTGOING);
		nodeC.connectTo(nodeG, Direction.OUTGOING);

		assertTrue(!cycleFinder.hasCycle(graph));
	}

	@Test
	public void testFindCyclesOnACyclicGraph() {
		// Make a complete binary tree upto 3 levels
		Graph<String> graph = new DirectedGraph<String>();

		Node<String> nodeA = new GraphNode<String>("A");
		Node<String> nodeB = new GraphNode<String>("B");
		Node<String> nodeC = new GraphNode<String>("C");
		Node<String> nodeD = new GraphNode<String>("D");
		Node<String> nodeE = new GraphNode<String>("E");
		Node<String> nodeF = new GraphNode<String>("F");
		Node<String> nodeG = new GraphNode<String>("G");
		Node<String> nodeH = new GraphNode<String>("H");

		graph.addNode(nodeA);
		graph.addNode(nodeB);
		graph.addNode(nodeC);
		graph.addNode(nodeD);
		graph.addNode(nodeE);
		graph.addNode(nodeF);
		graph.addNode(nodeG);
		graph.addNode(nodeH);

		nodeA.connectTo(nodeB, Direction.OUTGOING);
		nodeA.connectTo(nodeD, Direction.INCOMING);
		nodeA.connectTo(nodeF, Direction.OUTGOING);

		nodeB.connectTo(nodeD, Direction.OUTGOING);

		nodeD.connectTo(nodeG, Direction.OUTGOING);

		nodeC.connectTo(nodeE, Direction.INCOMING);
		nodeC.connectTo(nodeH, Direction.INCOMING);
		nodeC.connectTo(nodeG, Direction.OUTGOING);

		nodeG.connectTo(nodeH, Direction.OUTGOING);

		nodeF.connectTo(nodeE, Direction.OUTGOING);
		nodeF.connectTo(nodeE, Direction.INCOMING);

		graph.display();

		assertTrue(cycleFinder.hasCycle(graph));

		Set<Set<Node<String>>> cycles = cycleFinder.findCycles(graph);
		assertTrue(cycles.size() == 3);
		for (Set<Node<String>> cycle : cycles) {
			if (cycle.contains(nodeA)) {
				assertTrue(cycle.size() == 3);
				assertTrue(cycle.contains(nodeB));
				assertTrue(cycle.contains(nodeD));
			}
			if (cycle.contains(nodeF)) {
				assertTrue(cycle.size() == 2);
				assertTrue(cycle.contains(nodeE));
			}
			if (cycle.contains(nodeC)) {
				assertTrue(cycle.size() == 3);
				assertTrue(cycle.contains(nodeG));
				assertTrue(cycle.contains(nodeH));
			}
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		cycleFinder = null;
	}

}
