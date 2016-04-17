package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import org.graphstream.graph.*;

public class AStar {
	Graph graph;
	String startingPos, endingPos;
	PriorityQueue<Pair> openList;
	ArrayList<Pair> closeList;
	
	public AStar(Graph g){
		graph = g;
		openList = new PriorityQueue<>();
		closeList = new ArrayList<>();
	}
	
	/* Not yet complete */
	public void pathFind(String sp, String ep){
		startingPos = sp;
		endingPos = ep;
		openList.add(new Pair(startingPos,0));
		graph.getNode(startingPos).setAttribute("gScore", 0);
		graph.getNode(startingPos).setAttribute("fScore", 12345);//heuristic incomplete
		Pair current;
		
		while(!openList.isEmpty()){
			current = openList.poll();
			if(current.equals(endingPos)){
				/* return path */
			}else {
				closeList.add(current);
				for (Edge e : graph.getNode(current.id).getEachEdge()) {
					String id = e.getOpposite(graph.getNode(current.id)).getId();
					if(!closeList.contains(new Pair(id,0))){
						/* Calculate heuristic value */
						int value = (int)graph.getNode(current.id).getAttribute("gScore") + 12345; //heuristic incomplete
						if(!openList.contains(new Pair(id,0))){
							openList.add(new Pair(id,value));
						}else if(value < (int)graph.getNode(id).getAttribute("gScore")){
							/* Best path until now */
							graph.getNode(id).setAttribute("comeFrom", current.id);
							graph.getNode(id).setAttribute("gScore", value);
							graph.getNode(id).setAttribute("fScore", 12345); //heuristic incomplete
						}
					}
				}
			}
		}
		/* return failure */
	}
	
	public class Pair implements Comparable<Pair>{
		public String id;
		public int value;
		
		public Pair(String s,int v){
			id = s;
			value = v;
		}

		@Override
		public int compareTo (Pair o) {
			if(value < o.value){
				return -1;
			}else if(value > o.value){
				return 1;
			}
			return 0;
		}
		
		public boolean equals(Pair o){
			if(id == o.id)
				return true;
			else
				return false;
		}
	}
}
