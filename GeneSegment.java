
public interface GeneSegment {
	public GeneSegment mutate();
	public double evaluate(int numAnchors, int numStrands);
	public GeneSegment copy();
	public String toString();
}
