import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WebSimulator extends JFrame{
	private static final String windowTitle = "Web Simulator";
	private ArrayList<WebDrawer> allTime, lastGen, thisGen, recent;
	public WebSimulator() {
		super(windowTitle);
		
		allTime = new ArrayList<WebDrawer>();
		lastGen = new ArrayList<WebDrawer>();
		thisGen = new ArrayList<WebDrawer>();
		recent = new ArrayList<WebDrawer>();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		JPanel webQueues = new JPanel();
		webQueues.setLayout(new BoxLayout(webQueues, BoxLayout.X_AXIS));
		JPanel allTimeDisplay = new JPanel();
		allTimeDisplay.setLayout(new BoxLayout(allTimeDisplay, BoxLayout.Y_AXIS));
		allTimeDisplay.setPreferredSize(new Dimension(200,900));
		JTextField allTimeDisplayLabel = new JTextField("All time best");
		allTimeDisplayLabel.setEditable(false);
		allTimeDisplay.add(allTimeDisplayLabel);
//		WebDrawer wd4 = new WebDrawer();
//		wd4.buildWeb(5, 100);
//		wd4.setPreferredSize(new Dimension(200,200));
//		allTimeDisplay.add(wd4);
		JPanel lastGenDisplay = new JPanel();
		lastGenDisplay.setLayout(new BoxLayout(lastGenDisplay, BoxLayout.Y_AXIS));
		lastGenDisplay.setPreferredSize(new Dimension(200,900));
		JTextField lastGenDisplayLabel = new JTextField("Best last generation");
		lastGenDisplayLabel.setEditable(false);
		lastGenDisplay.add(lastGenDisplayLabel);
		
		JPanel thisGenDisplay = new JPanel();
		thisGenDisplay.setLayout(new BoxLayout(thisGenDisplay, BoxLayout.Y_AXIS));
		thisGenDisplay.setPreferredSize(new Dimension(200,900));
		JTextField thisGenDisplayLabel = new JTextField("Best this generation");
		thisGenDisplayLabel.setEditable(false);
		thisGenDisplay.add(thisGenDisplayLabel);
		
		JPanel recentDisplay = new JPanel();
		recentDisplay.setLayout(new BoxLayout(recentDisplay, BoxLayout.Y_AXIS));
		recentDisplay.setPreferredSize(new Dimension(200,900));
		JTextField recentDisplayLabel = new JTextField("Most recent");
		recentDisplayLabel.setEditable(false);
		recentDisplay.add(recentDisplayLabel);
		
		webQueues.add(allTimeDisplay);
		webQueues.add(lastGenDisplay);
		webQueues.add(thisGenDisplay);
		webQueues.add(recentDisplay);
		this.add(webQueues);
		
		JPanel controls = new JPanel();
		controls.setPreferredSize(new Dimension(300,900));
		
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		WebSimulator webSim = new WebSimulator();
	}

}
