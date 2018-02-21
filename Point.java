
public class Point {
	private double x,y;
	/**
	 * Creates a point at (x,y).
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Gets the x coordinate of this point.
	 * @return The x coordinate.
	 */
	public double getX() {
		return this.x;
	}
	/**
	 * Gets the y coordinate of this point.
	 * @return The y coordinate.
	 */
	public double getY() {
		return this.y;
	}
	public String toString() {
		return String.format("(%.2f,%.2f)", x,y);
	}
}
