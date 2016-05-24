package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import logic.Node;

public class FileManager {

	// Reads the graph data
	public static ArrayList<Node> readDataSet(String filePath){

		ArrayList<Node> nodeList = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {			
			String line;
			while ((line = br.readLine()) != null) { // reads line by line
				String[] arr = line.split("\t", -1); // splits line read into multiple strings, divided by tabs
				nodeList.add(new Node(arr[0], Float.parseFloat(arr[1]), Float.parseFloat(arr[2]), false, false, false)); // creates the graph nodes
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return nodeList;
	}

	public static void writeDataSet(String filePath, ArrayList<Node> nodeList){

		try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)))){
			if(nodeList.size() <= 0) return;

			if(nodeList.size() != 1)
				for(int i = 0; i < nodeList.size() - 1; i++) bw.write(nodeList.get(i).toString() + "\n");

			bw.write(nodeList.get(nodeList.size() - 1).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}