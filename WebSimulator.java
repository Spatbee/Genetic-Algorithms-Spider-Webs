import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WebSimulator extends JFrame{
	private PriorityQueue<CompleteSim> lastGeneration, thisGeneration, bestAllTimePQ;
	private static final String windowTitle = "Web Simulator";
	private WebDrawer[] allTime, lastGen, thisGen, recent;
	private final int previewSize = 180;
	private boolean firstGen = true;
	private int webLength = 85;
	private WebDrawer currentWeb = new WebDrawer();
	private ArrayList<CompleteSim> recentSims = new ArrayList<CompleteSim>();
	private FitnessComparator c = new FitnessComparator();
	private int generationNumber = 0;
	private transient JTextField generationNumberText, webAmountText, minimumAnchorText, maximumAnchorText;
	private volatile boolean continueGenerating = false;
	private JButton save, load;
	/**
	 * Creates a WebSimulator.
	 */
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
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		//controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
		controls.setPreferredSize(new Dimension(300,previewSize*4+50));
		controls.setMaximumSize(new Dimension(300,previewSize*4+50));
		final JButton stepOneGeneration = new JButton("Step one generation");
		stepOneGeneration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				runOneGeneration();
			}
		});
		JPanel wrapper1 = new JPanel();
		wrapper1.add(stepOneGeneration);
		controls.add(wrapper1);
		
		final JButton startStop = new JButton("Start");
		startStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				continueGenerating = !continueGenerating;
				stepOneGeneration.setEnabled(!continueGenerating);
				if(continueGenerating) {
					startStop.setText("Stop");
				}
				else {
					startStop.setText("Start");
				}
				Thread t = new Thread(new Runnable() {
					public void run() {
						playGenerations();
					}
				});
				t.start();
			}
		});
		JPanel wrapper4 = new JPanel();
		wrapper4.add(startStop);
		controls.add(wrapper4);
		
		JLabel generationLabel = new JLabel("Generation number: ");
		generationNumberText = new JTextField("0");
		generationNumberText.setPreferredSize(new Dimension(45,20));
		generationNumberText.setHorizontalAlignment(JTextField.RIGHT);
		generationNumberText.setEditable(false);
		//generationNumberText.setPreferredSize(new Dimension(200,30));
//		generationNumberLabel.setMaximumSize(new Dimension(300,50));
		JPanel wrapper2 = new JPanel();
		wrapper2.add(generationLabel);
		wrapper2.add(generationNumberText);
		controls.add(wrapper2);
		
		JPanel wrapper5 = new JPanel();
		JLabel webAmountLabel = new JLabel("Web amount: ");
		webAmountText = new JTextField(webLength+"");
		webAmountText.setPreferredSize(new Dimension(60,20));
		webAmountText.setHorizontalAlignment(JTextField.RIGHT);
		wrapper5.add(webAmountLabel);
		wrapper5.add(webAmountText);
		wrapper5.setPreferredSize(new Dimension(300,30));
		controls.add(wrapper5);
		
		JPanel wrapper6 = new JPanel();
		JLabel minimumAnchorLabel = new JLabel("Minimum number of anchors: ");
		minimumAnchorText = new JTextField("4");
		minimumAnchorText.setPreferredSize(new Dimension(60,20));
		minimumAnchorText.setHorizontalAlignment(JTextField.RIGHT);
		wrapper6.add(minimumAnchorLabel);
		wrapper6.add(minimumAnchorText);
		wrapper6.setPreferredSize(new Dimension(300,30));
		controls.add(wrapper6);
		
		JPanel wrapper7 = new JPanel();
		JLabel maximumAnchorLabel = new JLabel("Maximum number of anchors: ");
		maximumAnchorText = new JTextField("8");
		maximumAnchorText.setPreferredSize(new Dimension(60,20));
		maximumAnchorText.setHorizontalAlignment(JTextField.RIGHT);
		wrapper7.add(maximumAnchorLabel);
		wrapper7.add(maximumAnchorText);
		wrapper7.setPreferredSize(new Dimension(300,30));
		controls.add(wrapper7);
		
		currentWeb.setPreferredSize(new Dimension(300,300));
//		currentWeb.setMaximumSize(new Dimension(600,300));
		JPanel wrapper3 = new JPanel();
		wrapper3.add(currentWeb);
		controls.add(wrapper3);
		
		save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					save();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		JPanel wrapper8 = new JPanel();
		wrapper8.add(save);
		wrapper8.setPreferredSize(new Dimension(300,30));
		controls.add(wrapper8);
		
		load = new JButton("Load");
		
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFileChooser jfc = new JFileChooser(new File("saves"));
					if(jfc.showDialog(load, "Choose") == JFileChooser.APPROVE_OPTION) {
						String fileName = jfc.getSelectedFile().getPath();
						load(fileName);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		JPanel wrapper9 = new JPanel();
		wrapper9.add(load);
		wrapper9.setPreferredSize(new Dimension(300,30));
		controls.add(wrapper9);
		

		this.add(controls);
		
		this.pack();
		//this.revalidate();
		this.setVisible(true);
	}
	/*
	 * Updates the target WebDrawer to display a CompleteSim.
	 * @param target The WebDrawer to be updated.
	 * @param cs The CompleteSim to be put in the WebDrawer.
	 */
	private void updateWebDrawer(WebDrawer target, final CompleteSim cs) {
		final int parsedWebLength = Integer.parseInt(webAmountText.getText());
		target.newWeb(cs.web);
		target.buildWeb(cs.bestAnchor, parsedWebLength);
		target.paintComponent(target.getGraphics());
		for(MouseListener ml : target.getMouseListeners()) {
			target.removeMouseListener(ml);
		}
		target.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {
				WebViewer wv = new WebViewer(new WebDrawer(cs.web), cs.bestAnchor, parsedWebLength);
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
	}
	/*
	 * Runs a simulation on a web. 
	 * @param web The web to be tested.
	 * @return A CompleteSim with the total fitness and the best number of anchors recorded.
	 */
	private CompleteSim runSim(Web web) {
		int bestFitness = 0;
		int bestAnchor = 0;
		int totalFitness = 0;
		int parsedWebLength = Integer.parseInt(webAmountText.getText());
		int min = Integer.parseInt(minimumAnchorText.getText());
		int max = Integer.parseInt(maximumAnchorText.getText());
		for(int i = min;i<=max;i++) {
			int fitness = web.getFitness(i, parsedWebLength);
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
	/*
	 * Runs multiple gnerations of webs.
	 */
	private void playGenerations() {
		while(continueGenerating) {
			runOneGeneration();
		}
	}
	/*
	 *Runs a single generation of webs. 
	 */
	private void runOneGeneration() {
		setTextFieldsEditable(false);
		setButtonsEnabled(false);
		validateTextFields();
		if(firstGen) {
			firstGen = false;
			for(int i = 0; i<38; i++) {
				CompleteSim cs = runSim(new Web());
				thisGeneration.add(cs);
				populateRecent(cs);
				
			}
			populateBestAllTime();
			populateBestThisGen();
			
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
					
				}
			}
			populateBestAllTime();
			populateBestThisGen();
			
		}
		generationNumber++;
		generationNumberText.setText(""+generationNumber);
		setTextFieldsEditable(true);
		setButtonsEnabled(true);
	}
	/*
	 *Populates/updates the "best this generation" column. 
	 */
	private void populateBestThisGen() {
		if(!thisGeneration.isEmpty()) {
			ArrayList<CompleteSim> temp = new ArrayList<CompleteSim>();
			for(int i = 0; i < 4; i++) {
				CompleteSim cs = thisGeneration.remove().copy();
				updateWebDrawer(thisGen[i],cs);
				temp.add(cs);
			}
			for(int i = 0; i < 4; i++) thisGeneration.add(temp.remove(0));
		}
	}
	/*
	 *Populates/updates the "best last generation" column. 
	 */
	private void populateBestLastGen() {
		if(!lastGeneration.isEmpty()) {
			ArrayList<CompleteSim> temp = new ArrayList<CompleteSim>();
			for(int i = 0; i < 4; i++) {
				CompleteSim cs = lastGeneration.remove().copy();
				updateWebDrawer(lastGen[i],cs);
				temp.add(cs);
			}
			for(int i = 0; i < 4; i++) lastGeneration.add(temp.remove(0));
		}
	}
	/*
	 *Populates/updates the "best all time" column. 
	 */
	private void populateBestAllTime() {
		if(!thisGeneration.isEmpty()) {
			bestAllTimePQ.addAll(thisGeneration);
			ArrayList<CompleteSim> temp = new ArrayList<CompleteSim>();
			int size = bestAllTimePQ.size();
			for(int i = 0; i<size; i++) {
				CompleteSim best = bestAllTimePQ.remove().copy();
				if(i<4)updateWebDrawer(allTime[i],best);
				temp.add(best);
			}
			for(int i = 0; i<4; i++) {
				if(temp.size()>0)bestAllTimePQ.add(temp.remove(0));
			}
		}
	}
	/*
	 *Populates/updates the "most recent" column.
	 *@param cs The most recent CompleteSim. 
	 */
	private void populateRecent(CompleteSim cs) {
		recentSims.add(0,cs);
		for(int i = 0; i<4; i++) {
			if(recentSims.size()>i) {
				updateWebDrawer(recent[i],recentSims.get(i).copy());
			}
		}
		if(recentSims.size()>4) {
			recentSims.remove(4);
		}
	}

	
	/*
	 * Makes sure all the text fields are parseable ints within expected operating range.
	 */
	private void validateTextFields() {
		try {
			int x = Integer.parseInt(webAmountText.getText());
			if(x<5) {
				webAmountText.setText("5");
			}
			else if(x>1000) {
				webAmountText.setText("1000");
			}
		}
		catch(Exception e) {
			webAmountText.setText(webLength+"");
		}
		try {
			int x = Integer.parseInt(minimumAnchorText.getText());
			if(x<3) {
				minimumAnchorText.setText("3");
			}
			else if(x>100) {
				minimumAnchorText.setText("100");
			}
		}
		catch(Exception e) {
			minimumAnchorText.setText(4+"");
		}
		try {
			int x = Integer.parseInt(maximumAnchorText.getText());
			if(x<3||x<Integer.parseInt(minimumAnchorText.getText())) {
				maximumAnchorText.setText(minimumAnchorText.getText());
			}
			else if(x>100) {
				maximumAnchorText.setText("100");
			}
		}
		catch(Exception e) {
			maximumAnchorText.setText(""+Math.max(8, Integer.parseInt(maximumAnchorText.getText())));
		}
	}
	
	/*
	 * Makes the relevant text fields either editable or not.
	 */
	private void setTextFieldsEditable(boolean editable) {
		webAmountText.setEditable(editable);
		minimumAnchorText.setEditable(editable);
		maximumAnchorText.setEditable(editable);
	}
	/*
	 * Makes the relevant buttons enabled or not
	 */
	private void setButtonsEnabled(boolean enabled) {
		save.setEnabled(enabled);
		load.setEnabled(enabled);
	}
	
	/**
	 * Fills this WebSimulator with the data from the given file path.
	 * @param filePath The complete file path to the data.
	 */
	public void load(String filePath) throws IOException, ClassNotFoundException, FileNotFoundException {
		//System.out.println(filePath);
		//System.out.println(new File(filePath).exists());
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(filePath)));
		lastGeneration = (PriorityQueue<CompleteSim>) ois.readObject();
		thisGeneration = (PriorityQueue<CompleteSim>) ois.readObject();
		bestAllTimePQ = (PriorityQueue<CompleteSim>) ois.readObject();
		/*
		for(int i = 0; i<lastGenerationTemp.length; i++) {
			lastGeneration.add(lastGenerationTemp[i]);
		}
		for(int i = 0; i<thisGenerationTemp.length; i++) {
			thisGeneration.add(thisGenerationTemp[i]);
		}
		for(int i = 0; i<bestAllTimePQTemp.length; i++) {
			bestAllTimePQ.add(bestAllTimePQTemp[i]);
		}*/
		generationNumber = (int)ois.readObject();
		generationNumberText.setText(""+generationNumber);
		webAmountText.setText((String)ois.readObject());
		minimumAnchorText.setText((String)ois.readObject());
		maximumAnchorText.setText((String)ois.readObject());
		firstGen=(boolean)(ois.readObject());
		populateBestAllTime();
		populateBestThisGen();
		populateBestLastGen();
	}
	
	/**
	 * Puts the data from this simulation to the given file path.
	 */
	public void save() throws IOException{
		File saveDir = new File("saves");
		if(!saveDir.exists()) {
			saveDir.mkdir();
		}
		String fileName = (String) JOptionPane.showInputDialog(
				this,
				"Type a name for your simulation", 
				"Save",
				JOptionPane.QUESTION_MESSAGE);
		if(fileName!=null) {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("saves\\"+fileName+".sim")));
			//System.out.println(lastGeneration);
			/*
			CompleteSim[] lastGenerationTemp = new CompleteSim[lastGeneration.size()];
			for(int i = 0; i< lastGenerationTemp.length; i++) {
				lastGenerationTemp[i] = lastGeneration.remove();
			}
			for(int i = 0; i<lastGenerationTemp.length; i++) {
				lastGeneration.add(lastGenerationTemp[i]);
			}
			CompleteSim[] thisGenerationTemp = new CompleteSim[thisGeneration.size()];
			for(int i = 0; i< thisGenerationTemp.length; i++) {
				thisGenerationTemp[i] = thisGeneration.remove();
			}
			for(int i = 0; i<lastGenerationTemp.length; i++) {
				thisGeneration.add(lastGenerationTemp[i]);
			}
			CompleteSim[] bestAllTimePQTemp = new CompleteSim[bestAllTimePQ.size()];
			for(int i = 0; i< bestAllTimePQTemp.length; i++) {
				bestAllTimePQTemp[i] = bestAllTimePQ.remove();
			}
			for(int i = 0; i<bestAllTimePQTemp.length; i++) {
				bestAllTimePQ.add(bestAllTimePQTemp[i]);
			}*/
			oos.writeObject(lastGeneration);
			oos.writeObject(thisGeneration);
			oos.writeObject(bestAllTimePQ);
			oos.writeObject(generationNumber);
			oos.writeObject(webAmountText.getText());
			oos.writeObject(minimumAnchorText.getText());
			oos.writeObject(maximumAnchorText.getText());
			oos.writeObject(firstGen);
			}
	}
		
	
	public static void main(String[] args) {
		WebSimulator webSim = new WebSimulator();
	}
	
}
