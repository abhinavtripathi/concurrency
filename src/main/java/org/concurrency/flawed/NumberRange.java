package org.concurrency.flawed;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The {@code NumberRange} class from the book "Java Concurrency in Practice" to
 * hold an integer range. This class is not thread-safe.
 * 
 * @author Abhinav Tripathi
 */
public class NumberRange {
	// INVARIANT: lower <= upper
	private final AtomicInteger lower = new AtomicInteger(0);
	private final AtomicInteger upper = new AtomicInteger(0);

	public void setLower(int i) {
		// Warning -- unsafe check-then-act
		if (i > upper.get())
			throw new IllegalArgumentException("can't set lower to " + i + " > upper");
		lower.set(i);
	}

	public void setUpper(int i) {
		// Warning -- unsafe check-then-act
		if (i < lower.get())
			throw new IllegalArgumentException("can't set upper to " + i + " < lower");
		upper.set(i);
	}

	public boolean isInRange(int i) {
		return (i >= lower.get() && i <= upper.get());
	}

	/**
	 * Gets the width of the number range. A valid width should never be
	 * negative.
	 * 
	 * @return the width of the number range
	 */
	public int getRangeWidth() {
		return upper.intValue() - lower.intValue();
	}
}
