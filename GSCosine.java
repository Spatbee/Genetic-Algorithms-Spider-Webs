
public class GSCosine implements GeneSegment{
	private GeneSegment inner;
	public GSCosine() {
		this(GeneCreator.newGene());
	}
	public GSCosine(GeneSegment inner) {
		this.inner = inner;
	}
	
	public GeneSegment mutate() {
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
