package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import logic.AStar;
import logic.AlgorithmSettings;
import logic.Node;
import logic.NodeStop;
import logic.AStar.RestrictionType;
import utils.FileManager;

public class Menu extends JFrame {
	private static final long serialVersionUID = 7883336728124920447L;

	private JPanel dummyPanel, formPanel, bottomPanel, filePanel, restrictionPanel, gasPanel, timePanel, speedPanel, consumePanel, maxGasPanel;
	private JButton startButton,cancelButton,openFile;
	private JLabel titleLabel,sourceLabel, destinationLabel, fileLabel, gasLabel, timeLabel, speedLabel, consumeLabel, maxGasLabel;
	private JFileChooser fileChooser;
	private JRadioButton noRestrictionButton;
	private JRadioButton[] radioButtons;
	private JSlider[] sliders;
	private JLabel[] restrictionLabels;
	private JSpinner gasSpinner, timeSpinner, speedSpinner, consumeSpinner, maxGasSpinner;
	private final Color menuColor = new Color(58,134,207); 

	private int option, gas,time,maxGas;
	private double speed, consume;
	private File fileSelected;

	private GraphApp gApp;

	public Menu(){
		//int initialGasVale, int initialTimeTravelValue,
		// float averageSpeed, float averageGasConsume, int maxGasDeposit
		super("Main Menu");

		gApp = null;

		createButtons();
		createLabels();
		createRadioButtons();
		createSpinners();

		formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setBackground(menuColor);

		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
		fileChooser.setFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(false);

		filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		filePanel.add(openFile);
		filePanel.add(fileLabel);
		filePanel.setAlignmentX(LEFT_ALIGNMENT);
		filePanel.setBackground(menuColor);

		gasPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		gasPanel.add(gasLabel);
		gasPanel.add(gasSpinner);
		gasPanel.setAlignmentX(LEFT_ALIGNMENT);
		gasPanel.setBackground(menuColor);

		maxGasPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		maxGasPanel.add(maxGasLabel);
		maxGasPanel.add(maxGasSpinner);
		maxGasPanel.setAlignmentX(LEFT_ALIGNMENT);
		maxGasPanel.setBackground(menuColor);

		speedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		speedPanel.add(speedLabel);
		speedPanel.add(speedSpinner);
		speedPanel.setAlignmentX(LEFT_ALIGNMENT);
		speedPanel.setBackground(menuColor);

		consumePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		consumePanel.add(consumeLabel);
		consumePanel.add(consumeSpinner);
		consumePanel.setAlignmentX(LEFT_ALIGNMENT);
		consumePanel.setBackground(menuColor);

		timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		timePanel.add(timeLabel);
		timePanel.add(timeSpinner);
		timePanel.setAlignmentX(LEFT_ALIGNMENT);
		timePanel.setBackground(menuColor);

		restrictionPanel = new JPanel();
		restrictionPanel.setAlignmentX(LEFT_ALIGNMENT);
		restrictionPanel.setLayout(new BoxLayout(restrictionPanel, BoxLayout.Y_AXIS));
		restrictionPanel.setBackground(menuColor);
		sliders = new JSlider[4];
		restrictionLabels = new JLabel[4];
		for (int i = 0; i < radioButtons.length; i++) {
			JPanel restPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			restPanel.setBackground(menuColor);
			sliders[i] = new JSlider(0,100);
			sliders[i].setForeground(menuColor);
			sliders[i].addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged (ChangeEvent e) {
					for (int j = 0; j < sliders.length; j++) {
						if(e.getSource() == sliders[j]){
							restrictionLabels[j].setText(sliders[j].getValue()+"%");
						}
					}
				}
			});
			restrictionLabels[i] = new JLabel(sliders[i].getValue()+"%");
			restrictionLabels[i].setSize(new Dimension(10,10));
			restPanel.add(radioButtons[i]);
			sliders[i].setEnabled(false);
			restrictionLabels[i].setEnabled(false);
			if(i < 2){
				restPanel.add(sliders[i]);
				restPanel.add(restrictionLabels[i]);
			}
			restrictionPanel.add(restPanel);
		}

		formPanel.add(titleLabel);
		formPanel.add(filePanel);
		formPanel.add(gasPanel);
		formPanel.add(maxGasPanel);
		formPanel.add(speedPanel);
		formPanel.add(consumePanel);
		formPanel.add(timePanel);
		formPanel.add(restrictionPanel);
		formPanel.add(new JLabel("                                                                                               "));
		formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		formPanel.add(bottomPanel);

		dummyPanel = new JPanel();
		dummyPanel.setPreferredSize(new Dimension(700,500));
		dummyPanel.setBackground(Color.BLACK);

		setLayout(new GridBagLayout());
		add(formPanel);
		add(dummyPanel);
		getContentPane().setBackground(menuColor);
		pack();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run () {
				if(gApp == null)
					startButton.setEnabled(false);
				else if(gApp.isValid() && slidersValid())
					startButton.setEnabled(true);
				else
					startButton.setEnabled(false);
			}
		}, 0L,200L);
	}

	private void createRadioButtons(){

		radioButtons = new JRadioButton[4];
		radioButtons[0] = new JRadioButton();
		radioButtons[0].setText("Distance");
		radioButtons[0].setBackground(menuColor);
		radioButtons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				if(radioButtons[0].isSelected() && radioButtons[1].isSelected()){
					sliders[0].setEnabled(true);
					restrictionLabels[0].setEnabled(true);
					sliders[1].setEnabled(true);
					restrictionLabels[1].setEnabled(true);
				}else{
					sliders[0].setEnabled(false);
					restrictionLabels[0].setEnabled(false);
					sliders[1].setEnabled(false);
					restrictionLabels[1].setEnabled(false);
				}
			}
		});

		radioButtons[1] = new JRadioButton();
		radioButtons[1].setText("Cost");
		radioButtons[1].setBackground(menuColor);
		radioButtons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				if(radioButtons[0].isSelected() && radioButtons[1].isSelected()){
					sliders[0].setEnabled(true);
					restrictionLabels[0].setEnabled(true);
					sliders[1].setEnabled(true);
					restrictionLabels[1].setEnabled(true);
				}else{
					sliders[0].setEnabled(false);
					restrictionLabels[0].setEnabled(false);
					sliders[1].setEnabled(false);
					restrictionLabels[1].setEnabled(false);
				}
			}
		});

		radioButtons[2] = new JRadioButton();
		radioButtons[2].setText("Refuel");
		radioButtons[2].setBackground(menuColor);
		radioButtons[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				if(radioButtons[2].isSelected()){
					sliders[2].setEnabled(true);
					restrictionLabels[2].setEnabled(true);
				}else{
					sliders[2].setEnabled(false);
					restrictionLabels[2].setEnabled(false);
				}
			}
		});

		radioButtons[3] = new JRadioButton();
		radioButtons[3].setText("Rest");
		radioButtons[3].setBackground(menuColor);
		radioButtons[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				if(radioButtons[3].isSelected()){
					sliders[3].setEnabled(true);
					restrictionLabels[3].setEnabled(true);
				}else{
					sliders[3].setEnabled(false);
					restrictionLabels[3].setEnabled(false);
				}
			}
		});
	}

	private void createButtons(){
		openFile = new JButton(new ImageIcon("graphics/openFile.gif"));
		openFile.setText("Open a File...");
		openFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				int n = fileChooser.showOpenDialog(Menu.this);
				if (n == JFileChooser.APPROVE_OPTION) {
					fileSelected = fileChooser.getSelectedFile();
					fileLabel.setText(fileSelected.getName());
					fileLabel.setForeground(Color.GREEN);
					remove(dummyPanel);

					gApp = new GraphApp(FileManager.readDataSet(fileSelected));
					gApp.createGraph();

					add(gApp.getView());
					pack();
				}
			}
		});

		startButton = new JButton("Start");
		startButton.setForeground(Color.GREEN);
		startButton.setEnabled(false);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				gas = (int)gasSpinner.getValue();
				maxGas = (int)maxGasSpinner.getValue();
				speed = (double)speedSpinner.getValue();
				consume = (double)consumeSpinner.getValue();
				time = (int)timeSpinner.getValue();
				showPath();
			}
		});

		cancelButton = new JButton("Cancel");
		cancelButton.setForeground(Color.RED);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				System.exit(0);
			}
		});

		bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.setBackground(menuColor);
		bottomPanel.add(startButton);
		bottomPanel.add(cancelButton);
		bottomPanel.setAlignmentX(LEFT_ALIGNMENT);
	}

	private void createLabels(){
		titleLabel = new JLabel("A* Algorithm - Path Finding");
		titleLabel.setFont(new Font(titleLabel.getFont().getFontName(), titleLabel.getFont().getStyle(), 20));
		titleLabel.setForeground(Color.WHITE);

		sourceLabel = new JLabel("Source identifier:");

		destinationLabel = new JLabel("Destination identifier:");

		fileLabel = new JLabel("No File Selected");
		fileLabel.setForeground(Color.RED);

		gasLabel = new JLabel("Initial Gas Value:");
		maxGasLabel = new JLabel("Maximum Gas Deposit:");
		speedLabel = new JLabel("Average Speed:");
		consumeLabel = new JLabel("Average Gas Consume:");
		timeLabel = new JLabel("Maximum Travel Time:");
	}

	private void createSpinners(){
		gasSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100000, 1));
		gasSpinner.setPreferredSize(new Dimension(100,25));

		maxGasSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100000, 1));
		maxGasSpinner.setPreferredSize(new Dimension(100,25));

		speedSpinner = new JSpinner(new SpinnerNumberModel(0f, 0f, 100000.000f, 0.010f));
		speedSpinner.setPreferredSize(new Dimension(100,25));

		consumeSpinner = new JSpinner(new SpinnerNumberModel(0f, 0f, 100000.000f, 0.010f));
		consumeSpinner.setPreferredSize(new Dimension(100,25));

		timeSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100000, 1));
		timeSpinner.setPreferredSize(new Dimension(100,25));
	}

	private void showPath(){
		AlgorithmSettings settings = new AlgorithmSettings((int) gasSpinner.getValue(),
				(int) timeSpinner.getValue(), (float) (1f / (double) speedSpinner.getValue()),
				Float.parseFloat(""+consumeSpinner.getValue()), (int) maxGasSpinner.getValue(),
				(int) timeSpinner.getValue(), (float) sliders[0].getValue(), (float) sliders[1].getValue());

		ArrayList<RestrictionType> restrictionList = new ArrayList<>();
		if(radioButtons[0].isSelected())
			restrictionList.add(RestrictionType.DISTANCE);
		if(radioButtons[1].isSelected())
			restrictionList.add(RestrictionType.COST);
		if(radioButtons[2].isSelected())
			restrictionList.add(RestrictionType.REFUEL);
		if(radioButtons[3].isSelected())
			restrictionList.add(RestrictionType.REST);
		if(restrictionList.size() == 0)
			restrictionList.add(RestrictionType.NO_RESTRICTION);

		LinkedList<Node> poi = gApp.getPOI();
		for(int i = 0; i < (poi.size() - 1); i++){
			LinkedList<NodeStop> result = AStar.runAlgorithm(settings, settings.nextGasValue, settings.nextTravelTime,
					poi.get(i), poi.get(i + 1), restrictionList);

			gApp.showPath(result);
		}
	}

	private boolean slidersValid(){
		if(radioButtons[0].isSelected() && radioButtons[1].isSelected()){
			int sum = 0;
			for (int i = 0; i < 2; i++) {
				if(radioButtons[i].isSelected()){
					sum += sliders[i].getValue();
				}
			}
			return sum == 100;
		}else{
			return true;
		}
	}
}