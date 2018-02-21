import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Anchor implements WebComponent {
	private Point point;
	/**
	 * Creates an Anchor object at the given point.  The point is on the unit circle.
	 * @param point The unit circle point.
	 */
	public Anchor (Point point) {
		this.point = point;
	}
	/**
	 * Creates an Anchor object at the given point.  THe point is on the unit circle.
	 * @param x The point's x coordinate.
	 * @param y The point's y coordinate.
	 */
	public Anchor (double x, double y) {
		this(new Point(x,y));
	}
	/**
	 * Gets the point of this Anchor.
	 * @return The point of this Anchor.
	 */
	public Point getPoint(double ratio) {
		return point;
	}
	public String toString() {
		return "Anchor at "+point.toString();
	}
	
	/**
	 * Draws this anchor on a JPanel.
	 * 
	 * @param g The graphics of the target JPanel.
	 * @param canvas The canvas to draw on.
	 */
	public void drawSelf(Graphics g, JPanel canvas) {
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		g.setColor(Color.BLACK);
		g.fillOval(WebDrawer.rangeFit(width, point.getX())-width/45/2
				, WebDrawer.rangeFit(height, point.getY())-height/45/2
				, width/45, height/45);
	}
	/**
	 * Sees if this intersects with a circle.
	 * 
	 * @param x The x coordinate of the center of the circle.
	 * @param y The y coordinate of the center of the circle.
	 * @param r The radius of the circle.
	 * 
	 * @return Always false.  Anchors cannot intersect circles.
	 */
	public boolean intersects(double x, double y, double r) {
		return false;//anchors to not intersect circles
	}

}
