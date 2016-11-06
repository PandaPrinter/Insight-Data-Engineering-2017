import java.util.*;

public class Graph {
	private Map<String, Set<String>> graph = new HashMap<>();

	public void addEdge(String id1, String id2) {
		if (!graph.containsKey(id1)) {
			graph.put(id1, new HashSet<>());
		}
		graph.get(id1).add(id2);
		if (!graph.containsKey(id2)) {
			graph.put(id2, new HashSet<>());
		}
		graph.get(id2).add(id1);
	}

	public boolean isInGraph(String id1, String id2) {
		return graph.containsKey(id1) && graph.containsKey(id2);
	}

	/**
	 * Feature one implementation
	 */
	public boolean checkFirstDegree(String id1, String id2) {
		return graph.get(id1).contains(id2);
	}

	/**
	 * Feature two implementation
	 */
	public boolean checkSecondDegree(String id1, String id2) {
		Set<String> friendSet1 = graph.get(id1);
		Set<String> friendSet2 = graph.get(id2);
		return checkIntersection(friendSet1, friendSet2);
	}

	/**
	 * Feature three implementation
	 * 
	 * Bidirectional BFS
	 */
	public boolean checkFourthDegree(String id1, String id2) {
		Set<String> friendSet1 = new HashSet<>(graph.get(id1));
		Set<String> friendSet2 = new HashSet<>(graph.get(id2));
		for (String friend : graph.get(id1)) {
			if (graph.containsKey(friend)) {
				friendSet1.addAll(graph.get(friend));
			}
		}
		for (String friend : graph.get(id2)) {
			if (graph.containsKey(friend)) {
				friendSet2.addAll(graph.get(friend));
			}
		}
		return checkIntersection(friendSet1, friendSet2);
	}
	
	/**
	 * Exit the loop once a match is found
	 */
	public boolean checkIntersection(Set<String> set1, Set<String> set2) {
		if (set1.size() > set2.size())
			return checkIntersection(set2, set1);
		for (String id : set1) {
			if (set2.contains(id)) {
				return true;
			}
		}
		return false;
	}
}
