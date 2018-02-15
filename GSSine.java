
public class GSSine implements GeneSegment{
	private GeneSegment inner;
	public GSSine() {
		this(GeneCreator.newGene());
	}
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
