package logic;

public class NodeScore implements Comparable<NodeScore> {

	private Node node;
	private int score;

	public NodeScore(Node node, int score){
		this.node = node;
		this.score = score;
	}

	public Node getNode(){
		return node;
	}

	public int getScore(){
		return score;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;

		if(!(obj instanceof NodeScore)) return false;

		NodeScore that = (NodeScore) obj;
		if(this.node.equals(that.node)) return true;

		return false;
	}

	@Override
	public int compareTo(NodeScore other) {
		return Integer.valueOf(this.score).compareTo(other.score);
	}
}