/**
 * 
 */
package org.concurrency.cas;

/**
 * A pseudo-random number generator.
 * 
 * @author Abhinav Tripathi
 */
public interface PseudoRandom {

	/**
	 * Returns the next pseudo-random integer in the range [0,n)
	 * 
	 * @param n
	 *            the upper bound
	 * @return the next pseudo-random integer in the range [0,n)
	 */
	public int nextInt(int n);

}
