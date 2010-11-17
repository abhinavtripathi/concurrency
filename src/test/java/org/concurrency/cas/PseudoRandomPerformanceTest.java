/**
 * 
 */
package org.concurrency.cas;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Tests comparing the performance of various {@link PseudoRandom}
 * implementations.
 * 
 * @author Abhinav Tripathi
 */
public class PseudoRandomPerformanceTest {

	private static Logger logger = Logger.getLogger(PseudoRandomPerformanceTest.class);

	@Test
	public void testAtomicIntegerVsReentrantLockBasedPRNGs() throws InterruptedException, BrokenBarrierException {
		int numThreadsStartValue = 2;
		int numVariations = 5;
		final int upperBound = 10000;
		final AtomicPseudoRandom atomicPseudoRandom = new AtomicPseudoRandom(17);
		final ReentrantLockPseudoRandom reentrantLockPseudoRandom = new ReentrantLockPseudoRandom(17);
		long[] reentrantLockComputeTimes = new long[numVariations];
		long[] atomicIntegerComputeTimes = new long[numVariations];

		for (int i = 0; i < numVariations; i++) {
			final CyclicBarrier barrier = new CyclicBarrier(1 + (i + 1) * numThreadsStartValue);

			// Measure ReentrantLock based PRNG
			long start = System.nanoTime();
			for (int j = 0; j < (i + 1) * numThreadsStartValue; j++) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						reentrantLockPseudoRandom.nextInt(upperBound);
						try {
							barrier.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BrokenBarrierException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}).start();
			}
			barrier.await();
			long end = System.nanoTime();
			reentrantLockComputeTimes[i] = end - start;

			barrier.reset();
			// Measure AtomicInteger based PRNG
			start = System.nanoTime();
			for (int j = 0; j < (i + 1) * numThreadsStartValue; j++) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						atomicPseudoRandom.nextInt(upperBound);
						try {
							barrier.await();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (BrokenBarrierException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}).start();
			}
			barrier.await();
			end = System.nanoTime();
			atomicIntegerComputeTimes[i] = end - start;
		}

		logger.info("ReentrantLock based times: " + display(reentrantLockComputeTimes, numThreadsStartValue));
		logger.info("AtomicInteger based times: " + display(atomicIntegerComputeTimes, numThreadsStartValue));

	}

	private String display(long[] computeTimes, int numThreadsStartValue) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < computeTimes.length; i++) {
			builder.append(((i + 1) * numThreadsStartValue) + " threads " + computeTimes[i] + " ns	");
		}
		return builder.toString();
	}

}
