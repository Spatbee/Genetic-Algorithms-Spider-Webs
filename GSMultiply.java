
public class GSMultiply implements GeneSegment {
	public GeneSegment gene1, gene2;
	
	/**
	 * Creates a new, random, GSMultiply.
	 */
	public GSMultiply() {
		this(GeneCreator.newGene(), GeneCreator.newGene());
	}
	/**
	 * Creates a new GSMultiply gene segment.
	 * @param gene1 The first gene to be multiplied.
	 * @param gene2 The second gene to be multiplied.
	 */
	public GSMultiply(GeneSegment gene1, GeneSegment gene2) {
		this.gene1 = gene1;
		this.gene2 = gene2;
	}
	public GeneSegment mutate() {
		if(Math.random()<.1) return GeneCreator.newGene();
		return new GSMultiply(gene1.mutate(), gene2.mutate());
	}


	public double evaluate(int numAnchors, int numStrands) {
		try {
			return gene1.evaluate(numAnchors, numStrands)*gene2.evaluate(numAnchors, numStrands);
		}
		catch(Exception e) {
			return 0;
		}
	}


	public GeneSegment copy() {
		return new GSMultiply(gene1.copy(), gene2.copy());
	}
	
	public String toString() {
		return "( "+gene1.toString()+" * "+gene2.toString()+" )";
	}
	
}
