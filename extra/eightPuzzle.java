package extra;

import java.util.*;

class EightPuzzle {

  // Represents the goal state
  private final int[][] goalState = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
  private int[][] puzzleState;

  public EightPuzzle(int[][] puzzle) {
    if (
      puzzle.length != 3 || puzzle[0].length != 3
    ) throw new IllegalArgumentException("Invalid puzzle dimensions");

    puzzleState = puzzle;
  }

  // Node class to store puzzle states and moves
  private static class Node {

    int[][] state;
    int moves;
    Node parent;

    public Node(int[][] s, int m, Node p) {
      state = s;
      moves = m;
      parent = p;
    }
  }

  // Heuristic function (Manhattan distance)
  private int calculateHeuristic(int[][] state) {
    int distance = 0;
    for (int i = 0; i < state.length; i++) {
      for (int j = 0; j < state[i].length; j++) {
        int value = state[i][j];
        if (value != 0) {
          int goalRow = (value - 1) / 3;
          int goalCol = (value - 1) % 3;
          distance += Math.abs(i - goalRow) + Math.abs(j - goalCol);
        }
      }
    }
    return distance;
  }

  // Get possible moves
  private List<Node> getPossibleMoves(Node node) {
    List<Node> possibleMoves = new ArrayList<>();
    int emptyRow = -1, emptyCol = -1;

    // Find empty space
    for (int i = 0; i < node.state.length; i++) {
      for (int j = 0; j < node.state[i].length; j++) {
        if (node.state[i][j] == 0) {
          emptyRow = i;
          emptyCol = j;
          break;
        }
      }
    }

    int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    // Generate possible moves
    for (int[] dir : directions) {
      int newRow = emptyRow + dir[0];
      int newCol = emptyCol + dir[1];

      if (newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3) {
        int[][] newState = new int[3][3];
        for (int i = 0; i < 3; i++) {
          newState[i] = Arrays.copyOf(node.state[i], 3);
        }

        int temp = newState[newRow][newCol];
        newState[newRow][newCol] = 0;
        newState[emptyRow][emptyCol] = temp;

        possibleMoves.add(new Node(newState, node.moves + 1, node));
      }
    }

    return possibleMoves;
  }

  // A* Algorithm to solve the puzzle
  public List<int[][]> solvePuzzle() {
    PriorityQueue<Node> openSet = new PriorityQueue<>(
      Comparator.comparingInt(node ->
        node.moves + calculateHeuristic(node.state)
      )
    );
    Set<String> closedSet = new HashSet<>();
    Map<String, Integer> movesMap = new HashMap<>();
    Node startNode = new Node(puzzleState, 0, null);

    openSet.add(startNode);
    movesMap.put(Arrays.deepToString(startNode.state), 0);

    while (!openSet.isEmpty()) {
      Node currentNode = openSet.poll();
      if (Arrays.deepEquals(currentNode.state, goalState)) {
        // Reconstruct path
        List<int[][]> solutionPath = new ArrayList<>();
        while (currentNode != null) {
          solutionPath.add(0, currentNode.state);
          currentNode = currentNode.parent;
        }
        return solutionPath;
      }

      closedSet.add(Arrays.deepToString(currentNode.state));
      List<Node> possibleMoves = getPossibleMoves(currentNode);

      for (Node move : possibleMoves) {
        String key = Arrays.deepToString(move.state);
        if (
          !closedSet.contains(key) ||
          move.moves < movesMap.getOrDefault(key, Integer.MAX_VALUE)
        ) {
          openSet.add(move);
          movesMap.put(key, move.moves);
        }
      }
    }

    return null;
  }

  // Helper method to print puzzle state
  public static void printPuzzle(int[][] state) {
    for (int[] row : state) {
      System.out.println(Arrays.toString(row));
    }
    System.out.println();
  }

  public static void main(String[] args) {
    int[][] initialPuzzle = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 0, 8 } };

    EightPuzzle puzzleSolver = new EightPuzzle(initialPuzzle);
    List<int[][]> solution = puzzleSolver.solvePuzzle();

    if (solution != null) {
      for (int[][] state : solution) {
        printPuzzle(state);
      }
    } else {
      System.out.println("No solution found.");
    }
  }
}
