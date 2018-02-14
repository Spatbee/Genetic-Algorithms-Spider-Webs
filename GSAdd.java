
public class GSAdd implements GeneSegment {
	public GeneSegment gene1, gene2;
	
	public GSAdd() {
		this(GeneCreator.newGene(), GeneCreator.newGene());
	}
	
	public GSAdd(GeneSegment gene1, GeneSegment gene2) {
		this.gene1 = gene1;
		this.gene2 = gene2;
	}
	public GeneSegment mutate() {
		return new GSAdd(gene1.mutate(), gene2.mutate());
	}

	public double evaluate(int numAnchors, int numStrands) {
		try {
			return gene1.evaluate(numAnchors, numStrands)+gene2.evaluate(numAnchors, numStrands);
		}
		catch(Exception e) {
			return 0;
		}
	}

	public GeneSegment copy() {
		return new GSAdd(gene1.copy(), gene2.copy());
	}
	
	public String toString() {
		return gene1.toString()+" + "+gene2.toString();
	}
	
}
