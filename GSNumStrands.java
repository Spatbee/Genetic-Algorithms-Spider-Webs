public class GSNumStrands implements GeneSegment{
	/**
	 * Creates a new GSNumStrands.
	 */
	public GSNumStrands() {
		
	}
	
	public GeneSegment mutate() {
		if(Math.random()<.1) {
			return GeneCreator.newGene();
		}
		return this.copy();
	}

	/**
	 * @return the number of strands in the web, currently.
	 */
	public double evaluate(int numAnchors, int numStrands) {
		return numStrands;
	}


	public GeneSegment copy() {
		return new GSNumStrands();
	}
	
	public String toString() {
		return "# strands";
	}

}
