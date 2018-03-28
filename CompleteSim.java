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
		/**
		 * Runs a sim of a web on the given parameters.
		 * @param web The web being simulated.
		 * @param webLength The amount of web to be used.
		 * @param minAnchors The minimum number of anchors to be tested on.
		 * @param maxAnchors The maximum number of anchors to be tested on (max-min is the range).
		 * @return The completed sim.
		 */
		public static CompleteSim getSim(Web web, int webLength, int minAnchors, int maxAnchors) {
			int bestFitness = 0;
			int bestAnchor = 0;
			int totalFitness = 0;
			for(int i = minAnchors;i<=maxAnchors;i++) {
				int fitness = web.getFitness(i, webLength);
				totalFitness+=fitness;
				if(fitness>=bestFitness) {
					bestFitness= fitness;
					bestAnchor = i;
				}
			}
			CompleteSim cs = new CompleteSim(web, bestAnchor, totalFitness);
			return cs;
		}
	}