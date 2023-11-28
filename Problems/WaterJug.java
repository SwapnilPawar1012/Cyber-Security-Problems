package Problems;

import java.util.*;

public class WaterJug {

  static class State {

    int jugX;
    int jugY;
    State parent;

    State(int jugX, int jugY) {
      this.jugX = jugX;
      this.jugY = jugY;
    }

    boolean isGoal(int target) {
      return jugX == target || jugY == target || jugX + jugY == target;
    }

    List<State> generateSuccessors(int maxX, int maxY) {
      List<State> successors = new ArrayList<>();

      // Fill jug X
      successors.add(new State(maxX, jugY));

      // Fill jug Y
      successors.add(new State(jugX, maxY));

      // Empty jug X
      successors.add(new State(0, jugY));

      // Empty jug Y
      successors.add(new State(jugX, 0));

      // Pour from X to Y
      int pourXY = Math.min(jugX, maxY - jugY);
      successors.add(new State(jugX - pourXY, jugY + pourXY));

      // Pour from Y to X
      int pourYX = Math.min(jugY, maxX - jugX);
      successors.add(new State(jugX + pourYX, jugY - pourYX));

      return successors;
    }

    boolean equals(State otherState) {
      return this.jugX == otherState.jugX && this.jugY == otherState.jugY;
    }
  }

  static List<State> solve(
    int jugCapacityX,
    int jugCapacityY,
    int targetAmount
  ) {
    Queue<State> queue = new LinkedList<>();
    State initialState = new State(0, 0);
    initialState.parent = null;
    queue.offer(initialState);

    Set<State> visited = new HashSet<>();
    visited.add(initialState);

    while (!queue.isEmpty()) {
      State currentState = queue.poll();

      if (currentState.isGoal(targetAmount)) {
        List<State> solutionPath = new ArrayList<>();
        while (currentState != null) {
          solutionPath.add(currentState);
          currentState = currentState.parent;
        }
        Collections.reverse(solutionPath);
        return solutionPath;
      }

      List<State> successors = currentState.generateSuccessors(
        jugCapacityX,
        jugCapacityY
      );
      for (State successor : successors) {
        if (!visited.contains(successor)) {
          successor.parent = currentState;
          queue.offer(successor);
          visited.add(successor);
        }
      }
    }
    return null; // No solution found
  }

  static void printSolution(List<State> solutionPath) {
    if (solutionPath == null) {
      System.out.println("No solution exists.");
      return;
    }

    System.out.println("Solution path:");
    for (int i = 0; i < solutionPath.size(); i++) {
      State state = solutionPath.get(i);
      System.out.println(
        "Step " + i + ": Jug X = " + state.jugX + ", Jug Y = " + state.jugY
      );
    }
  }

  public static void main(String[] args) {
    int jugCapacityX = 4; // Capacity of jug X
    int jugCapacityY = 3; // Capacity of jug Y
    int targetAmount = 2; // Target amount to be measured

    List<State> solutionPath = solve(jugCapacityX, jugCapacityY, targetAmount);
    printSolution(solutionPath);
  }
}
