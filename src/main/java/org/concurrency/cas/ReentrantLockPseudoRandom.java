package org.concurrency.cas;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A {@link ReentrantLock} based pseudo-random number generator.
 * 
 * @author Abhinav Tripathi
 */
public class ReentrantLockPseudoRandom implements PseudoRandom {

	private final Lock lock = new ReentrantLock(false);
	private int seed;

	ReentrantLockPseudoRandom(int seed) {
		this.seed = seed;
	}

	public int nextInt(int n) {
		lock.lock();
		try {
			int s = seed;
			seed = calculateNext(s);
			int remainder = s % n;
			return remainder > 0 ? remainder : remainder + n;
		} finally {
			lock.unlock();
		}
	}

	private int calculateNext(int s) {
		return s * s + 1;
	}

}
