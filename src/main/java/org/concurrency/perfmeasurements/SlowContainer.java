package org.concurrency.perfmeasurements;

/**
 * 
 * @author Abhinav Tripathi
 */
public class SlowContainer extends Container implements Directory {

	public SlowContainer(int numThreads) {
		super(numThreads);
	}

	public void addEntry(String name, Integer phoneNum) {
		this.slowAdd(name, phoneNum);
	}

}