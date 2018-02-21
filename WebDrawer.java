
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class WebDrawer extends JPanel{
	private Web web;
	private ArrayList<WebComponent> webPieces;
	//ArrayList<Circle> bugs = new ArrayList<Circle>();
	
	
	/**
	 * Creates a new, random, WebDrawer.
	 */
	public WebDrawer() {
		this(new Web());
	}
	
	/**
	 * Creates a new WebDrawer.
	 * @param web The Web that this WebDrawer will draw.
	 */
	public WebDrawer(Web web) {
		this.web = web;
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		webPieces = new ArrayList<WebComponent>();
	}
	/**
	 * Gives this WebDrawer a new Web.
	 * @param web The new Web.
	 */
	public void newWeb(Web web) {
		this.web = web;
	}
	/**
	 * Fits a double on the unit circle to an int inside this JPanel.
	 * @param size The size of the desired range.  Either the width or height of this.
	 * @param loc The coordinate in the unit circle.
	 * @return The int relating to the coordinate in the unit circle, in this JPanel's dimensions, with a buffer size of 1/25 the size of this.
	 */
	public static int rangeFit(int size, double loc) {
		int min = size/25;
		int max = size*24/25;
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
		/*g.setColor(Color.RED);
		for(Circle c : bugs) {
			g.fillOval(c.x-c.r, c.y-c.r, 2*c.r, 2*c.r);
		}*/
	}
	
	/**
	 * Builds a web using the genes from the contained Web.
	 * 
	 * @param numAnchors The number of anchor points in this web.
	 * @param webLength The maximum length of the strands in the web in total.
	 * 
	 * @return The fitness of this web.
	 */
	public int buildWeb(int numAnchors, double webLength) {
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
		return getFitness();
	}
	
	public String toString() {
		String s = "";
		for(int i = 0; i<webPieces.size(); i++) {
			s = s + webPieces.get(i).toString()+"\n";
		}
		return s;
	}
	/**
	 * Gets the web inside this.
	 * @return The web inside this.
	 */
	public Web getWeb() {
		return web;
	}
	/**
	 * Gets the fitness of the web inside this.  buildWeb() must be used first.  The fitness is on a scale of 0 to 800, with 800 being a perfect score.
	 * @return The fitness of the web currently inside this.
	 */
	private int getFitness() {
		//bugs = new ArrayList<Circle>();
		int score = 0;
		for(int i = 0; i<800; i++) {
			int multiplier = i/200+2;
			double r = 1.0/100*multiplier;//200 small, 200 medium, 200 big bugs
			double x = Math.random()*2-1;
			double y = Math.random()*2-1;
			while(x*x+y*y>1) {//random point in the unit circle
				x = Math.random()*2-1;
				y = Math.random()*2-1;
			}
			//bugs.add(new Circle(x,y,r,this.getWidth(),this.getHeight()));
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
	private class Circle{
		int x, y, r;
		public Circle(double x, double y, double r, int width, int height) {
			this.r = (int)(width*23/25*r);
			this.x = (int)(WebDrawer.rangeFit(width, x)-r);
			this.y = (int)(WebDrawer.rangeFit(height, y)-r);
		}
	}
}

