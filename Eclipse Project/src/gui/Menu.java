package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


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
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Menu extends JFrame{
	public static enum RestrictionType {
		NO_RESTRICTION, COST, DURATION, DURATION_COST
	}
	private JPanel formPanel, bottomPanel, filePanel, restrictionPanel, moneyPanel, depositPanel, hourPanel;
	private JButton startButton,cancelButton,openFile;
	private JLabel titleLabel,sourceLabel,destinationLabel, fileLabel, moneyLabel, errorLabel, depositLabel, hourLabel;
	private JComboBox<String> sourceComboBox, destinationComboBox;
	private JFileChooser fileChooser;
	private JRadioButton[] radioButtons;
	private JSpinner moneySpinner,depositSpinner,hourSpinner;
	
	private String source,destination;
	private int option, money, depositSize, hours;
	private File fileSelected;
	
	public Menu(){
		/*
		 * Limite de dinheiro
		 * Tamanho Depósito
		 * Tempo que guia com cada descanso
		 */
		super("Main Menu");
		
		
		createButtons();
		createLabels();
		createComboBoxes();
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
		
		depositPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		depositPanel.add(depositLabel);
		depositPanel.add(depositSpinner);
		depositPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		hourPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		hourPanel.add(hourLabel);
		hourPanel.add(hourSpinner);
		hourPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		restrictionPanel = new JPanel();
		restrictionPanel.setLayout(new BoxLayout(restrictionPanel, BoxLayout.Y_AXIS));
		for (int i = 0; i < radioButtons.length; i++) {
			restrictionPanel.add(radioButtons[i]);
		}
		restrictionPanel.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createTitledBorder("Choose an option"),
			BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		formPanel.add(titleLabel);
		formPanel.add(filePanel);
		formPanel.add(sourceLabel);
		formPanel.add(sourceComboBox);
		formPanel.add(destinationLabel);
		formPanel.add(destinationComboBox);
		formPanel.add(moneyPanel);
		formPanel.add(depositPanel);
		formPanel.add(hourPanel);
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
		radioButtons = new JRadioButton[5];
		radioButtons[0] = new JRadioButton();
		radioButtons[0].setText("Shortest path                                   ");
		radioButtons[0].setHorizontalAlignment(SwingConstants.CENTER);
		
		radioButtons[1] = new JRadioButton();
		radioButtons[1].setText("Cheapest path");
		radioButtons[1].setHorizontalAlignment(SwingConstants.CENTER);
		
		radioButtons[2] = new JRadioButton();
		radioButtons[2].setText("Quickest path");
		radioButtons[2].setHorizontalAlignment(SwingConstants.CENTER);
		
		radioButtons[3] = new JRadioButton();
		radioButtons[3].setText("Refuel");
		radioButtons[3].setHorizontalAlignment(SwingConstants.CENTER);
		
		radioButtons[4] = new JRadioButton();
		radioButtons[4].setText("Rest");
		radioButtons[4].setHorizontalAlignment(SwingConstants.CENTER);
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
				money = (int)moneySpinner.getValue();
				depositSize = (int)moneySpinner.getValue();
				hours = (int)moneySpinner.getValue();
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
		depositLabel = new JLabel("Deposit limit:");
		hourLabel = new JLabel("Hours between rest:");
	}
	
	private void createSpinners(){
		moneySpinner = new JSpinner();
		moneySpinner.setPreferredSize(new Dimension(100,25));
		
		depositSpinner = new JSpinner();
		depositSpinner.setPreferredSize(new Dimension(100,25));
		
		hourSpinner = new JSpinner();
		hourSpinner.setPreferredSize(new Dimension(100,25));
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
