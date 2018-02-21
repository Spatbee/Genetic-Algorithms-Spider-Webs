
public class GSTangent implements GeneSegment{
	private GeneSegment inner;
	/**
	 * Creates a new, random, GSTangent.
	 */
	public GSTangent() {
		this(GeneCreator.newGene());
	}
	/**
	 * Creates a new GSTangent gene segment.
	 * @param inner The gene inside the tangent function.
	 */
	public GSTangent(GeneSegment inner) {
		this.inner = inner;
	}
	
	public GeneSegment mutate() {
		if(Math.random()<.1) return GeneCreator.newGene();
		return new GSTangent(inner.mutate());
	}

	public double evaluate(int numAnchors, int numStrands) {
		try {
			return Math.tan(inner.evaluate(numAnchors, numStrands));
		}
		catch(Exception e) {
			return 0;
		}
	}

	public GeneSegment copy() {
		return new GSTangent(inner.copy());
	}
	
	public String toString() {
		return "tan( "+inner.toString()+" )";
	}

}
