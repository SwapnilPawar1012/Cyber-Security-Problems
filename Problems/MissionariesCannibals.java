package Problems;

import java.util.*;

public class MissionariesCannibals {

  static class State {

    int missionariesLeft;
    int cannibalsLeft;
    int missionariesRight;
    int cannibalsRight;
    boolean boat; // true if the boat is on the left bank, false if on the right bank
    State parent; // Parent state (to track the solution path)

    State(
      int missionariesLeft,
      int cannibalsLeft,
      int missionariesRight,
      int cannibalsRight,
      boolean boat
    ) {
      this.missionariesLeft = missionariesLeft;
      this.cannibalsLeft = cannibalsLeft;
      this.missionariesRight = missionariesRight;
      this.cannibalsRight = cannibalsRight;
      this.boat = boat;
    }

    boolean isValid() {
      if (
        missionariesLeft < 0 ||
        cannibalsLeft < 0 ||
        missionariesRight < 0 ||
        cannibalsRight < 0
      ) return false;

      return (
        (missionariesLeft == 0 || missionariesLeft >= cannibalsLeft) &&
        (missionariesRight == 0 || missionariesRight >= cannibalsRight)
      );
    }

    boolean isGoal() {
      return (
        missionariesLeft == 0 &&
        cannibalsLeft == 0 &&
        missionariesRight == 3 &&
        cannibalsRight == 3
      );
    }

    List<State> generateSuccessors() {
      List<State> successors = new ArrayList<>();
      if (boat) {
        for (int m = 0; m <= 2; m++) {
          for (int c = 0; c <= 2; c++) {
            if (m + c >= 1 && m + c <= 2) {
              State newState = new State(
                missionariesLeft - m,
                cannibalsLeft - c,
                missionariesRight + m,
                cannibalsRight + c,
                false
              );
              if (newState.isValid()) successors.add(newState);
            }
          }
        }
      } else {
        for (int m = 0; m <= 2; m++) {
          for (int c = 0; c <= 2; c++) {
            if (m + c >= 1 && m + c <= 2) {
              State newState = new State(
                missionariesLeft + m,
                cannibalsLeft + c,
                missionariesRight - m,
                cannibalsRight - c,
                true
              );
              if (newState.isValid()) successors.add(newState);
            }
          }
        }
      }
      return successors;
    }
  }

  static List<State> solve() {
    List<State> path = new ArrayList<>();
    State initialState = new State(3, 3, 0, 0, true);
    Stack<State> stack = new Stack<>();
    Set<State> visited = new HashSet<>();

    stack.push(initialState);
    while (!stack.isEmpty()) {
      State currentState = stack.pop();
      path.add(currentState);

      if (currentState.isGoal()) {
        return path;
      }

      visited.add(currentState);

      List<State> successors = currentState.generateSuccessors();
      for (State successor : successors) {
        if (!visited.contains(successor)) {
          successor.parent = currentState;
          stack.push(successor);
        }
      }
    }
    return null; // No solution found
  }

  static void printSolution(List<State> path) {
    if (path == null) {
      System.out.println("No solution exists.");
      return;
    }

    System.out.println("Solution path:");
    for (int i = 0; i < path.size(); i++) {
      State state = path.get(i);
      System.out.println(
        "Step " +
        i +
        ": " +
        "Left Bank (M: " +
        state.missionariesLeft +
        " C: " +
        state.cannibalsLeft +
        ") | Boat: " +
        (state.boat ? "<---" : "--->") +
        " | Right Bank (M: " +
        state.missionariesRight +
        " C: " +
        state.cannibalsRight +
        ")"
      );
    }
  }

  public static void main(String[] args) {
    List<State> solutionPath = solve();
    printSolution(solutionPath);
  }
}
