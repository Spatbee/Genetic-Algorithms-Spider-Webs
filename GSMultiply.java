
public class GSMultiply implements GeneSegment {
	public GeneSegment gene1, gene2;
	
	public GSMultiply() {
		this(GeneCreator.newGene(), GeneCreator.newGene());
	}
	
	public GSMultiply(GeneSegment gene1, GeneSegment gene2) {
		this.gene1 = gene1;
		this.gene2 = gene2;
	}
	public GeneSegment mutate() {
		return new GSMultiply(gene1.mutate(), gene2.mutate());
	}

	@Override
	public double evaluate(int numAnchors, int numStrands) {
		try {
			return gene1.evaluate(numAnchors, numStrands)*gene2.evaluate(numAnchors, numStrands);
		}
		catch(Exception e) {
			return 0;
		}
	}

	@Override
	public GeneSegment copy() {
		return new GSMultiply(gene1.copy(), gene2.copy());
	}
	
	public String toString() {
		return "( "+gene1.toString()+" * "+gene2.toString()+" )";
	}
	
}