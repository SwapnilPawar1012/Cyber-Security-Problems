package ununiformedSearch;

import java.util.*;

public class breadthFirstSearch {

  public static void bfs(Map<Integer, List<Integer>> graph, int startNode) {
    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>();

    visited.add(startNode);
    queue.add(startNode);

    while (!queue.isEmpty()) {
      int currentNode = queue.poll();
      System.out.print(currentNode + " ");

      List<Integer> neighbors = graph.get(currentNode);

      if (neighbors != null) {
        for (int neighbor : neighbors) {
          if (!visited.contains(neighbor)) {
            visited.add(neighbor);
            queue.add(neighbor);
          }
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

    System.out.println("BFS traversal starting from node " + startNode + ": ");
    bfs(graph, startNode);
  }
}
