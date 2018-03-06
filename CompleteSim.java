import java.io.Serializable;

public class CompleteSim implements Serializable{
		public Web web;
		public int bestAnchor, totalFitness;
		public CompleteSim(Web web, int bestAnchor, int totalFitness) {
			this.web = web;
			this.bestAnchor = bestAnchor;
			this.totalFitness = totalFitness;
		}
		
		public CompleteSim copy() {
			return new CompleteSim(web.copy(), bestAnchor, totalFitness);
		}
		
		public String toString() {
			return "anchor: "+bestAnchor+"\n"+
					"fitness: "+totalFitness+"\n"+
					web;
		}
	}