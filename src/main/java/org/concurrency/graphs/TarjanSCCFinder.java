/**
 * 
 */
package org.concurrency.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Finds all strongly connected components in a {@link Graph}, using <a href=
 * "http://en.wikipedia.org/wiki/Tarjan%E2%80%99s_strongly_connected_components_algorithm"
 * >Tarjan's algorithm</a>.
 * 
 * @author Abhinav Tripathi
 */
// TODO: the type parameter T does not really convey the relationship this class
// has with Graph, either this class should not be a generic type OR the type
// parameter should tell that it works on Graph<T>
public class TarjanSCCFinder<T> {

	private Graph<T> graph;

	private Stack stack = new Stack();
	private Map<Node<T>, NodeWrapper<T>> wrappedNodesWithTarjanIndices = new HashMap<Node<T>, TarjanSCCFinder<T>.NodeWrapper<T>>();
	private static int index = 0;

	private static Logger logger = Logger.getLogger(TarjanSCCFinder.class);

	public TarjanSCCFinder() {

	}

	public TarjanSCCFinder(Graph<T> graph) {
		this.graph = graph;
	}

	/**
	 * A very simple stack implementation, meant to be used by
	 * {@link TarjanSCCFinder}.
	 * 
	 * @author trader
	 */
	private class Stack {
		private List<Object> stack = new ArrayList<Object>();

		public void push(Object elem) {
			stack.add(0, elem);
		}

		public Object pop() {
			Object elem = null;
			if (stack.isEmpty())
				throw new RuntimeException("Cannot pop from an empty stack!");
			elem = stack.remove(0);
			return elem;
		}

		public Object peek() {
			return stack.get(0);
		}

		public boolean contains(Object elem) {
			return (stack.contains(elem));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuffer representation = new StringBuffer("Stack [ ");
			for (Object elem : stack)
				representation.append(elem.toString() + " ");
			representation.append(" ]");
			return representation.toString();
		}
	}

	public Set<Set<Node<T>>> findAllSronglyConnectedComponents(Graph<T> graph) {
		Set<Set<Node<T>>> stronglyConnectedComponents = new HashSet<Set<Node<T>>>();
		this.graph = graph;
		for (Node<T> node : graph.getAllNodes()) {
			NodeWrapper<T> wrappedNode = this.new NodeWrapper<T>(node);
			wrappedNodesWithTarjanIndices.put(wrappedNode.getNode(), wrappedNode);
		}
		for (Node<T> node : wrappedNodesWithTarjanIndices.keySet()) {
			if (wrappedNodesWithTarjanIndices.get(node).isTarjanIndexUndefined()) {
				tarjanAlgorithm(wrappedNodesWithTarjanIndices.get(node), stronglyConnectedComponents);
			}
		}

		return stronglyConnectedComponents;
	}

	private Set<Set<Node<T>>> tarjanAlgorithm(NodeWrapper<T> wrappedNode, Set<Set<Node<T>>> stronglyConnectedComponents) {
		wrappedNode.setTarjanIndex(index);
		wrappedNode.setLowLink(index);
		index++;
		stack.push(wrappedNode);
		for (Node<T> otherNode : wrappedNode.getNode().getOutgoingConnections().keySet()) {
			NodeWrapper<T> otherWrappedNode = wrappedNodesWithTarjanIndices.get(otherNode);
			if (otherWrappedNode.isTarjanIndexUndefined()) {
				stronglyConnectedComponents = tarjanAlgorithm(otherWrappedNode, stronglyConnectedComponents);
				wrappedNode.setLowLink(wrappedNode.getLowLink() < otherWrappedNode.getLowLink() ? wrappedNode.getLowLink() : otherWrappedNode
						.getLowLink());
			} else if (stack.contains(otherWrappedNode)) {
				wrappedNode.setLowLink(wrappedNode.getLowLink() < otherWrappedNode.getTarjanIndex() ? wrappedNode.getLowLink() : otherWrappedNode
						.getTarjanIndex());
			}
		}
		if (wrappedNode.getLowLink() == wrappedNode.getTarjanIndex()) {
			Set<Node<T>> stronglyConnectedComponent = new HashSet<Node<T>>();
			stronglyConnectedComponents.add(stronglyConnectedComponent);
			StringBuffer result = new StringBuffer();
			result.append("Strongly Connected Component: ");
			@SuppressWarnings("unchecked")
			NodeWrapper<T> otherNode = (NodeWrapper<T>) stack.pop();
			stronglyConnectedComponent.add(otherNode.getNode());
			result.append(otherNode.getNode().getName() + " ");
			while (!otherNode.getNode().equals(wrappedNode.getNode())) {
				otherNode = (NodeWrapper<T>) stack.pop();
				stronglyConnectedComponent.add(otherNode.getNode());
				result.append(otherNode.getNode().getName() + " ");
			}
			logger.info(result.toString());
		}
		
		return stronglyConnectedComponents;
	}

	private class NodeWrapper<T> {

		private Node<T> node;
		private int tarjanIndex = -1; // undefined
		private int lowLink = -1; // undefined

		public NodeWrapper(Node<T> node) {
			this.node = node;
		}

		public boolean isTarjanIndexUndefined() {
			return tarjanIndex == -1;
		}

		/**
		 * @return the node
		 */
		public Node<T> getNode() {
			return node;
		}

		/**
		 * @param node
		 *            the node to set
		 */
		public void setNode(Node<T> node) {
			this.node = node;
		}

		/**
		 * @return the tarjanIndex
		 */
		public int getTarjanIndex() {
			return tarjanIndex;
		}

		/**
		 * @param tarjanIndex
		 *            the tarjanIndex to set
		 */
		public void setTarjanIndex(int tarjanIndex) {
			this.tarjanIndex = tarjanIndex;
		}

		/**
		 * @return the lowLink
		 */
		public int getLowLink() {
			return lowLink;
		}

		/**
		 * @param lowLink
		 *            the lowLink to set
		 */
		public void setLowLink(int lowLink) {
			this.lowLink = lowLink;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return node.hashCode();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof NodeWrapper)) {
				return false;
			}
			@SuppressWarnings("rawtypes")
			NodeWrapper other = (NodeWrapper) obj;
			if (!getOuterType().equals(other.getOuterType())) {
				return false;
			}
			return node.equals(other.getNode());
		}

		@SuppressWarnings("rawtypes")
		private TarjanSCCFinder getOuterType() {
			return TarjanSCCFinder.this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "[node=" + node + ", tarjanIndex=" + tarjanIndex + ", lowLink=" + lowLink + "]";
		}

	}

}
