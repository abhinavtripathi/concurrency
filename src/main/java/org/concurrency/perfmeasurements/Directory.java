package org.concurrency.perfmeasurements;

/**
 * Represents some basic operations on a directory data structure, here
 * specifically the phone book.
 * 
 * @author Abhinav Tripathi
 */
public interface Directory {

	/**
	 * Adds an entry to the phone book
	 * 
	 * @param name
	 * @param phoneNum
	 */
	public void addEntry(String name, Integer phoneNum);

}
