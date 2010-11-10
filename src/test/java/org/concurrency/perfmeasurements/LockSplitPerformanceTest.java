/**
 * 
 */
package org.concurrency.perfmeasurements;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the performance improvements achieved using the lock splitting
 * technique.
 * 
 * @author abhinav
 */
public class LockSplitPerformanceTest {

	private ServerStatus server;
	private static Logger logger = Logger.getLogger(LockSplitPerformanceTest.class);
	private CyclicBarrier barrier;
	private int numThreads;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		server = new ServerStatus();
		numThreads = 100;
		barrier = new CyclicBarrier(numThreads + 1);
	}

	/**
	 * Adds either {@link ServerStatus.User}s or {@link ServerStatus.Query}s
	 * depending upon how its constructed. Also, depending on how its
	 * constructed, calls slow or fast methods for addition.
	 * 
	 * @author abhinav
	 */
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

	/**
	 * Removes either {@link ServerStatus.User}s or {@link ServerStatus.Query}s
	 * depending upon how its constructed. Also, depending on how its
	 * constructed, calls slow or fast methods for removal.
	 * 
	 * @author abhinav
	 */
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

	@Test
	public void testPerformanceImprovementForUsers() throws InterruptedException, BrokenBarrierException {
		// SLOW OPERATION
		long ts1 = System.nanoTime();
		for (int i = 0; i < numThreads; i++) {
			new Thread(new Adder(server, true, true, barrier)).start();
		}
		barrier.await();
		long ts2 = System.nanoTime();
		logger.info("Avg. number of nanosecs for slow add user operation = " + (ts2 - ts1) / numThreads);

		barrier.reset();

		// FAST OPERATION
		long ts3 = System.nanoTime();
		for (int i = 0; i < numThreads; i++) {
			new Thread(new Adder(server, false, true, barrier)).start();
		}
		barrier.await();
		long ts4 = System.nanoTime();
		logger.info("Avg. number of nanosecs for fast remove user operation = " + (ts4 - ts3) / numThreads);

		assertTrue((ts4 - ts3) < (ts2 - ts1));
	}

	@Test
	public void testPerformanceImprovementForQueries() throws InterruptedException, BrokenBarrierException {
		// SLOW OPERATION
		barrier.reset();
		long ts1 = System.nanoTime();
		for (int i = 0; i < numThreads; i++) {
			new Thread(new Adder(server, true, false, barrier)).start();
		}
		barrier.await();
		long ts2 = System.nanoTime();
		logger.info("Avg. number of nanosecs for slow add query operation = " + (ts2 - ts1) / numThreads);

		barrier.reset();

		// FAST OPERATION
		long ts3 = System.nanoTime();
		for (int i = 0; i < numThreads; i++) {
			new Thread(new Adder(server, false, true, barrier)).start();
		}
		barrier.await();
		long ts4 = System.nanoTime();
		logger.info("Avg. number of nanosecs for fast remove query operation = " + (ts4 - ts3) / numThreads);

		assertTrue((ts4 - ts3) < (ts2 - ts1));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		server = null;
		barrier = null;
	}

}
