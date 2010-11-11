/**
 * 
 */
package org.concurrency.cas;

/**
 * A simple counter which relies on locking to ensure thread-safe operation.
 * 
 * @author Abhinav Tripathi
 */
public class LockingBasedCounter {

	private int count = 0;

	public synchronized int getValue() {
		return this.count;
	}

	public synchronized int incrementAndGet() {
		return ++count;
	}

}
