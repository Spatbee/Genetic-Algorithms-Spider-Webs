
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

}
