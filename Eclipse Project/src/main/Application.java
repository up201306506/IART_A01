package main;

import gui.Menu;

public class Application {
	
	private static String dataFilePath = "data.txt";

	public static void main (String[] args) {
		
		// IMPORTANT !!!!!!
		// do not use this class yet
		// use tests
		
		// read data
		/*ArrayList<Node> nodeList = FileManager.readDataSet(dataFilePath);
		
		GraphApp gApp = new GraphApp(nodeList);
		gApp.createGraph();
		gApp.display();
		*/
		
		Menu menu = new Menu();
		
		
		// write data
		//FileManager.writeDataSet(dataFilePath, nodeList);
	}
}