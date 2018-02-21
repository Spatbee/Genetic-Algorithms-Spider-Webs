
public class GeneCreator {
	/**
	 * Gets a new, random gene.  Terminating genes are more likely.
	 * @return A random GeneSegment extended object.
	 */
	public static GeneSegment newGene() {
		double t = Math.random();
		double rand = Math.random();
		//non-terminating
		if(t<.33) {
			if(rand<.08) return new GSLog();
			if(rand<.24) return new GSMultiply();
			if(rand<.40) return new GSDivide();
			if(rand<.56) return new GSAdd();
			if(rand<.72) return new GSSubtract();
			if(rand<.8) return new GSSine();
			if(rand<.9) return new GSCosine();
			return new GSTangent();
		}
		//terminating
		if(rand<.2) return new GSRandom();
		if(rand<.4) return new GSNumAnchors();
		if(rand<.6) return new GSNumStrands();
		return new GSConstant();
	}

}
