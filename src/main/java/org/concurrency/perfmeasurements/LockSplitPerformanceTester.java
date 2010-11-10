/**
 * 
 */
package org.concurrency.perfmeasurements;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author Abhinav Tripathi
 * 
 */
public class LockSplitPerformanceTester {

	private static Logger logger = Logger.getLogger(LockSplitPerformanceTester.class);
	private CyclicBarrier barrier;

	public LockSplitPerformanceTester(CyclicBarrier gate) {
		this.barrier = gate;
	}

	class Adder implements Runnable {

		private ServerStatus server;
		private boolean isSlow;
		private boolean addsUsers;
		private CyclicBarrier waitBarrier;

		public Adder(ServerStatus server, boolean isSlow, boolean addsUsers, CyclicBarrier waitBarrier) {
			this.server = server;
			this.isSlow = isSlow;
			this.addsUsers = addsUsers;
			this.waitBarrier = waitBarrier;
		}

		@Override
		public void run() {
			Random rnd = new Random();
			if (isSlow && addsUsers)
				server.slowAddUser(server.new User("x", rnd.nextInt(10)));
			if (!isSlow && addsUsers)
				server.fastAddUser(server.new User("x", rnd.nextInt(10)));
			if (isSlow && !addsUsers)
				server.slowAddQuery(server.new Query("x" + rnd.nextDouble()));
			if (!isSlow && !addsUsers)
				server.fastAddQuery(server.new Query("x" + rnd.nextDouble()));
			try {
				this.waitBarrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

	}

	class Remover implements Runnable {

		private ServerStatus server;
		private boolean isSlow;
		private boolean removesUsers;
		private CyclicBarrier waitBarrier;

		public Remover(ServerStatus server, boolean isSlow, boolean removesUsers, CyclicBarrier waitBarrier) {
			this.server = server;
			this.isSlow = isSlow;
			this.removesUsers = removesUsers;
			this.waitBarrier = waitBarrier;
		}

		@Override
		public void run() {
			if (isSlow && removesUsers)
				server.slowRemoveUser();
			if (isSlow && !removesUsers)
				server.slowRemoveQuery();
			if (!isSlow && removesUsers)
				server.fastRemoveUser();
			if (!isSlow && !removesUsers)
				server.fastRemoveQuery();
			try {
				this.waitBarrier.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		DOMConfigurator.configure("log4j.xml");

		int numThreads = Integer.parseInt(args[0]);
		LockSplitPerformanceTester tester = new LockSplitPerformanceTester(new CyclicBarrier(numThreads + 1));

		ServerStatus server = new ServerStatus();

		// SLOW OPERATIONS
		long ts1 = System.nanoTime();
		for (int i = 0; i < numThreads; i++) {
			new Thread(tester.new Adder(server, true, true, tester.getBarrier())).start();
		}
		tester.getBarrier().await();
		long ts2 = System.nanoTime();
		logger.info("Avg. number of nanosecs for slow add user operation = " + (ts2 - ts1) / numThreads);

		tester.getBarrier().reset();
		ts1 = System.nanoTime();
		for (int i = 0; i < numThreads; i++) {
			new Thread(tester.new Adder(server, true, false, tester.getBarrier())).start();
		}
		tester.getBarrier().await();
		ts2 = System.nanoTime();
		logger.info("Avg. number of nanosecs for slow add query operation = " + (ts2 - ts1) / numThreads);

		// FAST OPERATIONS
		tester.getBarrier().reset();
		long ts3 = System.nanoTime();
		for (int i = 0; i < numThreads; i++) {
			new Thread(tester.new Adder(server, false, true, tester.getBarrier())).start();
		}
		tester.getBarrier().await();
		long ts4 = System.nanoTime();
		logger.info("Avg. number of nanosecs for fast remove user operation = " + (ts4 - ts3) / numThreads);

		tester.getBarrier().reset();
		ts3 = System.nanoTime();
		for (int i = 0; i < numThreads; i++) {
			new Thread(tester.new Adder(server, false, true, tester.getBarrier())).start();
		}
		tester.getBarrier().await();
		ts4 = System.nanoTime();
		logger.info("Avg. number of nanosecs for fast remove query operation = " + (ts4 - ts3) / numThreads);
	}

	/**
	 * @return the gate
	 */
	public CyclicBarrier getBarrier() {
		return barrier;
	}

}
