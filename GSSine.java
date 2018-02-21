
public class GSSine implements GeneSegment{
	private GeneSegment inner;
	/**
	 * Create a new, random, GSSine.
	 */
	public GSSine() {
		this(GeneCreator.newGene());
	}
	/**
	 * Create a new GSSine gene segment.
	 * @param inner The gene on the inside of the sine.
	 */
	public GSSine(GeneSegment inner) {
		this.inner = inner;
	}
	
	public GeneSegment mutate() {
		if(Math.random()<.1) return GeneCreator.newGene();
		return new GSSine(inner.mutate());
	}

	public double evaluate(int numAnchors, int numStrands) {
		try {
			return Math.sin(inner.evaluate(numAnchors, numStrands));
		}
		catch(Exception e) {
			return 0;
		}
	}

	public GeneSegment copy() {
		return new GSSine(inner.copy());
	}
	
	public String toString() {
		return "sin( "+inner.toString()+" )";
	}

}
