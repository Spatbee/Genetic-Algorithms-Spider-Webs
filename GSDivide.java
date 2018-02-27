
public class GSDivide implements GeneSegment {
	public GeneSegment gene1, gene2;
	
	/**
	 * Creates a new, random, GSDivide.
	 */
	public GSDivide() {
		this(GeneCreator.newGene(), GeneCreator.newGene());
	}
	/**
	 * Creates a new GSDivide gene segment.
	 * @param gene1 The gene to be divided from.
	 * @param gene2 The gene to be divided with.
	 */
	public GSDivide(GeneSegment gene1, GeneSegment gene2) {
		this.gene1 = gene1;
		this.gene2 = gene2;
	}
	public GeneSegment mutate() {
		if(Math.random()<.1) return GeneCreator.newGene();
		return new GSDivide(gene1.mutate(), gene2.mutate());
	}

	public double evaluate(int numAnchors, int numStrands) {
		try {
			return gene1.evaluate(numAnchors, numStrands)/gene2.evaluate(numAnchors, numStrands);
		}
		catch(Exception e) {
			return 0;
		}
	}

	public GeneSegment copy() {
		return new GSDivide(gene1.copy(), gene2.copy());
	}
	
	public String toString() {
		return "( "+gene1.toString()+" / "+gene2.toString()+" )";
	}
	
}
