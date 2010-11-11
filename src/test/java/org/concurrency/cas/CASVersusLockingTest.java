/**
 * 
 */
package org.concurrency.cas;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for expected performance improvements over locking when using CAS
 * operations.
 * 
 * @author Abhinav Tripathi
 */
public class CASVersusLockingTest {

	private static Logger logger = Logger.getLogger(CASVersusLockingTest.class);
	private final CASBasedCounter casBasedCounter = new CASBasedCounter();
	private final LockingBasedCounter lockingBasedCounter = new LockingBasedCounter();
	private int numThreads;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		numThreads = 100;
	}

	private long average(long[] values, int n) {
		long total = 0;
		for (int i = 0; i < values.length; i++)
			total += values[i];
		return total / n;
	}

	@Test
	public void testCASPeformanceImprovementOverLocking() throws InterruptedException, BrokenBarrierException {
		final long[] lockingIncrementTimes = new long[numThreads];
		final long[] casIncrementTimes = new long[numThreads];
		final CyclicBarrier barrier = new CyclicBarrier(numThreads + 1);

		// Increment operations
		long start = System.nanoTime();
		for (int i = 0; i < numThreads; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// long t1, t2;
					// synchronized (this) {
					// t1 = System.nanoTime();
					// lockingBasedCounter.incrementAndGet();
					// t2 = System.nanoTime();
					// }
					// lockingIncrementTimes[Integer.parseInt(Thread.currentThread().getName())]
					// = t2 - t1;
					// lockingIncrementTimes[Integer.parseInt(Thread.currentThread().getName())]
					// = lockingBasedCounter.timedIncrement();
					lockingIncrementTimes[Integer.parseInt(Thread.currentThread().getName())] = lockingBasedCounter.incrementAndGet();
					try {
						barrier.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
				}

			}, String.valueOf(i)).start();
		}
		barrier.await();
		long end = System.nanoTime();
		assertTrue(lockingBasedCounter.getValue() == numThreads);
		long timeTaken4LockingBased = end - start;
		logger.info("Locking based, time taken (ns) = " + timeTaken4LockingBased);

		barrier.reset();

		start = System.nanoTime();
		for (int i = 0; i < numThreads; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// long t1, t2;
					// synchronized (this) {
					// t1 = System.nanoTime();
					// casBasedCounter.incrementAndGet();
					// t2 = System.nanoTime();
					// }
					// casIncrementTimes[Integer.parseInt(Thread.currentThread().getName())]
					// = t2 - t1;
					// casIncrementTimes[Integer.parseInt(Thread.currentThread().getName())]
					// = casBasedCounter.timedIncrement();
					casIncrementTimes[Integer.parseInt(Thread.currentThread().getName())] = casBasedCounter.incrementAndGet();
					try {
						barrier.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
				}

			}, String.valueOf(i)).start();
		}
		barrier.await();
		end = System.nanoTime();
		assertTrue(casBasedCounter.getValue() == numThreads);
		long timeTaken4CASBased = end - start;
		logger.info("CAS based, time taken (ns) = " + timeTaken4CASBased);

		// logger.info("Locking based increment times : " +
		// display(lockingIncrementTimes));
		// logger.info("Avg. increment time for locking based counter = " +
		// average(lockingIncrementTimes, numThreads));
		// logger.info("CAS based increment times : " +
		// display(casIncrementTimes));
		// logger.info("Avg. increment time for CAS based counter = " +
		// average(casIncrementTimes, numThreads));
		//
		// assertTrue(average(lockingIncrementTimes, numThreads) >
		// average(casIncrementTimes, numThreads));

		assertTrue(timeTaken4CASBased < timeTaken4LockingBased);
	}

	private String display(long[] values) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < values.length - 1; i++)
			str.append(values[i] + ",");
		str.append(values[values.length - 1]);
		return str.toString();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
