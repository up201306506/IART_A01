package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import logic.Edge;
import logic.Node;

public class FileManager {

	// Reads the graph data
	public static ArrayList<Node> readDataSet(File fileSelected){

		ArrayList<Node> nodeList = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(fileSelected))) {			
			String line;
			while (!(line = br.readLine()).equals("EDGES")) { // reads line by line
				String[] arr = line.split(" ", -1); // splits line read into multiple strings, divided by tabs
				Node n = new Node(arr[0], Float.parseFloat(arr[1]), Float.parseFloat(arr[2]), Boolean.parseBoolean(arr[3]), Boolean.parseBoolean(arr[4]));
				nodeList.add(n); // creates the graph nodes
			}
			while ((line = br.readLine()) != null) { // reads line by line
				String[] arr = line.split(" ", -1); // splits line read into multiple strings, divided by tabs
				Node nodeA = null, nodeB = null;
				for(Node node : nodeList){
					if(node.getName().equals(arr[0])) nodeA = node;
					if(node.getName().equals(arr[1])) nodeB = node;
					if(nodeA != null && nodeB != null) break;
				}
				Edge.associateNodes(nodeA, nodeB, Float.parseFloat(arr[2]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return nodeList;
	}
}