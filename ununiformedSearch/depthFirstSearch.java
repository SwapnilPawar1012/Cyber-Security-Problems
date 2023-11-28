package ununiformedSearch;

import java.util.*;

public class depthFirstSearch {

  public static void dfs(Map<Integer, List<Integer>> graph, int startNode) {
    Set<Integer> visited = new HashSet<>();

    dfsRecursive(graph, startNode, visited);
  }

  public static void dfsRecursive(
    Map<Integer, List<Integer>> graph,
    int currentNode,
    Set<Integer> visited
  ) {
    visited.add(currentNode);
    System.out.print(currentNode + " ");

    List<Integer> neighbors = graph.get(currentNode);

    if (neighbors != null) {
      for (int neighbor : neighbors) {
        if (!visited.contains(neighbor)) {
          dfsRecursive(graph, neighbor, visited);
        }
      }
    }
  }

  public static void main(String[] args) {
    Map<Integer, List<Integer>> graph = new HashMap<>();

    graph.put(1, Arrays.asList(2, 3, 4));
    graph.put(2, Arrays.asList(5, 6));
    graph.put(3, Arrays.asList(7, 8));
    graph.put(4, Arrays.asList(9, 10));
    graph.put(5, Arrays.asList(11, 12));
    graph.put(7, Arrays.asList(13));
    graph.put(9, Arrays.asList(14));
    graph.put(12, Arrays.asList(15));

    int startNode = 1;
    System.out.println("DFS traversal starting from " + startNode + ": ");
    dfs(graph, startNode);
  }
}
