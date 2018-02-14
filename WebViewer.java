import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WebViewer extends JFrame {
	private final static String windowTitle = "Web Viewer";
	private int numAnchors = 5;
	private int webLength = 15;
	
	public WebViewer(WebDrawer webDrawer) {
		super(windowTitle);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 400);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		webDrawer.setPreferredSize(new Dimension(400,400));
		webDrawer.buildWeb(numAnchors, webLength);
		this.add(webDrawer);
		
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		
		JTextArea geneInfo = new JTextArea("Algorithms/Genes:\n\n"+webDrawer.getWeb().toString());
		geneInfo.setEditable(false);
		controlPanel.add(geneInfo);
		
		JPanel anchorInfo = new JPanel();
		//anchorInfo.setLayout(new BoxLayout(anchorInfo, BoxLayout.X_AXIS));
		JTextArea numAnchorsDisplay = new JTextArea("Number of anchors: "+numAnchors);
		numAnchorsDisplay.setEditable(false);
		numAnchorsDisplay.setSize(numAnchorsDisplay.getPreferredSize());
		JButton plusAnchor = new JButton("+");
		JButton minusAnchor = new JButton("-");
		plusAnchor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				numAnchors++;
				numAnchorsDisplay.setText("Number of anchors: "+numAnchors);
				minusAnchor.setEnabled(true);
				webDrawer.buildWeb(numAnchors, webLength);
				webDrawer.paintComponent(webDrawer.getGraphics());
			}
		});
		minusAnchor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numAnchors--;
				numAnchorsDisplay.setText("Number of anchors: "+numAnchors);
				if(numAnchors==3) {
					minusAnchor.setEnabled(false);
				}
				webDrawer.buildWeb(numAnchors,  webLength);
				webDrawer.paintComponent(webDrawer.getGraphics());
			}
		});
		
		anchorInfo.add(numAnchorsDisplay);
		anchorInfo.add(plusAnchor);
		anchorInfo.add(minusAnchor);
		
		controlPanel.add(anchorInfo);
		
		JPanel webLengthInfo = new JPanel();
		//webLengthInfo.setLayout(new BoxLayout(webLengthInfo, BoxLayout.X_AXIS));
		JTextArea webLengthDisplay = new JTextArea("Length of web (in radii): "+webLength);
		webLengthDisplay.setEditable(false);
		webLengthDisplay.setSize(numAnchorsDisplay.getPreferredSize());
		JButton plusWebLength = new JButton("+");
		JButton minusWebLength = new JButton("-");
		plusWebLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				webLength+=5;
				webLengthDisplay.setText("Length of web (in radii): "+webLength);
				minusWebLength.setEnabled(true);
				webDrawer.buildWeb(numAnchors, webLength);
				webDrawer.paintComponent(webDrawer.getGraphics());
			}
		});
		minusWebLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				webLength-=5;
				webLengthDisplay.setText("Length of web (in radii): "+webLength);
				if(webLength<=5) {
					minusWebLength.setEnabled(false);
				}
				webDrawer.buildWeb(numAnchors,  webLength);
				webDrawer.paintComponent(webDrawer.getGraphics());
			}
		});
		
		webLengthInfo.add(webLengthDisplay);
		webLengthInfo.add(plusWebLength);
		webLengthInfo.add(minusWebLength);
		
		controlPanel.add(webLengthInfo);
		
		
		
		this.add(controlPanel);
		
		this.pack();
		this.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		WebViewer webViewer = new WebViewer(new WebDrawer());
	}

}
