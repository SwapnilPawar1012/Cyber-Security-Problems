package informedSearch;

import java.util.*;

public class aStarAlgo {

  public static class Node {

    public int id;
    public List<Edge> neighbors;
    public Node parent;
    public double g;
    public double h;

    Node(int id) {
      this.id = id;
      this.neighbors = new ArrayList<>();
      this.parent = null;
      this.g = Double.MAX_VALUE;
      this.h = 0;
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

  public static Node aStar(Node startNode, Node target) {
    PriorityQueue<Node> openList = new PriorityQueue<>(
      Comparator.comparingDouble(n -> n.g + n.h)
    );
    Set<Node> closedSet = new HashSet<>();

    startNode.g = 0;
    startNode.h = heuristic(startNode, target);
    openList.add(startNode);

    while (!openList.isEmpty()) {
      Node currentNode = openList.poll();

      if (currentNode == target) {
        return currentNode;
      }

      closedSet.add(currentNode);

      for (Edge edge : currentNode.neighbors) {
        Node neighbor = edge.node;

        if (closedSet.contains(neighbor)) {
          continue;
        }

        Double tentetiveGScore = currentNode.g + edge.weight;

        if (tentetiveGScore < neighbor.g) {
          neighbor.parent = currentNode;
          neighbor.g = tentetiveGScore;
          neighbor.h = heuristic(neighbor, target);

          if (!openList.contains(neighbor)) {
            openList.add(neighbor);
          }
        }
      }
    }
    return null;
  }

  public static double heuristic(Node a, Node b) {
    return Math.sqrt(Math.pow(a.id - b.id, 2));
  }

  public static void printPath(Node target) {
    List<Integer> path = new ArrayList<>();
    Node currentNode = target;

    while (currentNode != null) {
      path.add(currentNode.id);
      currentNode = currentNode.parent;
    }
    Collections.reverse(path);

    System.out.println("Shortest path using A* is: ");
    for (int nodeId : path) {
      System.out.print(nodeId + " ");
    }
    System.out.println();
  }

  public static void main(String[] args) {
    Node node1 = new Node(1);
    Node node2 = new Node(2);
    Node node3 = new Node(3);
    Node node4 = new Node(4);
    Node node5 = new Node(5);
    Node node6 = new Node(6);
    Node node7 = new Node(7);

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

    Node result = aStar(startNode, endNode);
    if (result != null) {
      printPath(result);
    } else {
      System.out.println("No path found.");
    }
  }
}
