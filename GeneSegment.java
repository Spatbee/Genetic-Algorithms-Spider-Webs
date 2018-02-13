
public interface GeneSegment {
	public GeneSegment mutate();
	public double evaluate();
	public GeneSegment copy();
	public String toString();
}
