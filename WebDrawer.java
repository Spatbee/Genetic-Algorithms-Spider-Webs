import java.util.ArrayList;

public class WebDrawer {
	private Web web;
	private ArrayList<WebComponent> webPieces;
	
	public WebDrawer(Web web) {
		this.web = web;
		
	}
	
	/**
	 * Builds a web using the genes from the contained Web.
	 * 
	 * @param numAnchors The number of anchor points in this web.
	 * @param webLength The maximum length of the strands in the web in total.
	 */
	public void buildWeb(int numAnchors, double webLength) {
		//clears old web
		webPieces = new ArrayList<WebComponent>();
		
		if(numAnchors<3) {
			throw new IllegalArgumentException("numAnchors is out of range (must be greater than 2)");
		}
		//adds the anchors to the circle
		double degreeDif = 360.0/numAnchors;
		double radDif = degreeDif*Math.PI/180;
		double curAngleRads = 0;
		for(int i = 0; i<numAnchors; i++) {
			webPieces.add(new Anchor(Math.cos(curAngleRads),Math.sin(curAngleRads)));
			curAngleRads += radDif;
		}
		
		//adds strands until out of web
		double totalWebUsed = 0;
		while(totalWebUsed <= webLength) {
			Strand newStrand = web.getNext(webPieces);
			webPieces.add(newStrand);
			//if the strand added was length 0, kill the web
			if(newStrand.getLength()==0) {
				System.out.println("zero length strand");
				break;
			}
			//if there are too many strands
			if(webPieces.size()>1000) {
				System.out.println("too many strands");
				break;
			}
			totalWebUsed += newStrand.getLength();
		}
		//removes the last Strand added (it went over the length)
		webPieces.remove(webPieces.size()-1);
	}
	
	public String toString() {
		String s = "";
		for(int i = 0; i<webPieces.size(); i++) {
			s = s + webPieces.get(i).toString()+"\n";
		}
		return s;
	}
	
	public static void main(String[] args) {
		Web web = new Web();
		WebDrawer webDrawer = new WebDrawer(web);
		System.out.println("Web:");
		System.out.println(web);
		System.out.println();
		webDrawer.buildWeb(4, 4);
		System.out.println(webDrawer);
	}
}
