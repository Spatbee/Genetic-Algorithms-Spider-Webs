
public class GeneCreator {
	public static GeneSegment newGene() {
		double t = Math.random();
		double rand = Math.random();
		//non-terminating
		if(t<.33) {
			if(rand<.1) return new GSLog();
			if(rand<.2) return new GSMultiply();
			if(rand<.3) return new GSDivide();
			if(rand<.4) return new GSAdd();
			if(rand<.5) return new GSSubtract();
		}
		//terminating
		if(rand<.1) return new GSRandom();
		if(rand<.2) return new GSNumAnchors();
		if(rand<.3) return new GSNumStrands();
		return new GSConstant();
	}

}
