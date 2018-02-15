import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WebSimulator extends JFrame{
	private PriorityQueue<CompleteSim> lastGeneration, thisGeneration;
	private static final String windowTitle = "Web Simulator";
	private ArrayList<WebDrawer> allTime, lastGen, thisGen, recent;
	private final int previewSize = 180;
	private boolean firstGen = true;
	private int webLength = 100;
	private WebDrawer currentWeb = new WebDrawer();
	public WebSimulator() {
		super(windowTitle);
		
		allTime = new ArrayList<WebDrawer>();
		lastGen = new ArrayList<WebDrawer>();
		thisGen = new ArrayList<WebDrawer>();
		recent = new ArrayList<WebDrawer>();
		
		Comparator<CompleteSim> c = new Comparator<CompleteSim>() {

			public int compare(CompleteSim o1, CompleteSim o2) {
				return o2.totalFitness-o1.totalFitness;
			}

		};
		lastGeneration = new PriorityQueue<CompleteSim>(c);
		thisGeneration = new PriorityQueue<CompleteSim>(c);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		JPanel webQueues = new JPanel();
		webQueues.setLayout(new BoxLayout(webQueues, BoxLayout.X_AXIS));
		JPanel allTimeDisplay = new JPanel();
		allTimeDisplay.setLayout(new BoxLayout(allTimeDisplay, BoxLayout.Y_AXIS));
		allTimeDisplay.setPreferredSize(new Dimension(previewSize,previewSize*4+50));
		JTextField allTimeDisplayLabel = new JTextField("All time best");
		allTimeDisplayLabel.setEditable(false);
		allTimeDisplay.add(allTimeDisplayLabel);
//		WebDrawer wd4 = new WebDrawer();
//		wd4.buildWeb(5, 100);
//		wd4.setPreferredSize(new Dimension(200,200));
//		allTimeDisplay.add(wd4);
		JPanel lastGenDisplay = new JPanel();
		lastGenDisplay.setLayout(new BoxLayout(lastGenDisplay, BoxLayout.Y_AXIS));
		lastGenDisplay.setPreferredSize(new Dimension(previewSize,previewSize*4+50));
		JTextField lastGenDisplayLabel = new JTextField("Best last generation");
		lastGenDisplayLabel.setEditable(false);
		lastGenDisplay.add(lastGenDisplayLabel);
		
		JPanel thisGenDisplay = new JPanel();
		thisGenDisplay.setLayout(new BoxLayout(thisGenDisplay, BoxLayout.Y_AXIS));
		thisGenDisplay.setPreferredSize(new Dimension(previewSize,previewSize*4+50));
		JTextField thisGenDisplayLabel = new JTextField("Best this generation");
		thisGenDisplayLabel.setEditable(false);
		thisGenDisplay.add(thisGenDisplayLabel);
		
		JPanel recentDisplay = new JPanel();
		recentDisplay.setLayout(new BoxLayout(recentDisplay, BoxLayout.Y_AXIS));
		recentDisplay.setPreferredSize(new Dimension(previewSize,previewSize*4+50));
		JTextField recentDisplayLabel = new JTextField("Most recent");
		recentDisplayLabel.setEditable(false);
		recentDisplay.add(recentDisplayLabel);
		
		webQueues.add(allTimeDisplay);
		webQueues.add(lastGenDisplay);
		webQueues.add(thisGenDisplay);
		webQueues.add(recentDisplay);
		webQueues.setMaximumSize(new Dimension(previewSize*4,previewSize*4+50));
		this.add(webQueues);
		
		JPanel controls = new JPanel();
		controls.setPreferredSize(new Dimension(300,previewSize*4+50));
		JButton stepOneGeneration = new JButton("Step one generation");
		stepOneGeneration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				runOneGeneration();
			}
		});
		controls.add(stepOneGeneration);
		currentWeb.setPreferredSize(new Dimension(300,300));
		controls.add(currentWeb);
		this.add(controls);
		
		this.pack();
		this.setVisible(true);
	}
	
	private CompleteSim runSim(Web web) {
		int bestFitness = 0;
		int bestAnchor = 0;
		int totalFitness = 0;
		for(int i = 4;i<9;i++) {
			int fitness = web.getFitness(i, webLength);
			totalFitness+=fitness;
			if(fitness>=bestFitness) {
				bestFitness= fitness;
				bestAnchor = i;
			}
		}
		currentWeb.newWeb(web);
		currentWeb.buildWeb(bestAnchor, webLength);
		currentWeb.paintComponent(currentWeb.getGraphics());
		return new CompleteSim(web, bestAnchor, totalFitness);
	}
	
	private void runOneGeneration() {
		if(firstGen) {
			firstGen = false;
			for(int i = 0; i<36; i++) {
				thisGeneration.add(runSim(new Web()));
			}
		}
	}
	
	private class CompleteSim{
		public Web web;
		public int bestAnchor, totalFitness;
		public CompleteSim(Web web, int bestAnchor, int totalFitness) {
			this.web = web;
			this.bestAnchor = bestAnchor;
			this.totalFitness = totalFitness;
		}
	}
	
	public static void main(String[] args) {
		WebSimulator webSim = new WebSimulator();
	}
	
}
