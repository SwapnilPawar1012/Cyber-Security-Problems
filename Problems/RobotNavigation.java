package Problems;

import java.util.*;

class Point {

  int x, y;
  double f, g, h; // A*, f = g + h
  Point parent;

  Point(int x, int y) {
    this.x = x;
    this.y = y;
  }
}

public class RobotNavigation {

  private final int[][] grid; // 2D array representing the environment grid
  private final int gridSize;
  private final Point start;
  private final Point goal;

  public RobotNavigation(int[][] grid, Point start, Point goal) {
    this.grid = grid;
    this.gridSize = grid.length;
    this.start = start;
    this.goal = goal;
  }

  // Function to perform A* pathfinding
  public List<Point> findPath() {
    PriorityQueue<Point> openSet = new PriorityQueue<>((a, b) ->
      Double.compare(a.f, b.f)
    );
    Set<Point> closedSet = new HashSet<>();
    openSet.add(start);

    while (!openSet.isEmpty()) {
      Point current = openSet.poll();

      if (current.x == goal.x && current.y == goal.y) {
        return reconstructPath(current);
      }

      closedSet.add(current);

      for (Point neighbor : getNeighbors(current)) {
        if (closedSet.contains(neighbor)) {
          continue;
        }

        double tentative_g = current.g + 1; // Assuming each move cost is 1

        if (!openSet.contains(neighbor) || tentative_g < neighbor.g) {
          neighbor.parent = current;
          neighbor.g = tentative_g;
          neighbor.h = calculateHeuristic(neighbor, goal);
          neighbor.f = neighbor.g + neighbor.h;

          if (!openSet.contains(neighbor)) {
            openSet.add(neighbor);
          }
        }
      }
    }

    return null; // Path not found
  }

  // Function to calculate heuristic (Euclidean distance)
  private double calculateHeuristic(Point a, Point b) {
    return Math.sqrt(Math.pow((a.x - b.x), 2) + Math.pow((a.y - b.y), 2));
  }

  // Function to get valid neighbors for a point
  private List<Point> getNeighbors(Point p) {
    List<Point> neighbors = new ArrayList<>();
    int[] dx = { -1, 0, 1, 0 }; // For 4-connected neighbors (up, right, down, left)
    int[] dy = { 0, 1, 0, -1 };

    for (int i = 0; i < 4; i++) {
      int nx = p.x + dx[i];
      int ny = p.y + dy[i];

      if (
        nx >= 0 &&
        nx < gridSize &&
        ny >= 0 &&
        ny < gridSize &&
        grid[nx][ny] != 1
      ) {
        neighbors.add(new Point(nx, ny));
      }
    }

    return neighbors;
  }

  // Function to reconstruct path from goal to start
  private List<Point> reconstructPath(Point current) {
    List<Point> path = new ArrayList<>();
    while (current != null) {
      path.add(current);
      current = current.parent;
    }
    Collections.reverse(path);
    return path;
  }

  public static void main(String[] args) {
    int[][] grid = {
      { 0, 0, 0, 0, 0 },
      { 0, 1, 1, 1, 0 },
      { 0, 1, 0, 0, 0 },
      { 0, 0, 0, 1, 0 },
      { 0, 0, 0, 0, 0 },
    };
    Point start = new Point(0, 0);
    Point goal = new Point(4, 4);

    RobotNavigation navigator = new RobotNavigation(grid, start, goal);
    List<Point> path = navigator.findPath();

    if (path != null) {
      System.out.println("Path found:");
      for (Point point : path) {
        System.out.println("(" + point.x + ", " + point.y + ")");
      }
    } else {
      System.out.println("No path found.");
    }
  }
}
