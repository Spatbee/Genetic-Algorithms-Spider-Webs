
public class GeneCreator {
	public static GeneSegment newGene() {
		int rand = (int)(Math.random()*1);
		switch (rand) {
		case 1: return new GSConstant();
		}
		return new GSConstant();
	}

}
