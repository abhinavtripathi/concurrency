/**
 * 
 */
package org.concurrency.flawed;

import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the invalid {@link NumberRange}
 * 
 * @author A.Tripathi
 */
public class NumberRangeTest {

	private static Logger logger = Logger.getLogger(NumberRangeTest.class);
	private final NumberRange numberRange = new NumberRange();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Tests for the invalid construction of the {@link NumberRange}, this may
	 * take some time though.
	 */
	@Test(expected = IllegalStateException.class)
	public void testInvalidNumberRange() {
		int numThreads = 4;
		for (int i = 0; i < numThreads; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					Random rnd = new Random();
					while (true) {
						try {
							numberRange.setLower(rnd.nextInt());
							numberRange.setUpper(rnd.nextInt());
						} catch (IllegalArgumentException e) {
							logger.info("Thread tried to set wrong number range, was caught!");
						}
						// try {
						// Thread.sleep(new Random().nextInt(1000));
						// } catch (InterruptedException e) {
						// e.printStackTrace();
						// }
					}
				}

			}).start();
		}
		while (true) {
			NumberRange.Pair invalidRange = numberRange.getInvalidRange();
			if (invalidRange != null) {
				logger.info("*** Number range exists in invalid state! lower=" + invalidRange.min + ", upper=" + invalidRange.max + " ***");
				throw new IllegalStateException("Number range exists in invalid state!");
			}
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

}
