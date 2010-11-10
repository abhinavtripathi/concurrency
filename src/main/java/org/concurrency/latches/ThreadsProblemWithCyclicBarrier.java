/**
 * 
 */
package org.concurrency.latches;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;

/**
 * Problem: Implement a simulation where N threads are created, and then a
 * sequence of messages are printed by threads. First, the main thread prints
 * that it started. Secondly, all the threads print their start messages and
 * wait until all are done printing the start messages. Thirdly, the threads
 * print their finishing messages and then finally main thread prints its
 * finishing message.
 * 
 * This implementation uses {@link CyclicBarrier} to solve the problem.
 * 
 * @author Abhinav Tripathi
 */
public class ThreadsProblemWithCyclicBarrier {

	private static long[] startValues = null;
	private static long[] endValues = null;
	private static Logger logger = Logger.getLogger(ThreadsProblemWithCyclicBarrier.class);

	public static void timeTasks(int nThreads, final CyclicBarrier endGate) throws InterruptedException {
		final CyclicBarrier startGate = new CyclicBarrier(nThreads);
		startValues = new long[nThreads];
		endValues = new long[nThreads];

		for (int i = 0; i < nThreads; i++) {
			Thread t = new Thread("thread-" + i) {
				public void run() {
					int val = new Integer(getName().split("-")[1]);
					try {
						startValues[val] = System.nanoTime();
						logger.info("Thread " + val + " has started!");
						startGate.await();
						endValues[val] = System.nanoTime();
						logger.info("Thread " + val + " has finished!");
						endGate.await();
					} catch (InterruptedException ignored) {
						ignored.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
		}
	}

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		int numThreads = 10;
		logger.info("Main thread has started!");
		final CyclicBarrier endGate = new CyclicBarrier(numThreads + 1);
		timeTasks(numThreads, endGate);
		// logger.info("Start times : ");
		// for (int i = 0; i < startValues.length; i++)
		// System.out.print(startValues[i] + " ");
		// logger.info("");
		// logger.info("End times : ");
		// for (int i = 0; i < endValues.length; i++)
		// System.out.print(endValues[i] + " ");
		// logger.info("");
		// logger.info("Start times : max = " + findMax(startValues));
		// logger.info("End times :   min = " + findMin(endValues));
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
