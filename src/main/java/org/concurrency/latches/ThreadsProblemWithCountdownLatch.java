/**
 * 
 */
package org.concurrency.latches;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

/**
 * Problem: Implement a simulation where N threads are created, and then a
 * sequence of messages are printed by threads. First, the main thread prints
 * that it started. Secondly, all the threads print their start messages and
 * wait until all are done printing the start messages. Thirdly, the threads
 * print their finishing messages and then finally main thread prints its
 * finishing message.
 * 
 * This implementation uses {@link CountDownLatch} to solve the problem.
 * 
 * @author Abhinav Tripathi
 */
public class ThreadsProblemWithCountdownLatch {

	private static long[] startValues = null;
	private static long[] endValues = null;
	private static Logger logger = Logger.getLogger(ThreadsProblemWithCountdownLatch.class);

	public static long timeTasks(int nThreads, final CountDownLatch endGate) throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(nThreads);

		startValues = new long[nThreads];
		endValues = new long[nThreads];

		for (int i = 0; i < nThreads; i++) {
			Thread t = new Thread("thread-" + i) {
				public void run() {
					int val = new Integer(getName().split("-")[1]);
					try {
						// startValues[val] = System.nanoTime();
						logger.info("Thread " + val + " has started!");
						startGate.countDown();
						startGate.await();
						logger.info("Thread " + val + " has terminated!");
						endGate.countDown();
						// endValues[val] = System.nanoTime();
					} catch (InterruptedException ignored) {
						// TODO Auto-generated catch block
					}
				}
			};
			t.start();
		}

		long start = System.nanoTime();
		// startGate.countDown();
		long startLatchOpen = System.nanoTime();
		// endGate.await();
		long end = System.nanoTime();
		// return end - start;
		return startLatchOpen;
	}

	public static void main(String[] args) throws InterruptedException {
		int numThreads = 100;
		logger.info("Main thread has started!");
		final CountDownLatch endGate = new CountDownLatch(numThreads);
		long startLatchOpenTime = timeTasks(numThreads, endGate);
		// System.out.println("Start times : ");
		// for (int i = 0; i < startValues.length; i++)
		// System.out.print(startValues[i] + " ");
		// System.out.println();
		// System.out.println("End times : ");
		// for (int i = 0; i < endValues.length; i++)
		// System.out.print(endValues[i] + " ");
		// System.out.println();
		// System.out.println("Start times : max = " + findMax(startValues));
		// System.out.println("End times :   min = " + findMin(endValues));
		// System.out.println("Time before releasing the start latch : " +
		// startLatchOpenTime);
		endGate.await();
		logger.info("Main thread has finished!");
	}

	public static long findMax(long[] values) {
		long max = values[0];
		for (int i = 1; i < values.length; i++)
			if (max < values[i])
				max = values[i];
		return max;
	}

	public static long findMin(long[] values) {
		long min = values[0];
		for (int i = 1; i < values.length; i++)
			if (min > values[i])
				min = values[i];
		return min;
	}

}
