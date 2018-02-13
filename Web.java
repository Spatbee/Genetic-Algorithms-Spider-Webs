import java.util.ArrayList;

public class Web {
	private GeneSegment start, end, startRatio, endRatio;
	
	public Web() {
		this.start = GeneCreator.newGene();
		this.end = GeneCreator.newGene();
		this.startRatio = GeneCreator.newGene();
		this.endRatio = GeneCreator.newGene();
	}
	public Web(GeneSegment start, GeneSegment end, GeneSegment startRatio, GeneSegment endRatio) {
		this.start = start;
		this.end = end;
		this.startRatio = startRatio;
		this.endRatio = endRatio;
	}
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
	
	public Strand getNext(ArrayList<WebComponent> pieces) {
		int numPieces = pieces.size();
		int startIndex = Math.floorMod((int)Math.floor(this.start.evaluate()),numPieces);
		int endIndex = Math.floorMod((int)Math.floor(this.end.evaluate()),numPieces);
		double fixedStartRatio = this.startRatio.evaluate();
		double fixedEndRatio = this.endRatio.evaluate();
		if(fixedStartRatio<0) fixedStartRatio=0;
		else if(fixedStartRatio>1) fixedStartRatio=1;
		if(fixedEndRatio<0) fixedEndRatio=0;
		else if(fixedEndRatio>1) fixedEndRatio=1;
		return new Strand(pieces.get(startIndex).getPoint(fixedStartRatio),pieces.get(endIndex).getPoint(fixedEndRatio));
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
