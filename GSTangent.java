
public class GSTangent implements GeneSegment{
	private GeneSegment inner;
	public GSTangent() {
		this(GeneCreator.newGene());
	}
	public GSTangent(GeneSegment inner) {
		this.inner = inner;
	}
	
	public GeneSegment mutate() {
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
