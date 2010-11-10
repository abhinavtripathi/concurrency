/**
 * 
 */
package org.concurrency.latches;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Abhinav Tripathi
 * 
 */
public class ThreadsProblemWithCyclicBarrier {

	private static long[] startValues = null;
	private static long[] endValues = null;

	public static void timeTasks(int nThreads, final Runnable task) throws InterruptedException {
		final CyclicBarrier startGate = new CyclicBarrier(nThreads, task);
		startValues = new long[nThreads];
		endValues = new long[nThreads];

		for (int i = 0; i < nThreads; i++) {
			Thread t = new Thread("thread-" + i) {
				public void run() {
					int val = new Integer(getName().split("-")[1]);
					try {
						startValues[val] = System.nanoTime();
						startGate.await();
						endValues[val] = System.nanoTime();
					} catch (InterruptedException ignored) {
						// TODO Auto-generated catch block
						ignored.printStackTrace();
					} catch (BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			t.start();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		timeTasks(100, new Runnable() {
			@Override
			public void run() {
				// System.out.println("*** All Threads have started ! ***");
			}
		});
		System.out.println("Start times : ");
		for (int i = 0; i < startValues.length; i++)
			System.out.print(startValues[i] + " ");
		System.out.println();
		System.out.println("End times : ");
		for (int i = 0; i < endValues.length; i++)
			System.out.print(endValues[i] + " ");
		System.out.println();
		System.out.println("Start times : max = " + findMax(startValues));
		System.out.println("End times :   min = " + findMin(endValues));
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
