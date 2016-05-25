package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Menu extends JFrame{
	
	public static enum RestrictionType {
		NO_RESTRICTION, COST, DURATION, DURATION_COST
	}
	
	private JPanel formPanel, bottomPanel, filePanel, restrictionPanel, moneyPanel;
	private JButton startButton,cancelButton,openFile;
	private JLabel titleLabel,sourceLabel,destinationLabel, fileLabel, moneyLabel;
	private JTextField sourceField, destinationField;
	private JFileChooser fileChooser;
	private JRadioButton[] radioButtons;
	private ButtonGroup restrictionRadioGroup;
	private JSpinner moneySpinner;
	
	private String source,destination;
	private int option, money;
	private File fileSelected;
	
	public Menu(){
		super("Main Menu");
		
		createButtons();
		createLabels();
		createTextFields();
		createRadioButtons();
		createSpinners();
		
		formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		
		fileChooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt");
		fileChooser.setFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		filePanel.add(openFile);
		filePanel.add(fileLabel);
		filePanel.setAlignmentX(LEFT_ALIGNMENT);
		
		moneyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		moneyPanel.add(moneyLabel);
		moneyPanel.add(moneySpinner);
		moneyPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		restrictionPanel = new JPanel();
		restrictionPanel.setLayout(new BoxLayout(restrictionPanel, BoxLayout.Y_AXIS));
		restrictionPanel.add(radioButtons[0]);
		restrictionPanel.add(radioButtons[1]);
		restrictionPanel.add(radioButtons[2]);
		restrictionPanel.add(radioButtons[3]);
		restrictionPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createTitledBorder("Choose an option"),
			BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		restrictionPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		
		formPanel.add(titleLabel);
		formPanel.add(filePanel);
		formPanel.add(sourceLabel);
		formPanel.add(sourceField);
		formPanel.add(destinationLabel);
		formPanel.add(destinationField);
		formPanel.add(moneyPanel);
		formPanel.add(restrictionPanel);
		formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		formPanel.add(bottomPanel);
		
		add(formPanel);
		pack();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void createRadioButtons(){
		radioButtons = new JRadioButton[4];
		restrictionRadioGroup = new ButtonGroup();
		radioButtons[0] = new JRadioButton();
		radioButtons[0].setText("Shortest path without restrictions");
		restrictionRadioGroup.add(radioButtons[0]);
		radioButtons[1] = new JRadioButton();
		radioButtons[1].setText("Shortest and cheaper path");
		restrictionRadioGroup.add(radioButtons[1]);
		radioButtons[2] = new JRadioButton();
		radioButtons[2].setText("Shortest and quicker path");
		restrictionRadioGroup.add(radioButtons[2]);
		radioButtons[3] = new JRadioButton();
		radioButtons[3].setText("Shortest and cheaper and quicker path");
		restrictionRadioGroup.add(radioButtons[3]);
		
		radioButtons[0].setSelected(true);
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
				}
			}
		});
		
		startButton = new JButton("Start");
		startButton.setForeground(Color.GREEN);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				source = sourceField.getText();
				destination = destinationField.getText();
				money = (int)moneySpinner.getValue();
				System.out.println(money);
				for (int i = 0; i < radioButtons.length; i++) {
					if(radioButtons[i].isSelected()){
						option = i;
						break;
					}
				}
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
		bottomPanel.add(startButton);
		bottomPanel.add(cancelButton);
		bottomPanel.setAlignmentX(LEFT_ALIGNMENT);
	}
	
	private void createLabels(){
		titleLabel = new JLabel("A* Algorithm - Path Finding");
		titleLabel.setFont(new Font(titleLabel.getFont().getFontName(), titleLabel.getFont().getStyle(), 20));
		
		sourceLabel = new JLabel("Source identifier:");
		
		destinationLabel = new JLabel("Destination identifier:");
		
		fileLabel = new JLabel("No File Selected");
		fileLabel.setForeground(Color.RED);
		
		moneyLabel = new JLabel("Starting Money:");
	}
	
	private void createSpinners(){
		moneySpinner = new JSpinner();
		moneySpinner.setPreferredSize(new Dimension(100,25));
	}
	
	private void createTextFields(){
		sourceField = new JTextField();
		destinationField = new JTextField();
	}	
}