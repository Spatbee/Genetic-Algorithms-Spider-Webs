import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainMenu extends JFrame{
	public MainMenu() {
		super("Genetic Algorithms: Spider Webs by Daniel Burgess");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setMinimumSize(new Dimension(500,1));
		this.setResizable(false);
		
		//Create a new simulation window, keeping this open
		JButton newSimulation = new JButton("Web Simulator (Genetic Algorithm)");
		newSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WebSimulator newWebSim = new WebSimulator();
			}
		});
		JPanel newSimulationPanel = new JPanel();
		newSimulationPanel.add(newSimulation);
		this.add(newSimulationPanel);
		
		
		//Create a new web viewing window, keeping this open
		JButton newViewer = new JButton("Web Viewer");
		newViewer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WebViewer newWebView = new WebViewer();
			}
		});
		JPanel newViewerPanel = new JPanel();
		newViewerPanel.add(newViewer);
		this.add(newViewerPanel);
		
		//Create a new web viewing window, keeping this open
		JButton newSA = new JButton("Simulated Annealing");
		newSA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WebSimulatedAnnealing wSA = new WebSimulatedAnnealing();
			}
		});
		JPanel newSAPanel = new JPanel();
		newSAPanel.add(newSA);
		this.add(newSAPanel);
		
		this.pack();
		this.setVisible(true);
	}
	public static void main(String[] args) {
		MainMenu mainMenu = new MainMenu();
	}

}
