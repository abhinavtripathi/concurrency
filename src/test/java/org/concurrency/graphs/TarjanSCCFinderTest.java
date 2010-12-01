/**
 * 
 */
package org.concurrency.graphs;

import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Abhinav Tripathi
 */
public class TarjanSCCFinderTest {

	private static Logger logger = Logger.getLogger(TarjanSCCFinder.class);

	@SuppressWarnings("rawtypes")
	private TarjanSCCFinder finder;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		finder = new TarjanSCCFinder<String>();
	}

	@Test
	public void testStronglyConnectedComponentsInACyclicGraph() {
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

		// Node<String> nodeA = new GraphNode<String>("A");
		// Node<String> nodeB = new GraphNode<String>("B");
		// Node<String> nodeC = new GraphNode<String>("C");
		// Node<String> nodeD = new GraphNode<String>("D");
		//
		// graph.addNode(nodeA);
		// graph.addNode(nodeB);
		// graph.addNode(nodeC);
		// graph.addNode(nodeD);
		//
		// nodeA.connectTo(nodeB, Direction.OUTGOING);
		// nodeA.connectTo(nodeC, Direction.INCOMING);
		//
		// nodeB.connectTo(nodeC, Direction.OUTGOING);
		//
		// nodeA.connectTo(nodeD, Direction.OUTGOING);

		graph.display();

		@SuppressWarnings("unchecked")
		Set<Set<Node<Object>>> components = finder.findAllSronglyConnectedComponents((Graph) graph);
		assertTrue(components.size() == 3);
		for (Set<Node<Object>> component : components) {
			if (component.contains(nodeA)) {
				assertTrue(component.size() == 3);
				assertTrue(component.contains(nodeB));
				assertTrue(component.contains(nodeD));
			}
			if (component.contains(nodeF)) {
				assertTrue(component.size() == 2);
				assertTrue(component.contains(nodeE));
			}
			if (component.contains(nodeC)) {
				assertTrue(component.size() == 3);
				assertTrue(component.contains(nodeG));
				assertTrue(component.contains(nodeH));
			}
		}
	}

	@Test
	public void testStronglyConnectedComponentsInAnAcyclicGraph() {
		// Make a complete binary tree upto 3 levels
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

		graph.display();

		@SuppressWarnings("unchecked")
		Set<Set<Node<Object>>> components = finder.findAllSronglyConnectedComponents((Graph) graph);
		assertTrue(components.size() == 7);
		for (Set<Node<Object>> component : components) {
			assertTrue(component.size() == 1);
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		finder = null;
	}

}
