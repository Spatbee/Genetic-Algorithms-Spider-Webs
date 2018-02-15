import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Anchor implements WebComponent {
	private Point point;
	public Anchor (Point point) {
		this.point = point;
	}
	public Anchor (double x, double y) {
		this(new Point(x,y));
	}
	public Point getPoint(double ratio) {
		return point;
	}
	public String toString() {
		return "Anchor at "+point.toString();
	}
	public void drawSelf(Graphics g, JPanel canvas) {
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		g.setColor(Color.BLACK);
		g.fillOval(WebDrawer.rangeFit(width, point.getX())-width/45/2
				, WebDrawer.rangeFit(height, point.getY())-height/45/2
				, width/45, height/45);
	}
	public boolean intersects(double x, double y, double r) {
		return false;//anchors to not intersect circles
	}

}
