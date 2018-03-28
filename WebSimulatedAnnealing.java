import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WebSimulatedAnnealing extends JFrame{
	private int minAnchors = 3;
	private int maxAnchors = 8;
	private int webLength = 85;
	private JTextArea temperature;
	private WebDrawer webDrawer;
	public WebSimulatedAnnealing() {
		super("Simulated Annealing");
		this.setLayout(new BoxLayout(getContentPane(),BoxLayout.X_AXIS));
		this.setResizable(false);
		
		webDrawer = new WebDrawer();
		webDrawer.setPreferredSize(new Dimension(400,400));
		this.add(webDrawer);
		
		JPanel controls = new JPanel();
		controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
		controls.setPreferredSize(new Dimension(150,400));
		
		JButton go = new JButton("Go");
		go.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				go.setEnabled(false);
				Web web = new Web();
				int maxFitness = 800*(maxAnchors-minAnchors+1);
				int maxTemp = 300;
				for(int t = maxTemp; t>=0; t--) {
					boolean choosePrime = false;
					Web webPrime = web.mutate();
					CompleteSim webCS = CompleteSim.getSim(web, webLength, minAnchors, maxAnchors);
					CompleteSim webPrimeCS = CompleteSim.getSim(webPrime, webLength, minAnchors, maxAnchors);
					int deltaFitness = webPrimeCS.totalFitness-webCS.totalFitness;
					if(deltaFitness > 0) choosePrime = true;
					else {
						double p = ((maxFitness+0.0+deltaFitness)/maxFitness)*(0.0+t/maxTemp);
						if(Math.random()<p)choosePrime = true;
					}
					if(choosePrime) {
						web = webPrime;
						webDrawer.newWeb(webPrime);
					}
					else {
						webDrawer.newWeb(web);
					}
					webDrawer.buildWeb(webPrimeCS.bestAnchor, (double)webLength);
					for(MouseListener ml : webDrawer.getMouseListeners()) {
						webDrawer.removeMouseListener(ml);
					}
					final Web webCopy = web.copy();
					webDrawer.addMouseListener(new MouseListener() {
						public void mouseClicked(MouseEvent arg0) {
							WebViewer wv = new WebViewer(new WebDrawer(webCopy), 3, webLength);
						}
						public void mouseEntered(MouseEvent arg0) {//do nothing
						}
						public void mouseExited(MouseEvent arg0) {//do nothing
						}
						public void mousePressed(MouseEvent arg0) {//do nothing
						}
						public void mouseReleased(MouseEvent arg0) {//do nothing
						}
					});
					temperature.setText("Temperature: "+t);
				}
				go.setEnabled(true);
			}
		});
		controls.add(go);
		
		temperature = new JTextArea("Temperature: ");
		temperature.setEditable(false);
		controls.add(temperature);
		
		this.add(controls);
		this.pack();
		this.setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		WebSimulatedAnnealing webSimulatedAnnealing = new WebSimulatedAnnealing();
	}
}
