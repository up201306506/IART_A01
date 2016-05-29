package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import logic.Node;

public class FileManager {

	// Reads the graph data
	public static ArrayList<Node> readDataSet(File fileSelected){

		ArrayList<Node> nodeList = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(fileSelected))) {			
			String line;
			while ((line = br.readLine()) != null) { // reads line by line
				String[] arr = line.split("\t", -1); // splits line read into multiple strings, divided by tabs
				nodeList.add(new Node(arr[0], Float.parseFloat(arr[1]), Float.parseFloat(arr[2]), false, false)); // creates the graph nodes
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return nodeList;
	}
}