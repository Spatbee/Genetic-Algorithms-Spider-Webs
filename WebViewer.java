import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BoxLayout;
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
		
		this.add(controlPanel);
		
		this.pack();
		this.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		WebViewer webViewer = new WebViewer(new WebDrawer());
	}

}
