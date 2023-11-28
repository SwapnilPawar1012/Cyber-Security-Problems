package extra;

import java.util.*;

class MonkeyBananaProblem {

  static final int ROOM_HEIGHT = 4;
  static final int BOX_HEIGHT = 2;

  static class State {

    int monkeyPosition;
    boolean hasBanana;
    boolean isOnBox;

    State(int monkeyPosition, boolean hasBanana, boolean isOnBox) {
      this.monkeyPosition = monkeyPosition;
      this.hasBanana = hasBanana;
      this.isOnBox = isOnBox;
    }

    @Override
    public int hashCode() {
      return Objects.hash(monkeyPosition, hasBanana, isOnBox);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      State other = (State) obj;
      return (
        monkeyPosition == other.monkeyPosition &&
        hasBanana == other.hasBanana &&
        isOnBox == other.isOnBox
      );
    }
  }

  static List<String> solveMonkeyBanana() {
    Queue<State> queue = new LinkedList<>();
    Map<State, State> cameFrom = new HashMap<>();
    State initial = new State(0, false, false);
    queue.add(initial);
    cameFrom.put(initial, null);

    while (!queue.isEmpty()) {
      State current = queue.poll();

      if (current.hasBanana && current.monkeyPosition == 0 && current.isOnBox) {
        return reconstructPath(cameFrom, current);
      }

      // Perform actions (move left or right, climb box, grasp banana)
      for (State nextState : generateSuccessors(current)) {
        if (!cameFrom.containsKey(nextState)) {
          queue.add(nextState);
          cameFrom.put(nextState, current);
        }
      }
    }

    return null;
  }

  static List<State> generateSuccessors(State currentState) {
    List<State> successors = new ArrayList<>();

    if (!currentState.isOnBox) {
      // If the monkey is not on the box, it can only move left or right
      if (currentState.monkeyPosition > 0) {
        successors.add(
          new State(
            currentState.monkeyPosition - 1,
            currentState.hasBanana,
            false
          )
        );
      }
      if (currentState.monkeyPosition < ROOM_HEIGHT - 1) {
        successors.add(
          new State(
            currentState.monkeyPosition + 1,
            currentState.hasBanana,
            false
          )
        );
      }
    } else {
      // If the monkey is on the box, it can climb down, move left or right, or grasp banana
      successors.add(
        new State(currentState.monkeyPosition, currentState.hasBanana, false)
      );

      if (currentState.monkeyPosition > 0) {
        successors.add(
          new State(
            currentState.monkeyPosition - 1,
            currentState.hasBanana,
            true
          )
        );
      }
      if (currentState.monkeyPosition < ROOM_HEIGHT - 1) {
        successors.add(
          new State(
            currentState.monkeyPosition + 1,
            currentState.hasBanana,
            true
          )
        );
      }
      if (!currentState.hasBanana) {
        successors.add(new State(currentState.monkeyPosition, true, true));
      }
    }

    return successors;
  }

  static List<String> reconstructPath(
    Map<State, State> cameFrom,
    State goalState
  ) {
    List<String> path = new ArrayList<>();
    State current = goalState;

    while (current != null) {
      path.add(0, describeAction(cameFrom.get(current), current));
      current = cameFrom.get(current);
    }

    return path;
  }

  static String describeAction(State prevState, State nextState) {
    if (prevState == null) {
      return "Start";
    }

    if (nextState.isOnBox && !prevState.isOnBox) {
      return "Climb box";
    } else if (!nextState.isOnBox && prevState.isOnBox) {
      return "Climb down";
    } else if (nextState.monkeyPosition > prevState.monkeyPosition) {
      return "Move right";
    } else if (nextState.monkeyPosition < prevState.monkeyPosition) {
      return "Move left";
    } else if (!prevState.hasBanana && nextState.hasBanana) {
      return "Grasp banana";
    } else {
      return "Unknown action";
    }
  }

  public static void main(String[] args) {
    List<String> solution = solveMonkeyBanana();

    if (solution != null) {
      for (String action : solution) {
        System.out.println(action);
      }
    } else {
      System.out.println("No solution found.");
    }
  }
}
