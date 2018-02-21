
public class GSCosine implements GeneSegment{
	private GeneSegment inner;
	/**
	 * Creates a new, random, GSCosine.
	 */
	public GSCosine() {
		this(GeneCreator.newGene());
	}
	/**
	 * Creates a new GSCosine gene segment.
	 * @param inner The GeneSegment inside the cosine.
	 */
	public GSCosine(GeneSegment inner) {
		this.inner = inner;
	}
	
	public GeneSegment mutate() {
		if(Math.random()<.1) return GeneCreator.newGene();
		return new GSCosine(inner.mutate());
	}

	public double evaluate(int numAnchors, int numStrands) {
		try {
			return Math.cos(inner.evaluate(numAnchors, numStrands));
		}
		catch(Exception e) {
			return 0;
		}
	}

	public GeneSegment copy() {
		return new GSCosine(inner.copy());
	}
	
	public String toString() {
		return "cos( "+inner.toString()+" )";
	}

}
