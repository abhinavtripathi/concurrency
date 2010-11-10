/**
 * 
 */
package org.concurrency.perfmeasurements;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author Abhinav Tripathi
 * 
 */
public class Container implements Directory {

	private Map<String, Integer> directory = new HashMap<String, Integer>();
	private static Logger logger = Logger.getLogger(Container.class);
	private CountDownLatch gate;

	public Container(int numThreads) {
		gate = new CountDownLatch(numThreads);
	}

	public synchronized void slowAdd(String name, Integer phoneNum) {
		if (name == null)
			return;
		if (phoneNum.intValue() < 0)
			return;
		if (phoneNum.toString().startsWith("+91")) {
			phoneNum = new Integer("+91-" + phoneNum.toString());
		}
		directory.put(name, phoneNum);
	}

	public void fastAdd(String name, Integer phoneNum) {
		if (name == null)
			return;
		if (phoneNum.intValue() < 0)
			return;
		if (phoneNum.toString().startsWith("+91")) {
			phoneNum = new Integer("+91-" + phoneNum.toString());
		}
		synchronized (this) {
			directory.put(name, phoneNum);
		}
	}

	public CountDownLatch getGate() {
		return this.gate;
	}

	public static void main(String[] args) throws InterruptedException {
		DOMConfigurator.configure("log4j.xml");
		int numThreads = Integer.parseInt(args[0]);
		Directory slowContainer = new SlowContainer(numThreads);
		long ts1 = System.nanoTime();
		for (int i = 0; i < numThreads; i++) {
			new Thread(((Container) slowContainer).new Worker(slowContainer, ((Container) slowContainer).getGate())).start();
		}
		((Container) slowContainer).getGate().await();
		long ts2 = System.nanoTime();
		logger.info("Avg. number of nanosecs for slow add operation = " + (ts2 - ts1) / numThreads);
		Directory fastContainer = new FastContainer(numThreads);
		long ts3 = System.nanoTime();
		for (int i = 0; i < numThreads; i++) {
			new Thread(((Container) fastContainer).new Worker(fastContainer, ((Container) fastContainer).getGate())).start();
		}
		((Container) fastContainer).getGate().await();
		long ts4 = System.nanoTime();
		logger.info("Avg. number of nanosecs for fast add operation = " + (ts4 - ts3) / numThreads);
	}

	private class Worker implements Runnable {

		private Directory container;
		private CountDownLatch gate;

		public Worker(Directory container, CountDownLatch gate) {
			this.container = container;
			this.gate = gate;
		}

		@Override
		public void run() {
			Random rnd = new Random();
			String name = "";
			int phoneNum = 0;
			switch (rnd.nextInt(3)) {
			case 0:
				name = "abc";
				phoneNum = rnd.nextInt(100);
				break;
			case 1:
				name = "xyz";
				phoneNum = rnd.nextInt(100);
				break;
			case 2:
				name = "yup";
				phoneNum = rnd.nextInt(100);
				break;
			default:
				break;
			}
			container.addEntry(name, phoneNum);
			((Container) container).getGate().countDown();
		}
	}

	@Override
	public void addEntry(String name, Integer phoneNum) {

	}

}
