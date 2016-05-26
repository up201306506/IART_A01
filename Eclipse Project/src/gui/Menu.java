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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.graphstream.ui.swingViewer.DefaultView;
import org.graphstream.ui.swingViewer.ViewPanel;

import sun.java2d.pipe.AAShapePipe;
import logic.Node;

public class Menu extends JFrame{
	
	public static enum RestrictionType {
		NO_RESTRICTION, COST, DURATION, DURATION_COST
	}
	
	private JPanel dummyPanel, formPanel, bottomPanel, filePanel, restrictionPanel, gasPanel, timePanel, speedPanel, consumePanel, maxGasPanel;
	private JButton startButton,cancelButton,openFile;
	private JLabel titleLabel,sourceLabel,destinationLabel, fileLabel, errorLabel, gasLabel, timeLabel, speedLabel, consumeLabel, maxGasLabel;
	private JComboBox<String> sourceComboBox, destinationComboBox;
	private JFileChooser fileChooser;
	private JRadioButton noRestrictionButton;
	private JRadioButton[] radioButtons;
	private JSpinner gasSpinner, timeSpinner, speedSpinner, consumeSpinner, maxGasSpinner;
	private final Color menuColor = new Color(58,134,207); 
	
	private String source,destination;
	private int option, gas,time,speed,consume,maxGas;
	private File fileSelected;
	
	private GraphApp gApp;
	
	public Menu(){
		//int initialGasVale, int initialTimeTravelValue,
		// float averageSpeed, float averageGasConsume, int maxGasDeposit
		super("Main Menu");
		
		ArrayList<Node> list = new ArrayList<Node>();
		list.add(new Node("A", 1, 10, false, false));
		list.add(new Node("B", 3, 2, false, false));
		list.add(new Node("C", 4,-5, false, false));
		list.add(new Node("D", -3,7, false, false));
		list.add(new Node("E", 1,0, false, false));
		list.add(new Node("F", 9,0, false, false));
		list.add(new Node("G", -10,10, false, false));
		list.add(new Node("H", -10,-10, false, false));
		gApp = new GraphApp(list);
		gApp.createGraph();
		
		createButtons();
		createLabels();
		createComboBoxes();
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
		restrictionPanel.setLayout(new BoxLayout(restrictionPanel, BoxLayout.Y_AXIS));
		restrictionPanel.setBackground(menuColor);
		for (int i = 0; i < radioButtons.length; i++) {
			restrictionPanel.add(radioButtons[i]);
		}
		restrictionPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createTitledBorder("Choose restrictions"),
			BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		formPanel.add(titleLabel);
		formPanel.add(filePanel);
		formPanel.add(gasPanel);
		formPanel.add(maxGasPanel);
		formPanel.add(speedPanel);
		formPanel.add(consumePanel);
		formPanel.add(timePanel);
		formPanel.add(noRestrictionButton);
		formPanel.add(restrictionPanel);
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
				if(gApp.isValid()){
					startButton.setEnabled(true);
					System.out.println("is valid");
				}else{
					System.out.println("is not valid");
				}
			}
		}, 0L,200L);
		
	}
	
	private void createRadioButtons(){
		noRestrictionButton = new JRadioButton();
		noRestrictionButton.setText("No restrictions");
		noRestrictionButton.setSelected(true);
		noRestrictionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				for (int i = 0; i < radioButtons.length; i++) {
					if(noRestrictionButton.isSelected()){
						radioButtons[i].setEnabled(false);
					}else{
						radioButtons[i].setEnabled(true);
					}
				}
			}
		});
		
		radioButtons = new JRadioButton[4];
		radioButtons[0] = new JRadioButton();
		radioButtons[0].setText("Distance                                      ");
		radioButtons[0].setEnabled(false);
		
		radioButtons[1] = new JRadioButton();
		radioButtons[1].setText("Cost");
		radioButtons[1].setEnabled(false);
		
		radioButtons[2] = new JRadioButton();
		radioButtons[2].setText("Refuel");
		radioButtons[2].setEnabled(false);
		
		radioButtons[3] = new JRadioButton();
		radioButtons[3].setText("Rest");
		radioButtons[3].setEnabled(false);
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
	            sourceComboBox.setEnabled(true);
	            destinationComboBox.setEnabled(true);
	            remove(dummyPanel);
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
				source = (String)sourceComboBox.getSelectedItem();
				destination = (String)destinationComboBox.getSelectedItem();
				gas = (int)gasSpinner.getValue();
				maxGas = (int)maxGasSpinner.getValue();
				speed = (int)speedSpinner.getValue();
				consume = (int)consumeSpinner.getValue();
				time = (int)timeSpinner.getValue();
				//TODO: Open graph viewer
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
		
		   gasLabel = new JLabel("Initial Gas Value:          ");
		maxGasLabel = new JLabel("Maximum Gas Deposit:");
		 speedLabel = new JLabel("Average Speed:             ");
	  consumeLabel = new JLabel("Average Gas Consume: ");
		  timeLabel = new JLabel("Maximum Travel Time: ");
	}
	
	private void createSpinners(){
		gasSpinner = new JSpinner();
		gasSpinner.setPreferredSize(new Dimension(100,25));
		
		maxGasSpinner = new JSpinner();
		maxGasSpinner.setPreferredSize(new Dimension(100,25));
		
		speedSpinner = new JSpinner();
		speedSpinner.setPreferredSize(new Dimension(100,25));
		
		consumeSpinner = new JSpinner();
		consumeSpinner.setPreferredSize(new Dimension(100,25));
		
		timeSpinner = new JSpinner();
		timeSpinner.setPreferredSize(new Dimension(100,25));
	}
	
	private void createComboBoxes(){
		sourceComboBox = new JComboBox<String>();
		sourceComboBox.setAlignmentX(LEFT_ALIGNMENT);
		sourceComboBox.setEnabled(false);
		sourceComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				System.out.println(sourceComboBox.getSelectedItem());
			}
		});
		
		
		destinationComboBox = new JComboBox<String>();
		destinationComboBox.setAlignmentX(LEFT_ALIGNMENT);
		destinationComboBox.setEnabled(false);
		destinationComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				System.out.println(destinationComboBox.getSelectedItem());
			}
		});
	}
	
}
