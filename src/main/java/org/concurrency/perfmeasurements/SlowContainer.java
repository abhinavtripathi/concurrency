package org.concurrency.perfmeasurements;

/**
 * A {@link Container} that provides slow operations.
 * 
 * @author Abhinav Tripathi
 */
public class SlowContainer extends Container {

	public SlowContainer(int numThreads) {
		super(numThreads);
	}

	public void addEntry(String name, Integer phoneNum) {
		this.slowAdd(name, phoneNum);
	}

}