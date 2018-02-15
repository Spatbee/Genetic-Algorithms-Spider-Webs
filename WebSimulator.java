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
	private PriorityQueue<CompleteSim> lastGeneration, thisGeneration, bestAllTimePQ;
	private static final String windowTitle = "Web Simulator";
	private WebDrawer[] allTime, lastGen, thisGen, recent;
	private final int previewSize = 180;
	private boolean firstGen = true;
	private int webLength = 100;
	private WebDrawer currentWeb = new WebDrawer();
	private ArrayList<CompleteSim> recentSims = new ArrayList<CompleteSim>();
	private Comparator<CompleteSim> c = new Comparator<CompleteSim>() {

		public int compare(CompleteSim o1, CompleteSim o2) {
			return o2.totalFitness-o1.totalFitness;
		}

	};
	public WebSimulator() {
		super(windowTitle);
		
		allTime = new WebDrawer[4];
		for(int i = 0; i<4; i++) {
			allTime[i] = new WebDrawer();
			allTime[i].setPreferredSize(new Dimension(previewSize, previewSize));
		}
		lastGen = new WebDrawer[4];
		for(int i = 0; i<4; i++) {
			lastGen[i] = new WebDrawer();
			lastGen[i].setPreferredSize(new Dimension(previewSize, previewSize));
		}
		thisGen = new WebDrawer[4];
		for(int i = 0; i<4; i++) {
			thisGen[i] = new WebDrawer();
			thisGen[i].setPreferredSize(new Dimension(previewSize, previewSize));
		}
		recent = new WebDrawer[4];
		for(int i = 0; i<4; i++) {
			recent[i] = new WebDrawer();
			recent[i].setPreferredSize(new Dimension(previewSize, previewSize));
		}
		
		
		lastGeneration = new PriorityQueue<CompleteSim>(c);
		thisGeneration = new PriorityQueue<CompleteSim>(c);
		bestAllTimePQ = new PriorityQueue<CompleteSim>(c);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		JPanel webQueues = new JPanel();
		webQueues.setLayout(new BoxLayout(webQueues, BoxLayout.X_AXIS));
		JPanel allTimeDisplay = new JPanel();
		allTimeDisplay.setLayout(new BoxLayout(allTimeDisplay, BoxLayout.Y_AXIS));
		allTimeDisplay.setPreferredSize(new Dimension(previewSize,previewSize*4+50));
		for(int i = 0; i<4; i++) {
			allTimeDisplay.add(allTime[i]);
		}
		JTextField allTimeDisplayLabel = new JTextField("All time best");
		allTimeDisplayLabel.setEditable(false);
		allTimeDisplay.add(allTimeDisplayLabel);
		
		JPanel lastGenDisplay = new JPanel();
		lastGenDisplay.setLayout(new BoxLayout(lastGenDisplay, BoxLayout.Y_AXIS));
		lastGenDisplay.setPreferredSize(new Dimension(previewSize,previewSize*4+50));
		for(int i = 0; i<4; i++) {
			lastGenDisplay.add(lastGen[i]);
		}
		JTextField lastGenDisplayLabel = new JTextField("Best last generation");
		lastGenDisplayLabel.setEditable(false);
		lastGenDisplay.add(lastGenDisplayLabel);
		
		JPanel thisGenDisplay = new JPanel();
		thisGenDisplay.setLayout(new BoxLayout(thisGenDisplay, BoxLayout.Y_AXIS));
		thisGenDisplay.setPreferredSize(new Dimension(previewSize,previewSize*4+50));
		for(int i = 0; i<4; i++) {
			thisGenDisplay.add(thisGen[i]);
		}
		JTextField thisGenDisplayLabel = new JTextField("Best this generation");
		thisGenDisplayLabel.setEditable(false);
		thisGenDisplay.add(thisGenDisplayLabel);
		
		JPanel recentDisplay = new JPanel();
		recentDisplay.setLayout(new BoxLayout(recentDisplay, BoxLayout.Y_AXIS));
		recentDisplay.setPreferredSize(new Dimension(previewSize,previewSize*4+50));
		for(int i = 0; i<4; i++) {
			recentDisplay.add(recent[i]);
		}
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
	private void updateWebDrawer(WebDrawer target, CompleteSim cs) {
		target.newWeb(cs.web);
		target.buildWeb(cs.bestAnchor, webLength);
		target.paintComponent(target.getGraphics());
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
		CompleteSim cs = new CompleteSim(web, bestAnchor, totalFitness);
		updateWebDrawer(currentWeb, cs);
		return cs;
	}
	
	private void runOneGeneration() {
		if(firstGen) {
			firstGen = false;
			for(int i = 0; i<38; i++) {
				CompleteSim cs = runSim(new Web());
				thisGeneration.add(cs);
				populateRecent(cs);
				//populateBestThisGen();
				populateBestAllTime(cs);
			}
			
		}
		else {
			lastGeneration = thisGeneration;
			thisGeneration = new PriorityQueue<CompleteSim>(c); 
			populateBestLastGen();
			for(int i = 0; i<2; i++) {
				CompleteSim cs = runSim(new Web());
				thisGeneration.add(cs);
				populateRecent(cs);
			}
			for(int i = 0; i<8; i++) {
				CompleteSim parent = lastGeneration.remove();
				for(int j = 8; j>i; j--) {
					CompleteSim child = runSim(parent.web.mutate());
					thisGeneration.add(child);
					populateRecent(child);
					//populateBestThisGen();
					populateBestAllTime(child);
				}
			}
			
		}
		
	}
	
	private void populateBestLastGen() {
		ArrayList<CompleteSim> temp = new ArrayList<CompleteSim>();
		for(int i = 0; i < 4; i++) {
			CompleteSim cs = lastGeneration.remove();
			updateWebDrawer(lastGen[i],cs);
			temp.add(cs);
		}
		for(int i = 0; i < 4; i++) lastGeneration.add(temp.remove(0));
	}
	
	private void populateBestAllTime(CompleteSim cs) {
		bestAllTimePQ.add(cs);
		ArrayList<CompleteSim> temp = new ArrayList<CompleteSim>();
		int size = bestAllTimePQ.size();
		for(int i = 0; i<size; i++) {
			CompleteSim best = bestAllTimePQ.remove();
			if(i<4)updateWebDrawer(allTime[i],best);
			temp.add(best);
		}
		for(int i = 0; i<4; i++) {
			if(temp.size()>0)bestAllTimePQ.add(temp.remove(0));
		}
	}
	
	private void populateRecent(CompleteSim cs) {
		recentSims.add(0,cs);
		for(int i = 0; i<4; i++) {
			if(recentSims.size()>i) {
				updateWebDrawer(recent[i],recentSims.get(i));
			}
		}
		if(recentSims.size()>4) {
			recentSims.remove(4);
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
