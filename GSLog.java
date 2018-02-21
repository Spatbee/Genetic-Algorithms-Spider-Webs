
public class GSLog implements GeneSegment{
	private double base;
	private GeneSegment inner;
	
	/**
	 * Creates a new, random, GSLog.
	 */
	public GSLog() {
		this(Math.random()*10+1,GeneCreator.newGene());
	}
	/**
	 * Creates a new GSLog gene segment.
	 * @param base The base of the log function.
	 * @param inner The inside of the log function.
	 */
	public GSLog(double base, GeneSegment inner) {
		this.base = base;
		this.inner = inner;
	}
	
	public GeneSegment mutate() {
		double rand = Math.random();
		if(rand<.1) {
			return GeneCreator.newGene();
		}
		if(rand<.3) {
			double newBase = this.base + (Math.random()-.5);
			return new GSLog(newBase, inner.mutate());
		}
		else {
			return new GSLog(base, inner.mutate());
		}
	}

	public double evaluate(int numAnchors, int numStrands) {
		try {
			return Math.log(inner.evaluate(numAnchors, numStrands)) / Math.log(base);
		}
		catch(Exception e) {
			return 0;
		}
	}


	public GeneSegment copy() {
		return new GSLog(base, inner.copy());
	}
	
	public String toString() {
		return String.format("log%.2f( "+inner.toString()+" )", base);
	}

}
