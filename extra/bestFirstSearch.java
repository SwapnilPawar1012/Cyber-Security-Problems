package extra;

import java.util.*;

public class bestFirstSearch {

  public static class Node {

    public int id;
    public List<Edge> neighbors;
    public Node parent;
    public double heuristic;

    Node(int id, double heuristic) {
      this.id = id;
      this.neighbors = new ArrayList<>();
      this.parent = null;
      this.heuristic = heuristic;
    }

    public void addNeighbor(Node node, int weight) {
      neighbors.add(new Edge(node, weight));
    }
  }

  public static class Edge {

    public Node node;
    public int weight;

    Edge(Node node, int weight) {
      this.node = node;
      this.weight = weight;
    }
  }

  public static Node bfsSearch(Node startNode, Node target) {
    PriorityQueue<Node> openList = new PriorityQueue<>(
      Comparator.comparingDouble(n -> n.heuristic)
    );
    Set<Node> visited = new HashSet<>();

    openList.add(startNode);

    while (!openList.isEmpty()) {
      Node currentNode = openList.poll();

      if (currentNode == target) {
        return currentNode;
      }

      visited.add(currentNode);

      for (Edge edge : currentNode.neighbors) {
        Node neighbor = edge.node;

        if (!visited.contains(neighbor) && !openList.contains(neighbor)) {
          neighbor.parent = currentNode;
          openList.add(neighbor);
        }
      }
    }
    return null;
  }

  public static void printPath(Node target) {
    List<Integer> path = new ArrayList<>();
    Node currentNode = target;

    while (currentNode != null) {
      path.add(currentNode.id);
      currentNode = currentNode.parent;
    }
    Collections.reverse(path);

    System.out.println("Shortest path Using Best-First Search is: ");
    for (int nodeId : path) {
      System.out.print(nodeId + " ");
    }
    System.out.println();
  }

  public static void main(String[] args) {
    Node node1 = new Node(1, 14);
    Node node2 = new Node(2, 12);
    Node node3 = new Node(3, 11);
    Node node4 = new Node(4, 6);
    Node node5 = new Node(5, 4);
    Node node6 = new Node(6, 11);
    Node node7 = new Node(7, 0);

    node1.addNeighbor(node2, 4);
    node1.addNeighbor(node3, 3);
    node2.addNeighbor(node5, 12);
    node2.addNeighbor(node6, 5);
    node3.addNeighbor(node4, 7);
    node3.addNeighbor(node5, 10);
    node4.addNeighbor(node5, 2);
    node5.addNeighbor(node7, 5);
    node6.addNeighbor(node7, 16);

    Node startNode = node1;
    Node endNode = node7;

    Node result = bfsSearch(startNode, endNode);
    if (result != null) {
      printPath(result);
    } else {
      System.out.println("No path found.");
    }
  }
}
