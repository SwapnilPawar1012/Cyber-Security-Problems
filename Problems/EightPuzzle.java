package Problems;

import java.util.*;

public class EightPuzzle {

  static class Board {

    int[][] tiles;
    int size;
    int manhattanDistance;
    Board parent;

    public Board(int[][] tiles) {
      this.tiles = tiles;
      this.size = tiles.length;
      this.manhattanDistance = calculateManhattanDistance();
      this.parent = null;
    }

    private int calculateManhattanDistance() {
      int distance = 0;
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          if (tiles[i][j] != 0) {
            int expectedRow = (tiles[i][j] - 1) / size;
            int expectedCol = (tiles[i][j] - 1) % size;
            distance += Math.abs(expectedRow - i) + Math.abs(expectedCol - j);
          }
        }
      }
      return distance;
    }

    public boolean isGoal() {
      return manhattanDistance == 0;
    }

    public List<Board> getNeighbors() {
      List<Board> neighbors = new ArrayList<>();
      int[][] dirs = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };

      int emptyRow = 0, emptyCol = 0;
      outerloop:for (emptyRow = 0; emptyRow < size; emptyRow++) {
        for (emptyCol = 0; emptyCol < size; emptyCol++) {
          if (tiles[emptyRow][emptyCol] == 0) {
            break outerloop;
          }
        }
      }

      for (int[] dir : dirs) {
        int newRow = emptyRow + dir[0];
        int newCol = emptyCol + dir[1];

        if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
          int[][] newTiles = deepCopy(tiles);
          newTiles[emptyRow][emptyCol] = newTiles[newRow][newCol];
          newTiles[newRow][newCol] = 0;
          neighbors.add(new Board(newTiles));
        }
      }
      return neighbors;
    }

    private int[][] deepCopy(int[][] matrix) {
      int[][] copy = new int[size][size];
      for (int i = 0; i < size; i++) {
        System.arraycopy(matrix[i], 0, copy[i], 0, size);
      }
      return copy;
    }
  }

  static Board solve(Board initial) {
    PriorityQueue<Board> openList = new PriorityQueue<>(
      Comparator.comparingInt(a -> a.manhattanDistance)
    );
    Set<Board> closedList = new HashSet<>();

    openList.add(initial);

    while (!openList.isEmpty()) {
      Board current = openList.poll();

      if (current.isGoal()) {
        return current;
      }

      closedList.add(current);

      for (Board neighbor : current.getNeighbors()) {
        if (!closedList.contains(neighbor)) {
          neighbor.parent = current;
          openList.add(neighbor);
        }
      }
    }
    return null;
  }

  static void printSolution(Board goalBoard) {
    List<Board> path = new ArrayList<>();
    while (goalBoard != null) {
      path.add(goalBoard);
      goalBoard = goalBoard.parent;
    }
    Collections.reverse(path);

    for (int i = 0; i < path.size(); i++) {
      System.out.println("Step " + i + ":");
      printBoard(path.get(i).tiles);
    }
  }

  static void printBoard(int[][] board) {
    for (int[] row : board) {
      for (int tile : row) {
        System.out.print(tile + " ");
      }
      System.out.println();
    }
    System.out.println();
  }

  public static void main(String[] args) {
    int[][] initialTiles = { { 1, 2, 3 }, { 4, 0, 6 }, { 7, 5, 8 } };

    Board initialBoard = new Board(initialTiles);
    Board goalBoard = solve(initialBoard);

    if (goalBoard != null) {
      System.out.println("Solution found:");
      printSolution(goalBoard);
    } else {
      System.out.println("No solution found.");
    }
  }
}
