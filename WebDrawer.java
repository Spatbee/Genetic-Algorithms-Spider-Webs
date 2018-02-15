import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class WebDrawer extends JPanel{
	private Web web;
	private ArrayList<WebComponent> webPieces;
	private int width;
	private int height;
	
	
	public WebDrawer() {
		this(new Web());
	}
	
	public WebDrawer(Web web) {
		this.web = web;
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		webPieces = new ArrayList<WebComponent>();
	}
	
	public void newWeb(Web web) {
		this.web = web;
	}
	
	public static int rangeFit(int size, double loc) {
		int min = size/25;
		int max = size*14/15;
		loc = (loc+1)/2;
		return (int) ((max-min)*loc+min);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = this.getWidth();
		int height = this.getHeight();
		//clears canvas
		g.setColor(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		g.fillRect(0, 0, width, height);
		//draws web
		for(WebComponent wc : webPieces) {
			wc.drawSelf(g, this);
		}
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
			Strand newStrand = web.getNext(webPieces, numAnchors);
			webPieces.add(newStrand);
			//if the strand added was length 0, kill the web
			if(newStrand.getLength()==0) {
				//System.out.println("zero length strand");
				break;
			}
			//if there are too many strands
			if(webPieces.size()>1000) {
				//System.out.println("too many strands");
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
	
	public Web getWeb() {
		return web;
	}
	
	public int getFitness() {
		int score = 0;
		for(int i = 0; i<800; i++) {
			double r = 1.0/100*(i/200.0+1);//200 small, 200 medium, 200 big bugs
			double x = Math.random()*2-1;
			double y = Math.random()*2-1;
			while(x*x+y*y>1) {//random point in the unit circle
				x = Math.random()*2-1;
				y = Math.random()*2-1;
			}
			for(WebComponent wc : webPieces) {
				if(wc.intersects(x,y,r)) {
					score++;
					break;
				}
			}
		}
		return score;
	}
	
	public static void main(String[] args) {
		Web web = new Web();
		WebDrawer webDrawer = new WebDrawer(web);
		System.out.println("Web:");
		System.out.println(web);
		System.out.println();
		webDrawer.buildWeb(4, 15);
		System.out.println(webDrawer);
		
	}
}
