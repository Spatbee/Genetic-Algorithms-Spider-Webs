import java.io.Serializable;
import java.util.ArrayList;

public class Web implements Serializable{
	private GeneSegment start, end, startRatio, endRatio;
	/**
	 * Creates a new, random, Web.
	 */
	public Web() {
		this.start = GeneCreator.newGene();
		this.end = GeneCreator.newGene();
		this.startRatio = GeneCreator.newGene();
		this.endRatio = GeneCreator.newGene();
	}
	/**
	 * Creates a new web.
	 * @param start The gene controlling the start point of new strands.
	 * @param end The gene controlling the end point of new strands.
	 * @param startRatio The gene controlling where on the start point the new strand will start from.
	 * @param endRatio The gene controlling where on the end point the new strand will end at.
	 */
	public Web(GeneSegment start, GeneSegment end, GeneSegment startRatio, GeneSegment endRatio) {
		this.start = start;
		this.end = end;
		this.startRatio = startRatio;
		this.endRatio = endRatio;
	}
	/**
	 * Returns a mutated version of this Web.  All genes are possibly mutated in this new web.
	 * @return The mutated version of this Web.
	 */
	public Web mutate() {
		return new Web(this.start.mutate(), this.end.mutate(), this.startRatio.mutate(), this.endRatio.mutate());
	}
	public String toString() {
		String startString = "Start: "+this.start.toString()+"\n";
		String endString = "End: "+this.end.toString()+"\n";
		String startRatioString = "Start ratio: "+this.startRatio.toString()+"\n";
		String endRatioString = "End ratio: "+this.endRatio.toString();
		return startString+endString+startRatioString+endRatioString;
	}
	/**
	 * Gets the next strand for this Web, according to this Web's genes.
	 * @param pieces The current Anchors and Strands in the web.
	 * @param numAnchors The number of anchors in the web.
	 * @return A new Strand conforming to the genes of this Web.
	 */
	public Strand getNext(ArrayList<WebComponent> pieces, int numAnchors) {
		int numStrands = pieces.size()-numAnchors;
		int numPieces = pieces.size();
		int startIndex = Math.floorMod((int)Math.floor(this.start.evaluate(numAnchors, numStrands)),numPieces);
		int endIndex = Math.floorMod((int)Math.floor(this.end.evaluate(numAnchors, numStrands)),numPieces);
		double fixedStartRatio = this.startRatio.evaluate(numAnchors, numStrands);
		double fixedEndRatio = this.endRatio.evaluate(numAnchors, numStrands);
		if(fixedStartRatio<0) fixedStartRatio=0;
		else if(fixedStartRatio>1) fixedStartRatio=1;
		if(fixedEndRatio<0) fixedEndRatio=0;
		else if(fixedEndRatio>1) fixedEndRatio=1;
		return new Strand(pieces.get(startIndex).getPoint(fixedStartRatio),pieces.get(endIndex).getPoint(fixedEndRatio));
	}
	/**
	 * Gets the fitness of this web.
	 * @param numAnchors The number of anchors in the web.
	 * @param webLength The amount of web that can be used in this web (in radii).
	 * @return The fitness of this web, with the given parameters.  On a scale of 0 to 800, with 800 being a perfect score.
	 */
	public int getFitness(int numAnchors, double webLength) {
		WebDrawer wd = new WebDrawer(this);
		return wd.buildWeb(numAnchors, webLength);
		
	}
	/**
	 * Creates a copy of this Web;
	 * @return The copy of this Web;
	 */
	public Web copy() {
		return new Web(start.copy(), end.copy(), startRatio.copy(), endRatio.copy());
	}
	
	public static void main(String[] args) {
		Web web = new Web();
		System.out.println("first web:");
		System.out.println(web);
		System.out.println();
		Web newWeb = web.mutate();
		System.out.println("first web mutated:");
		System.out.println(newWeb);
	}
}
