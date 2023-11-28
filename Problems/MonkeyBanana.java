package Problems;

import java.util.*;

public class MonkeyBanana {

  private static class State {

    private boolean monkeyOnBox;
    private boolean bananaOnBox;

    public State(boolean monkeyOnBox, boolean bananaOnBox) {
      this.monkeyOnBox = monkeyOnBox;
      this.bananaOnBox = bananaOnBox;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      State state = (State) o;
      return (
        monkeyOnBox == state.monkeyOnBox && bananaOnBox == state.bananaOnBox
      );
    }

    @Override
    public int hashCode() {
      return Objects.hash(monkeyOnBox, bananaOnBox);
    }

    @Override
    public String toString() {
      return (
        "State{" +
        "monkeyOnBox=" +
        monkeyOnBox +
        ", bananaOnBox=" +
        bananaOnBox +
        '}'
      );
    }
  }

  private static void solve() {
    Set<State> visitedStates = new HashSet<>();

    State initialState = new State(false, true);
    Queue<State> queue = new LinkedList<>();
    queue.add(initialState);

    while (!queue.isEmpty()) {
      State currentState = queue.poll();
      System.out.println(currentState);

      if (currentState.monkeyOnBox && currentState.bananaOnBox) {
        System.out.println("Solution found!");
        return;
      }

      visitedStates.add(currentState);

      State newState1 = new State(
        !currentState.monkeyOnBox,
        currentState.bananaOnBox
      );
      if (!visitedStates.contains(newState1)) {
        queue.add(newState1);
      }

      State newState2 = new State(
        currentState.monkeyOnBox,
        !currentState.bananaOnBox
      );
      if (!visitedStates.contains(newState2)) {
        queue.add(newState2);
      }
    }

    System.out.println("Solution not found.");
  }

  public static void main(String[] args) {
    solve();
  }
}
