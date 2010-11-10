package org.concurrency.perfmeasurements;

public class FastContainer extends Container implements Directory {

	public FastContainer(int numThreads) {
		super(numThreads);
	}

	public void addEntry(String name, Integer phoneNum) {
		this.fastAdd(name, phoneNum);
	}

}