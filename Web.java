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
	/**
	 * Returns a mutated version of the combination of this web and another web.  First the webs are combined
	 * randomly, then the result is mutated as normal.
	 * @param partner The web that will breed with this one.
	 * @return The offspring of this Web and another.
	 */
	public Web breed(Web partner) {
		GeneSegment offspringStart, offspringEnd, offspringStartRatio, offspringEndRatio;
		if(Math.random()<.5) offspringStart = this.start.mutate();
		else offspringStart = partner.getStartGene().mutate();
		if(Math.random()<.5) offspringEnd = this.end.mutate();
		else offspringEnd = partner.getEndGene().mutate();
		if(Math.random()<.5) offspringStartRatio = this.startRatio.mutate();
		else offspringStartRatio = partner.getStartRatioGene().mutate();
		if(Math.random()<.5) offspringEndRatio = this.endRatio.mutate();
		else offspringEndRatio = partner.getEndRatioGene().mutate();
		return new Web(offspringStart, offspringEnd, offspringStartRatio, offspringEndRatio);
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
	
	/**
	 * Gets the "start" gene of this Web
	 * @return
	 */
	public GeneSegment getStartGene() {
		return this.start;
	}
	/**
	 * Gets the "end" gene of this Web
	 * @return
	 */
	public GeneSegment getEndGene() {
		return this.end;
	}/**
	 * Gets the "start ratio" gene of this Web
	 * @return
	 */
	public GeneSegment getStartRatioGene() {
		return this.startRatio;
	}/**
	 * Gets the "end ratio" gene of this Web
	 * @return
	 */
	public GeneSegment getEndRatioGene() {
		return this.endRatio;
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
