package org.concurrency.perfmeasurements;

/**
 * A {@link Container} that provides fast operations.
 * @author abhinav
 */
public class FastContainer extends Container {

	public FastContainer(int numThreads) {
		super(numThreads);
	}

	public void addEntry(String name, Integer phoneNum) {
		this.fastAdd(name, phoneNum);
	}

}