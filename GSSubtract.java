

public class GSSubtract implements GeneSegment {
	public GeneSegment gene1, gene2;
	/**
	 * Create a new, random, GSSubtract.
	 */
	public GSSubtract() {
		this(GeneCreator.newGene(), GeneCreator.newGene());
	}
	/**
	 * Create a new GSSubtract gene segment.
	 * @param gene1 The gene to be subtracted from.
	 * @param gene2 The gene to be subtracted.
	 */
	public GSSubtract(GeneSegment gene1, GeneSegment gene2) {
		this.gene1 = gene1;
		this.gene2 = gene2;
	}
	public GeneSegment mutate() {
		if(Math.random()<.1) return GeneCreator.newGene();
		return new GSSubtract(gene1.mutate(), gene2.mutate());
	}

	public double evaluate(int numAnchors, int numStrands) {
		try {
			return gene1.evaluate(numAnchors, numStrands)-gene2.evaluate(numAnchors, numStrands);
		}
		catch(Exception e) {
			return 0;
		}
	}

	public GeneSegment copy() {
		return new GSSubtract(gene1.copy(), gene2.copy());
	}
	
	public String toString() {
		return gene1.toString()+" - "+gene2.toString();
	}
	
}
