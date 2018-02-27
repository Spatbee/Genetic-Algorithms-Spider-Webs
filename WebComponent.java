import java.awt.Graphics;
import java.io.Serializable;

import javax.swing.JPanel;

public interface WebComponent extends Serializable{
	/**
	 * Gets a point along the stretch of this WebComponent.
	 * @param ratio The ratio from the start point to the end point of this WebComponent (if applicable).
	 * @return The point along this WebComponent.
	 */
	public Point getPoint(double ratio);
	/**
	 * Draws this WebComponent.
	 * @param g The Graphics of the JPanel.
	 * @param canvas The JPanel to draw on.
	 */
	public void drawSelf(Graphics g, JPanel canvas);
	/**
	 * Determines if a circle intersects with this WebComponent.
	 * @param x The x coordinate of the center of the circle.
	 * @param y The y coordinate of the center of the circle.
	 * @param r The radius of the circle.
	 * @return True if this intersects with the circle, false otherwise.
	 */
	public boolean intersects(double x, double y, double r);
}
