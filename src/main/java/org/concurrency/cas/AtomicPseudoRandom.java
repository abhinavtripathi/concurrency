package org.concurrency.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * An {@link AtomicInteger} based pseudo-random number generator.
 * 
 * @author Abhinav Tripathi
 */
public class AtomicPseudoRandom implements PseudoRandom {

	private AtomicInteger seed;

	AtomicPseudoRandom(int seed) {
		this.seed = new AtomicInteger(seed);
	}

	public int nextInt(int n) {
		while (true) {
			int s = seed.get();
			int nextSeed = calculateNext(s);
			if (seed.compareAndSet(s, nextSeed)) {
				int remainder = s % n;
				return remainder > 0 ? remainder : remainder + n;
			}
		}
	}

	private int calculateNext(int s) {
		return s * s + 1;
	}

}
