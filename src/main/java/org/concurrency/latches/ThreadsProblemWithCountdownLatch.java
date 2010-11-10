/**
 * 
 */
package org.concurrency.latches;

import java.util.concurrent.CountDownLatch;

/**
 * @author Abhinav Tripathi
 * 
 */
public class ThreadsProblemWithCountdownLatch {

	private static long[] startValues = null;
	private static long[] endValues = null;

	public static long timeTasks(int nThreads, final Runnable task)
			throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(nThreads);
		final CountDownLatch endGate = new CountDownLatch(nThreads);
		startValues = new long[nThreads];
		endValues = new long[nThreads];

		for (int i = 0; i < nThreads; i++) {
			Thread t = new Thread("thread-" + i) {
				public void run() {
					int val = new Integer(getName().split("-")[1]);
					try {
						//startValues[val] = System.nanoTime();
						System.out.println(getName()+" has started");
						startGate.countDown();
						startGate.await();
						task.run();
						endGate.countDown();
						System.out.println("*** "+getName()+" has terminated ***");
						//endValues[val] = System.nanoTime();
					} catch (InterruptedException ignored) {
						// TODO Auto-generated catch block
					}
				}
			};
			t.start();
		}

		long start = System.nanoTime();
		//startGate.countDown();
		long startLatchOpen = System.nanoTime();
		endGate.await();
		long end = System.nanoTime();
		//return end - start;
		return startLatchOpen;
	}

	public static void main(String[] args) throws InterruptedException {
		long startLatchOpenTime = timeTasks(100, new Runnable() {
			@Override
			public void run() {
				// System.out.println("RUN");
			}
		});
		/*System.out.println("Start times : ");
		for (int i = 0; i < startValues.length; i++)
			System.out.print(startValues[i] + " ");
		System.out.println();
		System.out.println("End times : ");
		for (int i = 0; i < endValues.length; i++)
			System.out.print(endValues[i] + " ");
		System.out.println();
		System.out.println("Start times : max = " + findMax(startValues));
		System.out.println("End times :   min = " + findMin(endValues));
		System.out.println("Time before releasing the start latch : "+startLatchOpenTime);*/
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
