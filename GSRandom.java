
public class GSRandom implements GeneSegment {
	public GSRandom() {
		
	}
	
	public GeneSegment mutate() {
		double rand = Math.random();
		if(rand<.2) return GeneCreator.newGene();
		return this.copy();
	}

	public double evaluate(int numAnchors, int numStrands) {
		return Math.random();
	}

	public GeneSegment copy() {
		return new GSRandom();
	}
	public String toString() {
		return "random #";
	}

}
