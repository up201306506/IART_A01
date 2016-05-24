package main;

import java.util.ArrayList;

import gui.GraphApp;
import logic.Node;
import utils.FileManager;

public class Application {
	
	private static String dataFilePath = "data.txt";

	public static void main (String[] args) {
		
		// IMPORTANT !!!!!!
		// do not use this class yet
		// use tests
		
		// read data
		ArrayList<Node> nodeList = FileManager.readDataSet(dataFilePath);
		
		GraphApp gApp = new GraphApp(nodeList);
		gApp.createGraph();
		gApp.display();
		
		// write data
		FileManager.writeDataSet(dataFilePath, nodeList);
	}
}