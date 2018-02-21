
public interface GeneSegment {
	/**
	 * Mutates this gene
	 * @return A GeneSegment that may or may not be mutated.  It may also be a new random gene.
	 */
	public GeneSegment mutate();
	/**
	 * Gets the mathematical result of this gene.
	 * @param numAnchors The number of anchors in the web.
	 * @param numStrands The number of strands currently in the web.
	 * @return The mathematical result/answer.
	 */
	public double evaluate(int numAnchors, int numStrands);
	/**
	 * Returns a deep copy of this GeneSegment.
	 * @return A deep copy of this GeneSegment.
	 */
	public GeneSegment copy();
	public String toString();
}
