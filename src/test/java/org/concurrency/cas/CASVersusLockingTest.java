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

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	private long average(long[] values, int n) {
		long total = 0;
		for (int i = 0; i < values.length; i++)
			total += values[i];
		return total / n;
	}

	@Test
	public void testCASPeformanceImprovementOverLocking() throws InterruptedException, BrokenBarrierException {
		int numThreads = 10;
		final long[] lockingIncrementTimes = new long[numThreads];
		final long[] casIncrementTimes = new long[numThreads];
		final CyclicBarrier barrier = new CyclicBarrier(numThreads + 1);

		// Increment operations
		for (int i = 0; i < numThreads; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					long t1 = System.nanoTime();
					lockingBasedCounter.incrementAndGet();
					long t2 = System.nanoTime();
					lockingIncrementTimes[Integer.parseInt(Thread.currentThread().getName())] = t2 - t1;
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
		assertTrue(lockingBasedCounter.getValue() == numThreads);
		barrier.reset();
		for (int i = 0; i < numThreads; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					long t1 = System.nanoTime();
					casBasedCounter.incrementAndGet();
					long t2 = System.nanoTime();
					casIncrementTimes[Integer.parseInt(Thread.currentThread().getName())] = t2 - t1;
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
		assertTrue(casBasedCounter.getValue() == numThreads);

		logger.info("Avg. increment time for locking based counter = " + average(lockingIncrementTimes, numThreads));
		logger.info("Avg. increment time for CAS based counter = " + average(casIncrementTimes, numThreads));

		assertTrue(average(lockingIncrementTimes, numThreads) > average(casIncrementTimes, numThreads));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
