
public class GSNumAnchors implements GeneSegment{
	/**
	 * Creates a new GSNumAnchors.
	 */
	public GSNumAnchors() {
		
	}
	
	public GeneSegment mutate() {
		if(Math.random()<.1) {
			return GeneCreator.newGene();
		}
		return this.copy();
	}

	/**
	 * @return The number of anchors in the web.
	 */
	public double evaluate(int numAnchors, int numStrands) {
		return numAnchors;
	}


	public GeneSegment copy() {
		return new GSNumAnchors();
	}
	
	public String toString() {
		return "# anchors";
	}

}
