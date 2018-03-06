import java.io.Serializable;
import java.util.Comparator;

public class FitnessComparator implements Comparator<CompleteSim>, Serializable{
		public int compare(CompleteSim o1, CompleteSim o2) {
			return o2.totalFitness-o1.totalFitness;
		}
	}