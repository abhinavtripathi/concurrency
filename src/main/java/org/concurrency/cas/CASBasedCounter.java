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

}
