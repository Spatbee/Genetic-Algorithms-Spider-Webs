import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Strand implements WebComponent {
	private Point start, end;
	
	public Strand(Point start, Point end) {
		this.start = start;
		this.end = end;
	}
	public Strand(float x1, float y1, float x2, float y2) {
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
	
	public double getLength() {
		//distance formula
		return Math.sqrt(Math.pow(start.getX()-end.getX(),2)+Math.pow(start.getY()-end.getY(), 2));
	}
	
	public String toString() {
		return String.format("Strand from "+start.toString()+" to "+end.toString());
	}
	
	public void drawSelf(Graphics g, JPanel canvas) {
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		g.setColor(Color.GRAY);
		g.drawLine(WebDrawer.rangeFit(width, start.getX()), WebDrawer.rangeFit(height, start.getY())
				,WebDrawer.rangeFit(width, end.getX()), WebDrawer.rangeFit(height, end.getY()));
	}
	//modified from:
	//https://stackoverflow.com/questions/13053061/circle-line-intersection-points
	public boolean intersects(double x, double y, double r) {
		double baX = end.getX() - start.getX();
        double baY = end.getY() - start.getY();
        double caX = x - start.getX();
        double caY = y - start.getY();

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - r * r;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return false;
        }
        return true;
	}
}
