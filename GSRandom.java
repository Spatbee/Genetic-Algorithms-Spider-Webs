
public class GSRandom implements GeneSegment {
	/**
	 * Create a new GSRanodm.
	 */
	public GSRandom() {
		
	}
	
	public GeneSegment mutate() {
		double rand = Math.random();
		if(rand<.2) return GeneCreator.newGene();
		return this.copy();
	}
	/**
	 * @return a random number between 0 and 1.
	 */
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
