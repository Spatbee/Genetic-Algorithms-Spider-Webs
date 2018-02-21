import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Strand implements WebComponent {
	private Point start, end;
	/**
	 * Creates a strand going from one point to another.  These points are on the unit circle.
	 * @param start The start point.
	 * @param end The end point.
	 */
	public Strand(Point start, Point end) {
		this.start = start;
		this.end = end;
	}
	/**
	 * Creates a strand going from one point to another.  These points are on the unit circle.
	 * @param x1 The x coordinate of the start point.
	 * @param y1 The y coordinate of the start point.
	 * @param x2 The x coordinate of the end point.
	 * @param y2 The y coordinate of the end point.
	 */
	public Strand(double x1, double y1, double x2, double y2) {
		this(new Point(x1,y1), new Point(x2,y2));
	}
	/**
	 * Gets the point between start and end at the given ratio.
	 * 
	 * @param ratio  The desired ratio between start and end. (0 is start, 1 is end).
	 * 
	 * @return The point on the line between the start and end of this line at the desired ratio.
	 */
	public Point getPoint(double ratio){
		if(ratio<0||ratio>1) {
			throw new IllegalArgumentException("ratio out of bounds");
		}
		double x3 = start.getX() + (end.getX()-start.getX())*ratio;
		double y3 = start.getY() + (end.getY()-start.getY())*ratio;
		return new Point(x3,y3);
	}
	/**
	 * Gets the length of this Strand.
	 * @return The length of this strand.
	 */
	public double getLength() {
		//distance formula
		return Math.sqrt(Math.pow(start.getX()-end.getX(),2)+Math.pow(start.getY()-end.getY(), 2));
	}
	
	public String toString() {
		return String.format("Strand from "+start.toString()+" to "+end.toString());
	}
	/**
	 * Draws this strand on a JPanel.
	 * 
	 * @param g The graphics of the target JPanel.
	 * @param canvas The canvas to draw on.
	 */
	public void drawSelf(Graphics g, JPanel canvas) {
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		g.setColor(Color.GRAY);
		g.drawLine(WebDrawer.rangeFit(width, start.getX()), WebDrawer.rangeFit(height, start.getY())
				,WebDrawer.rangeFit(width, end.getX()), WebDrawer.rangeFit(height, end.getY()));
	}
	
	/**
	 * Sees if this strand intersects with a circle.
	 * 
	 * @param x3 The x coordinate of the center of the circle.
	 * @param y3 The y coordinate of the center of the circle.
	 * @param r The radius of the circle.
	 * 
	 * @return true if there exists a point of intersection between the strand and the circle.  False otherwise. Also false if this strand is vertical, as it has an infinite slope.
	 */
	public boolean intersects(double x3, double y3, double r) {
		//if(this.getLength()<2*r)return false;
		double x1 = this.start.getX();
		double y1 = this.start.getY();
		double x2 = this.end.getX();
		double y2 = this.end.getY();
		if(Math.abs(x2-x1)<0.00000001) {
			//TODO verticle lines
			return false;
		}
		double m = (y2-y1)/(x2-x1);
		double a = 1+m*m;
		double bFactor = -m*x1 + y1 - y3;
		double b = 2*m*bFactor-2*x3;
		double c = x3*x3+bFactor*bFactor-r*r;
		double b4ac = b*b-4*a*c;
//		System.out.println("a: "+a);
//		System.out.println("b: "+b);
//		System.out.println("c: "+c);
		if(b4ac<0) {
			return false;
		}
		double answer1 = (-b+Math.sqrt(b4ac))/(2*a);
		//System.out.println(answer1);
		double answer2 = (-b-Math.sqrt(b4ac))/(2*a);
		//System.out.println(answer2);
		double smallX = Math.min(x1, x2);
		double bigX = Math.max(x1, x2);
		if(answer1>=smallX&&answer1<=bigX||answer2>=smallX&&answer2<=bigX)return true;
		return false;
	}
	
	public static void main(String[] args) {
//		Strand s1 = new Strand(new Point(0.0,0.0), new Point(2.0,2.0));
//		System.out.println(s1.intersects(1.0, 1.0, 0.5));
//		System.out.println(s1.intersects(1.0, 1.0, 1.5));
//		System.out.println(s1.intersects(1.0, 1.0, 0.05));
//		System.out.println(s1.intersects(2.0, 1.0, 0.5));
//		System.out.println(s1.intersects(10.0, 1.0, 0.5));
		Strand s2 = new Strand(new Point(3,7), new Point(2,1));
		System.out.println(s2.intersects(2, 5, 1.5));
	}
}
