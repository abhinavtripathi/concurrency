package org.concurrency.flawed;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The {@code NumberRange} class (with a few additional methods) from the book
 * "Java Concurrency in Practice" to hold an integer range. This class is not
 * thread-safe.
 * 
 * @author Abhinav Tripathi
 */
public class NumberRange {
	// INVARIANT: lower <= upper
	private final AtomicInteger lower = new AtomicInteger(0);
	private final AtomicInteger upper = new AtomicInteger(0);

	/**
	 * Simple holder for pair of values, used here solely to provide values of
	 * lower and upper bounds of an invalid {@link NumberRange} when asked for
	 * by the caller.
	 * 
	 * @author Abhinav Tripathi
	 */
	static class Pair {
		final int min;
		final int max;

		public Pair(int min, int max) {
			this.max = max;
			this.min = min;
		}
	}

	private final AtomicReference<Pair> range = new AtomicReference<NumberRange.Pair>(new Pair(0, 0));

	public void setLower(int i) {
		// Warning -- unsafe check-then-act
		if (i > upper.get())
			throw new IllegalArgumentException("can't set lower to " + i + " > upper");
		lower.set(i);
		while (true) {
			Pair oldv = range.get();
			Pair newv = new Pair(i, oldv.max);
			if (range.compareAndSet(oldv, newv))
				return;
		}
	}

	public void setUpper(int i) {
		// Warning -- unsafe check-then-act
		if (i < lower.get())
			throw new IllegalArgumentException("can't set upper to " + i + " < lower");
		upper.set(i);
		while (true) {
			Pair oldv = range.get();
			Pair newv = new Pair(oldv.min, i);
			if (range.compareAndSet(oldv, newv))
				return;
		}
	}

	public boolean isInRange(int i) {
		return (i >= lower.get() && i <= upper.get());
	}

	/**
	 * Checks if the range is in an invalid state, and returns it if so. Uses
	 * CAS operations implicitly, to maintain atomic access to the bounds of
	 * range.
	 * 
	 * @return the range if it is in an invalid state, else null
	 */
	public Pair getInvalidRange() {
		Pair pair = range.get();
		if (pair.max < pair.min)
			return pair;
		else
			return null;
	}

	/**
	 * Checks if the range is in an invalid state, and returns it if so.
	 * 
	 * @return the range if it is in an invalid state, else null
	 */
	public Pair getInvalidRangeWithoutUsingCAS() {
		int min = lower.get();
		int max = upper.get();
		if (max < min)
			return new Pair(min, max);
		else
			return null;
	}

	/**
	 * @return the lower
	 */
	public AtomicInteger getLower() {
		return lower;
	}

	/**
	 * @return the upper
	 */
	public AtomicInteger getUpper() {
		return upper;
	}
}
