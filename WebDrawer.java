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
		if(numAnchors<3) {
			throw new IllegalArgumentException("numAnchors is out of range (must be greater than 2)");
		}
		//adds the anchors to the circle
		double degreeDif = 360.0/numAnchors;
		
	}
}
