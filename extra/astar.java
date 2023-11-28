package extra;
import java.util.*;

public class astar {

  public static class Node {

    public int id;
    public List<Edge> neighbors;
    public Node parent;
    public double g; // Cost from start to this node
    public double h; // Heuristic value

    Node(int id) {
      this.id = id;
      this.neighbors = new ArrayList<>();
      this.parent = null;
      this.g = Double.MAX_VALUE; // Set initial cost to infinity
      this.h = 0; // Heuristic value initialized to 0
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

  public static Node aStar(Node start, Node target) {
    PriorityQueue<Node> openList = new PriorityQueue<>(
      Comparator.comparingDouble(n -> n.g + n.h)
    );
    Set<Node> closedSet = new HashSet<>();

    start.g = 0;
    start.h = heuristic(start, target);
    openList.add(start);

    while (!openList.isEmpty()) {
      Node current = openList.poll();

      if (current == target) {
        return current;
      }

      closedSet.add(current);

      for (Edge edge : current.neighbors) {
        Node neighbor = edge.node;

        if (closedSet.contains(neighbor)) {
          continue;
        }

        double tentativeGScore = current.g + edge.weight;

        if (tentativeGScore < neighbor.g) {
          neighbor.parent = current;
          neighbor.g = tentativeGScore;
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
    // Heuristic function (Euclidean distance for demonstration)
    return Math.sqrt(Math.pow(a.id - b.id, 2));
  }

  public static void printPath(Node target) {
    List<Integer> path = new ArrayList<>();
    Node current = target;

    while (current != null) {
      path.add(current.id);
      current = current.parent;
    }
    Collections.reverse(path);

    System.out.println("Shortest path using A*:");
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
