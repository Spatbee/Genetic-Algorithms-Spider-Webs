
public class GSConstant implements GeneSegment {
	private double value;
	
	/**
	 * Creates a new, random, GSConstant.
	 */
	public GSConstant() {
		this.value =Math.random()*10-5;
	}
	
	/**
	 * Creates a new GSContstant gene segment.
	 * @param value The value of the constant.
	 */
	public GSConstant(double value) {
		this.value = value;
	}
	
	public GeneSegment mutate() {
		double rand = Math.random();
		if(rand<.1) {
			return GeneCreator.newGene();
		}
		if(rand<.3) {
			double newValue = this.value + (Math.random()-.5);
			return new GSConstant(newValue);
		}
		else {
			return this.copy();
		}
	}

	public double evaluate(int numAnchors, int numStrands) {
		return this.value;
	}

	public GeneSegment copy() {
		return new GSConstant(value);
	}

	public String toString() {
		return String.format("%.2f", this.value);
	}
}
