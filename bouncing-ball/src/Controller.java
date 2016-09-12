
/**
 * This is the kaleidoscope homework by Ao Sun, making
 * use of the Model-View-Controller design pattern and the Timer and
 * Observer/Observable classes.
 */
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

/**
 * The Controller sets up the GUI and handles all the controls (buttons, menu
 * items, etc.)
 * 
 * @author David Matuszek, modified by swapneel
 */
public class Controller extends JFrame {

	// private JPanel buttonPanel;
	
	private JPanel controlPanel;
	private JPanel subArguPanelOne;
	private JPanel subArguPanelTwo;
	private JPanel opPanel;
	private JPanel arguPanel;
	private Timer timer;

	private JButton runButton = new JButton("Start(Restart)");;
	private JButton stopButton = new JButton("Stop");
	JButton[] opButtons;
	JComboBox<String> colorBox;
	JComboBox<String> speedBox;
	JComboBox<String> randomBox;
	JComboBox<String> sizeBox;
	JLabel[] descriptLabel;
	Color currentColor = Color.RED;
	String currentColorCommand = "Random";
	String currentSpeed = "Slow";
	boolean currentRandSetting = false;
	int currentSpeedX = 5;
	int currentSpeedY = 5;
	int currentSizeFactor = 1;
	int currentRefNum = 7;
	int currentModelNumber = 0;
	JSpinner figNumSpinner;
	JSpinner refNumSpinner;
	Model[] testModel = new Model[20];

	public Controller() {
		setTitle("Kaleidoscope by Ao Sun");
	}

	/** The View object displays what is happening in the Model. */
	private View view;

	
	/**
	 * Runs the kaleidoscope program.
	 * @param args Ignored.
	 */
	public static void main(String[] args) {
		Controller myKaleidoscope = new Controller();
		myKaleidoscope.init();
		myKaleidoscope.display();

	}

	/**
	 * Sets up communication between the components.
	 * 
	 */
	private void init() {
		opButtons = new JButton[9];
		opPanel = new JPanel();
		arguPanel = new JPanel();
		controlPanel = new JPanel();
		subArguPanelOne = new JPanel();
		subArguPanelTwo = new JPanel();
		colorBox = new JComboBox<String>(
				new String[] { "Random", "Blue", "Red", "Yellow", "Green", "Cyan", "Orange", "Pink" });
		speedBox = new JComboBox<String>(new String[] { "Slow", "Fast" });
		sizeBox = new JComboBox<String>(new String[] { "Small", "Large" });
		randomBox = new JComboBox<String>(new String[] { "Common", "Random" });
		int initValue = 0;
		int minS = 0;
		int maxS = 20;
		int stepS = 1;
		SpinnerModel spinModel = new SpinnerNumberModel(initValue, minS, maxS, stepS);
		figNumSpinner = new JSpinner(spinModel);
		SpinnerModel refSpinModel = new SpinnerNumberModel(7, 0, 7, 1);
		refNumSpinner = new JSpinner(refSpinModel);
		// set Buttons
		opButtons[0] = runButton;
		opButtons[3] = stopButton;
		opButtons[6] = new JButton("Clear(Default)");
		opButtons[1] = new JButton("Slow");
		opButtons[4] = new JButton("Add(Setting)");
		opButtons[7] = new JButton("Delete");
		opButtons[2] = new JButton("RandomColor");
		opButtons[5] = new JButton("Fast");
		opButtons[8] = new JButton("ChangeMove");
		descriptLabel = new JLabel[6];
		descriptLabel[0] = new JLabel("NextItemColor");
		descriptLabel[1] = new JLabel("NextItemSpeed");
		descriptLabel[2] = new JLabel("TotalNumber");
		descriptLabel[5] = new JLabel("Reflection");
		descriptLabel[4] = new JLabel("RandomMove");
		descriptLabel[3] = new JLabel("NextItemSize");

		view = new View(testModel);
		view.setReflection(1);
		view.setReflection(2);
		view.setReflection(3);
		view.setReflection(4);
		view.setReflection(5);
		view.setReflection(6);
		view.setReflection(7);
	}

	/**
	 * add observers for each model in the testModel array
	 */
	public void addObserverforModel() {
		for (int i = 0; i < testModel.length; i++) {
			if (testModel[i] != null)
				testModel[i].addObserver(view);
		}
	}

	/**
	 * set default kaleidoscope operation
	 */
	public void setDefault() {
		figNumSpinner.setValue(3);
		currentColorCommand = "Random";
		currentSpeedX = 5;
		currentSpeedY = 5;
		currentSizeFactor = 1;
		currentRefNum = 7;
		currentModelNumber = 3;
		currentSpeed = "Slow";
		currentRandSetting = false;

		view.setReflection(1);
		view.setReflection(2);
		view.setReflection(3);
		view.setReflection(4);
		view.setReflection(5);
		view.setReflection(6);
		view.setReflection(7);

		testModel[2] = new OvalModel();
		testModel[1] = new RectModel();
		testModel[0] = new PolygonModel();
		for (int i = 3; i < testModel.length; i++) {
			testModel[i] = null;
		}

		((OvalModel) testModel[2]).initOval(20, 20, 100, 100, view.getWidth(), view.getHeight(), 5, 5, Color.RED);
		((RectModel) testModel[1]).initRect(20, 20, 0, 0, view.getWidth(), view.getHeight(), 7, 3, Color.BLUE);
		// ((OvalModel) testModel[2]).initOval(10, 10, 45, 37, view.getWidth(),
		// view.getHeight(), 5, 5, Color.ORANGE);
		((PolygonModel) testModel[0]).initPolygon(new int[] { 20, 0, 40 }, new int[] { 0, 20, 20 }, 3, view.getWidth(),
				view.getHeight(), 5, 5, Color.CYAN);

	}

	/**
	 * set random color
	 * @return a Color type random color
	 */
	public Color randColor() {
		Color randColor;
		Random rand = new Random();
		int randInt = rand.nextInt(7);
		switch (randInt) {
		case 0:
			randColor = Color.BLUE;
			break;
		case 1:
			randColor = Color.RED;
			break;
		case 2:
			randColor = Color.YELLOW;
			break;
		case 3:
			randColor = Color.GREEN;
			break;
		case 4:
			randColor = Color.CYAN;
			break;
		case 5:
			randColor = Color.ORANGE;
			break;
		case 6:
			randColor = Color.PINK;
			break;
		default:
			randColor = Color.RED;
		}
		return randColor;
	}

	/**
	 * set random speed
	 * @param str
	 */
	public void randSpeed(String str) {
		int tempSpeedX, tempSpeedY;
		Random rand = new Random();
		if (str.equals("Slow")) {
			tempSpeedX = rand.nextInt(5) + 1;
			if (tempSpeedX > 2)
				tempSpeedY = rand.nextInt(5) + 1;
			else
				tempSpeedY = rand.nextInt(3) + 3;
		} else {
			tempSpeedX = rand.nextInt(5) + 4;
			if (tempSpeedX > 6)
				tempSpeedY = rand.nextInt(5) + 4;
			else
				tempSpeedY = rand.nextInt(3) + 6;
		}
		currentSpeedX = tempSpeedX;
		currentSpeedY = tempSpeedY;
	}

	/**
	 * Displays the GUI.
	 */
	public void display() {
		arrangeLayout();
		attachListeners();
		setSize(800, 800);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * arrange the layout for the GUI
	 */
	private void arrangeLayout() {
		int rowNum = 3;
		opPanel.add(opButtons[0]);
		opPanel.add(opButtons[2]);
		opPanel.add(opButtons[8]);
		opPanel.add(opButtons[3]);
		opPanel.add(opButtons[5]);
		opPanel.add(opButtons[4]);
		opPanel.add(opButtons[6]);
		opPanel.add(opButtons[1]);
		opPanel.add(opButtons[7]);

		stopRunning();

		opPanel.setLayout(new GridLayout(rowNum, 3, 1, 1));
		arguPanel.setLayout(new GridLayout(1, 2, 6, 6));
		subArguPanelOne.setLayout(new GridLayout(rowNum, 2, 1, 1));
		subArguPanelTwo.setLayout(new GridLayout(rowNum, 2, 1, 1));

		subArguPanelOne.add(descriptLabel[0]);
		subArguPanelOne.add(colorBox);
		subArguPanelOne.add(descriptLabel[1]);
		subArguPanelOne.add(speedBox);
		subArguPanelOne.add(descriptLabel[2]);
		subArguPanelOne.add(figNumSpinner);
		subArguPanelTwo.add(descriptLabel[3]);
		subArguPanelTwo.add(sizeBox);
		subArguPanelTwo.add(descriptLabel[4]);
		subArguPanelTwo.add(randomBox);
		subArguPanelTwo.add(descriptLabel[5]);
		subArguPanelTwo.add(refNumSpinner);
		arguPanel.add(subArguPanelOne);
		arguPanel.add(subArguPanelTwo);

		controlPanel.setLayout(new GridLayout(1, 2, 6, 6));
		controlPanel.add(opPanel);
		controlPanel.add(arguPanel);
		setLayout(new BorderLayout());
		add(controlPanel, BorderLayout.SOUTH);
		add(view, BorderLayout.CENTER);
	}

	/**
	 * enable the runbutton, and disable the stop button
	 */
	private void stopRunning()// set enabled buttons
	{
		runButton.setEnabled(true);
		stopButton.setEnabled(false);
	}

	/**
	 * add listeners for event
	 */
	private void attachListeners() {
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				runButton.setEnabled(false);
				stopButton.setEnabled(true);
				colorBox.setEnabled(true);
				speedBox.setEnabled(true);
				figNumSpinner.setEnabled(true);
				sizeBox.setEnabled(true);
				randomBox.setEnabled(true);
				refNumSpinner.setEnabled(true);
				opButtons[6].setEnabled(true);
				if (testModel[0] == null) {
					setDefault();
				}
				addObserverforModel();
				for (int i = 0; i < testModel.length; i++) {
					if (testModel[i] != null)
						testModel[i].start();
				}

			}
		});
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				stopRunning();
				for (int i = 0; i < testModel.length; i++) {
					if (testModel[i] != null)
						testModel[i].stop();
				}
			}
		});
		opButtons[4].addActionListener(new ActionListener()// Add Button
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				int currentIndex = currentModelNumber;
				if (currentModelNumber == 20)
					return;
				if (addNewModel(currentIndex, new Random().nextInt(6) + 1)) {
					if (stopButton.isEnabled())
						testModel[currentIndex].start();
					currentModelNumber += 1;
					figNumSpinner.setValue(currentModelNumber);
					System.out.println(currentModelNumber);
				}
			}
		});
		opButtons[1].addActionListener(new ActionListener()// slow button
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				for (int i = 0; i < testModel.length; i++) {
					if (testModel[i] != null)
						testModel[i].slowSpeed();
				}
			}
		});
		opButtons[7].addActionListener(new ActionListener()// Delete Button
		{
			@Override
			public void actionPerformed(ActionEvent event) {

				System.out.println(currentModelNumber);

				if (currentModelNumber == 0)
					return;

				currentModelNumber = currentModelNumber - 1;
				testModel[currentModelNumber] = null;
				figNumSpinner.setValue(currentModelNumber);


				if (currentModelNumber == 0)
					stopRunning();
				

			}
		});
		opButtons[6].addActionListener(new ActionListener()// Clear button
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				if (testModel[0] == null) {
					setDefault();
				}
				addObserverforModel();
				for (int i = 0; i < testModel.length; i++) {
					if (testModel[i] != null)
						testModel[i].start();
				}
				for (int i = 0; i < testModel.length; i++) {
					testModel[i] = null;
				}
				runButton.setEnabled(true);
				stopButton.setEnabled(false);
				colorBox.setSelectedItem("Random");
				speedBox.setSelectedItem("Slow");
				sizeBox.setSelectedItem("Small");
				randomBox.setSelectedItem("Common");
				refNumSpinner.setValue(7);
				figNumSpinner.setValue(0);
				currentColorCommand = "Random";
				currentSpeedX = 5;
				currentSpeedY = 4;
				currentSizeFactor = 1;
				currentRefNum = 7;
				currentModelNumber = 0;
				currentSpeed = "Slow";
				currentRandSetting = false;
			}
		});
		opButtons[2].addActionListener(new ActionListener()// Auto Color button
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				for (int i = 0; i < testModel.length; i++) {
					if (testModel[i] != null) {
						testModel[i].changeColor();
					}

				}
			}
		});
		opButtons[5].addActionListener(new ActionListener()// fast button
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				for (int i = 0; i < testModel.length; i++) {
					if (testModel[i] != null) {
						testModel[i].fastSpeed();
					}
				}
			}
		});
		opButtons[8].addActionListener(new ActionListener()// Auto Move button
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				for (int i = 0; i < testModel.length; i++) {
					if (testModel[i] != null) {
						testModel[i].changeSpeed();
					}
				}
			}
		});
		colorBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String inputStr = (String) colorBox.getSelectedItem();
				// System.out.println(inputStr);
				currentColorCommand = inputStr;
				switch (inputStr) {
				case "Red":
					currentColor = Color.RED;
					break;
				case "Orange":
					currentColor = Color.ORANGE;
					break;
				case "Yellow":
					currentColor = Color.YELLOW;
					break;
				case "Green":
					currentColor = Color.GREEN;
					break;
				case "Cyan":
					currentColor = Color.CYAN;
					break;
				case "Blue":
					currentColor = Color.BLUE;
					break;
				case "Pink":
					currentColor = Color.PINK;
					break;
				default:
					currentColor = randColor();
				}
			}
		});
		speedBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String inputStr = (String) speedBox.getSelectedItem();
				// System.out.println(inputStr);
				currentSpeed = inputStr;
				randSpeed(inputStr);
			}
		});
		sizeBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String inputStr = (String) sizeBox.getSelectedItem();
				// System.out.println(inputStr);
				if (inputStr.equals("Large")) {
					currentSizeFactor = 2;
				} else {
					currentSizeFactor = 1;
				}
			}
		});
		randomBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				String inputStr = (String) randomBox.getSelectedItem();
				// System.out.println(inputStr);
				if (inputStr.equals("Random")) {
					currentRandSetting = true;
					for (int i = 0; i < testModel.length; i++) {
						if (testModel[i] != null) {
							testModel[i].setRandomMove(true);
						}
					}
				}
				if (inputStr.equals("Common")) {
					currentRandSetting = false;
					for (int i = 0; i < testModel.length; i++) {
						if (testModel[i] != null) {
							testModel[i].setRandomMove(false);
						}
					}
				}
			}
		});
		refNumSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				int tempInt = (Integer) (refNumSpinner.getValue());
				currentRefNum = tempInt;
				setReflections();
			}
		});
		figNumSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				int tempInt = (Integer) (figNumSpinner.getValue());
				int orgNum = currentModelNumber;
				int trueNum = currentModelNumber;
				currentModelNumber = tempInt;
				if (currentModelNumber > orgNum) {
					for (int currentIndex = orgNum; currentIndex < currentModelNumber; currentIndex++) {
						if (addNewModel(currentIndex, new Random().nextInt(6) + 1)) {
							if (stopButton.isEnabled())
								testModel[currentIndex].start();
							trueNum++;
							// System.out.println(currentIndex);
						}
					}
				} else if (currentModelNumber < orgNum) {
					for (int currentIndex = orgNum - 1; currentIndex >= currentModelNumber; currentIndex--) {
						testModel[currentIndex] = null;
						trueNum--;
						if (currentIndex == 0)
							stopRunning();
						// System.out.println(currentIndex);
					}
				}
				figNumSpinner.setValue(trueNum);
			}
		});

		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				for (int i = 0; i < testModel.length; i++) {
					if (testModel[i] != null)
						testModel[i].setLimits(view.getWidth(), view.getHeight());
				}
			}
		});
	}
	
	/**
	 * set the reflection type for each model image
	 */
	public void setReflections() {
		for (int i = 0; i <= currentRefNum; i++) {
			view.setReflection(i);
		}
		for (int i = currentRefNum + 1; i < 8; i++) {
			view.cancelReflection(i);
		}
	}

	/**
	 * add a new model in the kaleidoscope
	 * @param index
	 * @param modelType
	 * @return true if the model is been added sucessfully, 
	 * false otherwise
	 */
	public boolean addNewModel(int index, int modelType) {
		if (index < testModel.length) {
			if (testModel[index] == null && modelType < 7 && modelType > 0)// can add new models
																		
			{
				if (currentColorCommand.equals("Random")) {
					currentColor = randColor();
				}
				randSpeed(currentSpeed);
				if (modelType == 1)// Circle
				{
					testModel[index] = new OvalModel();
					((OvalModel) testModel[index]).initOval(20 * currentSizeFactor, 20 * currentSizeFactor, 0, 0,
							view.getWidth(), view.getHeight(), currentSpeedX, currentSpeedY, currentColor);
					testModel[index].setRandomMove(currentRandSetting);
					// System.out.println(currentRandSetting);
				} else if (modelType == 2) // Oval
				{
					testModel[index] = new OvalModel();
					((OvalModel) testModel[index]).initOval(20 * currentSizeFactor, 10 * currentSizeFactor, 0, 0,
							view.getWidth(), view.getHeight(), currentSpeedX, currentSpeedY, currentColor);
					testModel[index].setRandomMove(currentRandSetting);
					// System.out.println(currentRandSetting);
				} else if (modelType == 3)// Triangle
				{
					testModel[index] = new PolygonModel();
					((PolygonModel) testModel[index]).initPolygon(
							PolygonModel.multiply(new int[] { 20, 0, 40 }, currentSizeFactor),
							(PolygonModel.multiply(new int[] { 0, 17, 17 }, currentSizeFactor)), 3, view.getWidth(),
							view.getHeight(), currentSpeedX, currentSpeedY, currentColor);
					testModel[index].setRandomMove(currentRandSetting);
					// System.out.println(currentRandSetting);
				} else if (modelType == 4)// Square
				{
					testModel[index] = new RectModel();
					((RectModel) testModel[index]).initRect(20 * currentSizeFactor, 20 * currentSizeFactor, 0, 0,
							view.getWidth(), view.getHeight(), currentSpeedX, currentSpeedY, currentColor);
					testModel[index].setRandomMove(currentRandSetting);
					// System.out.println(currentRandSetting);
				} else if (modelType == 5)// Rectangle
				{
					testModel[index] = new RectModel();
					((RectModel) testModel[index]).initRect(20 * currentSizeFactor, 10 * currentSizeFactor, 0, 0,
							view.getWidth(), view.getHeight(), currentSpeedX, currentSpeedY, currentColor);
					testModel[index].setRandomMove(currentRandSetting);
					// System.out.println(currentRandSetting);
				} else if (modelType == 6)// diamond
				{
					testModel[index] = new PolygonModel();
					((PolygonModel) testModel[index]).initPolygon(
							PolygonModel.multiply(new int[] { 0, 20, 30, 10, 20 }, currentSizeFactor),
							(PolygonModel.multiply(new int[] { 0, 0, 17, 17, 10 }, currentSizeFactor)), 5, view.getWidth(),
							view.getHeight(), currentSpeedX, currentSpeedY, currentColor);
					testModel[index].setRandomMove(currentRandSetting);
					// System.out.println(currentRandSetting);
				} else if (modelType == 7)// hexagon
				{
					// TODO:
				} 
				return true;
			}
			return false;
		} else {
			currentModelNumber = testModel.length - 1;
			return false;
		}
	}

}