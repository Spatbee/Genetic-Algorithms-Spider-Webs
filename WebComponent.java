import java.awt.Graphics;

import javax.swing.JPanel;

public interface WebComponent {
	public Point getPoint(double ratio);
	public void drawSelf(Graphics g, JPanel canvas);
	public boolean intersects(double x, double y, double r);
}
