import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.PriorityQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class WebViewer extends JFrame {
	private final static String windowTitle = "Web Viewer";
	private int numAnchors, webLength;
	private Web web;
	public WebViewer() {
		this(new WebDrawer());
	}
	public WebViewer(WebDrawer webDrawer) {
		this(webDrawer, 5, 100);
	}
	public WebViewer(final WebDrawer webDrawer, int numberAnchors, int lengthOfWeb) {
		super(windowTitle);
		this.web = webDrawer.getWeb();
		this.numAnchors = numberAnchors;
		this.webLength = lengthOfWeb;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(600, 400);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		webDrawer.setPreferredSize(new Dimension(400,400));
		int fit = webDrawer.buildWeb(numAnchors, webLength);
		this.setResizable(false);
		this.add(webDrawer);
		
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		
		JTextArea geneInfo = new JTextArea("Algorithms/Genes:\n\n"+webDrawer.getWeb().toString());
		geneInfo.setEditable(false);
		controlPanel.add(geneInfo);
		
		final JTextArea fitnessInfo = new JTextArea("Fitness: "+fit+"");
		fitnessInfo.setEditable(false);
		controlPanel.add(fitnessInfo);
		
		JPanel anchorInfo = new JPanel();
		//anchorInfo.setLayout(new BoxLayout(anchorInfo, BoxLayout.X_AXIS));
		final JTextArea numAnchorsDisplay = new JTextArea("Number of anchors: "+numAnchors);
		numAnchorsDisplay.setEditable(false);
		numAnchorsDisplay.setSize(numAnchorsDisplay.getPreferredSize());
		JButton plusAnchor = new JButton("+");
		final JButton minusAnchor = new JButton("-");
		plusAnchor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				numAnchors++;
				numAnchorsDisplay.setText("Number of anchors: "+numAnchors);
				minusAnchor.setEnabled(true);
				
				int fitness = webDrawer.buildWeb(numAnchors, webLength);
				webDrawer.paintComponent(webDrawer.getGraphics());
				fitnessInfo.setText("Fitness: "+fitness+"");
				
			}
		});
		minusAnchor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numAnchors--;
				numAnchorsDisplay.setText("Number of anchors: "+numAnchors);
				if(numAnchors==3) {
					minusAnchor.setEnabled(false);
				}
				
				int fitness = webDrawer.buildWeb(numAnchors,  webLength);
				webDrawer.paintComponent(webDrawer.getGraphics());
				fitnessInfo.setText("Fitness: "+fitness+"");
				
			}
		});
		
		anchorInfo.add(numAnchorsDisplay);
		anchorInfo.add(plusAnchor);
		anchorInfo.add(minusAnchor);
		
		controlPanel.add(anchorInfo);
		
		JPanel webLengthInfo = new JPanel();
		//webLengthInfo.setLayout(new BoxLayout(webLengthInfo, BoxLayout.X_AXIS));
		final JTextArea webLengthDisplay = new JTextArea("Length of web (in radii): "+webLength);
		webLengthDisplay.setEditable(false);
		webLengthDisplay.setSize(numAnchorsDisplay.getPreferredSize());
		JButton plusWebLength = new JButton("+");
		final JButton minusWebLength = new JButton("-");
		plusWebLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				webLength+=5;
				webLengthDisplay.setText("Length of web (in radii): "+webLength);
				minusWebLength.setEnabled(true);
				int fitness = webDrawer.buildWeb(numAnchors, webLength);
				webDrawer.paintComponent(webDrawer.getGraphics());
				fitnessInfo.setText("Fitness: "+fitness+"");
			}
		});
		minusWebLength.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				webLength-=5;
				webLengthDisplay.setText("Length of web (in radii): "+webLength);
				if(webLength<=5) {
					minusWebLength.setEnabled(false);
				}
				int fitness = webDrawer.buildWeb(numAnchors,  webLength);
				webDrawer.paintComponent(webDrawer.getGraphics());
				fitnessInfo.setText("Fitness: "+fitness+"");
			}
		});
		
		webLengthInfo.add(webLengthDisplay);
		webLengthInfo.add(plusWebLength);
		webLengthInfo.add(minusWebLength);
		
		controlPanel.add(webLengthInfo);
		
		JButton save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					save();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		controlPanel.add(save);
		
		JButton load = new JButton("Load");
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFileChooser jfc = new JFileChooser(new File("saves"));
					FileNameExtensionFilter filter = new FileNameExtensionFilter("Webs", "web");
					jfc.setFileFilter(filter);
					if(jfc.showDialog(load, "Choose") == JFileChooser.APPROVE_OPTION) {
						String fileName = jfc.getSelectedFile().getPath();
						load(fileName);
					}
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			
		});
		controlPanel.add(load);
		
		this.add(controlPanel);
		
		this.pack();
		this.setVisible(true);
	}
	/**
	 * Saves a file with this web in it with the prompted name with '.web' appended.
	 * @throws IOException
	 */
	public void save() throws IOException {
		File saveDir = new File("saves");
		if(!saveDir.exists()) {
			saveDir.mkdir();
		}
		String fileName = (String) JOptionPane.showInputDialog(
				this,
				"Type a name for your web", 
				"Save",
				JOptionPane.QUESTION_MESSAGE);
		if(fileName!=null) {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("saves\\"+fileName+".web")));
			oos.writeObject(web);
			oos.writeObject(numAnchors);
			oos.writeObject(webLength);
			
			}
	}
	
	/**
	 * Updates this webViewer to display the selected web.
	 * @param filePath the complete filepath to the data.
	 */
	public void load(String filePath) throws IOException, ClassNotFoundException, FileNotFoundException {

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filePath)));
		this.web = (Web) ois.readObject();
		this.numAnchors = (int) ois.readObject();
		this.webLength = (int) ois.readObject();
		new WebViewer(new WebDrawer(web), numAnchors, webLength);
		this.dispose();
	}
	
	public static void main(String[] args) {
		WebViewer webViewer = new WebViewer(new WebDrawer());
	}

}
