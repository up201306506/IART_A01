package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Menu extends JFrame{
	private JPanel formPanel, bottomPanel, filePanel;
	private JButton startButton,cancelButton,openFile;
	private JLabel titleLabel,sourceLabel,destinationLabel, fileLabel;
	private JTextField sourceField, destinationField;
	private JFileChooser fileChooser;
	
	private String source,destination;
	private File fileSelected;
	
	public Menu(){
		super("Menu");
		
		createButtons();
		createLabels();
		createTextFields();
		
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
		
		formPanel.add(titleLabel);
		formPanel.add(filePanel);
		formPanel.add(sourceLabel);
		formPanel.add(sourceField);
		formPanel.add(destinationLabel);
		formPanel.add(destinationField);
		formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		formPanel.add(bottomPanel);
		
		add(formPanel);
		pack();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
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
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed (ActionEvent e) {
				source = sourceField.getText();
				destination = destinationField.getText();
				//TODO: Open graph viewer
			}
		});
		
		cancelButton = new JButton("cancel");
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
		titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		sourceLabel = new JLabel("Source identifier");
		sourceLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		destinationLabel = new JLabel("Destination identifier");
		destinationLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		fileLabel = new JLabel("No File Selected");
		fileLabel.setForeground(Color.RED);
	}
	
	private void createTextFields(){
		sourceField = new JTextField();
		sourceField.setAlignmentX(LEFT_ALIGNMENT);
		destinationField = new JTextField();
		destinationField.setAlignmentX(LEFT_ALIGNMENT);
	}
	
}
