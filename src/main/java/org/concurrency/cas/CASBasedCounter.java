/**
 * 
 */
package org.concurrency.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple counter which relies on CAS (Compare-And-Swap) operations to ensure
 * thread-safe operation.
 * 
 * @author Abhinav Tripathi
 */
public class CASBasedCounter {

	private AtomicInteger count = new AtomicInteger(0);

	public int getValue() {
		return count.intValue();
	}

	public int incrementAndGet() {
		return count.incrementAndGet();
	}

	/**
	 * Calculates the time taken for incrementing the counter.
	 * 
	 * @return the time taken
	 */
	public long timedIncrement() {
		long t1 = System.nanoTime();
		count.incrementAndGet();
		long t2 = System.nanoTime();

		return t2 - t1;
	}

}
