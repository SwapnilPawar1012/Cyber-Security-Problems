package extra;

import java.util.*;

public class cryptArithmetic {

  // Define the variables (letters)
  enum Variable {
    S,
    E,
    N,
    D,
    M,
    O,
    R,
    Y,
  }

  // Backtracking algorithm to solve Cryptarithmetic puzzle
  static boolean solveCryptarithmetic(
    Map<Variable, Integer> assignment,
    Set<Integer> usedDigits
  ) {
    if (assignment.size() == 8) {
      // All variables have been assigned a digit
      return checkEquation(assignment);
    }

    Variable unassignedVar = null;
    for (Variable var : Variable.values()) {
      if (!assignment.containsKey(var)) {
        unassignedVar = var;
        break;
      }
    }

    if (unassignedVar != null) {
      for (int digit = 0; digit <= 9; digit++) {
        if (!usedDigits.contains(digit)) {
          assignment.put(unassignedVar, digit);
          usedDigits.add(digit);

          if (solveCryptarithmetic(assignment, usedDigits)) {
            return true;
          }

          assignment.remove(unassignedVar);
          usedDigits.remove(digit);
        }
      }
    }

    return false;
  }

  // Check if the equation holds true
  static boolean checkEquation(Map<Variable, Integer> assignment) {
    int send =
      assignment.get(Variable.S) *
      1000 +
      assignment.get(Variable.E) *
      100 +
      assignment.get(Variable.N) *
      10 +
      assignment.get(Variable.D);

    int more =
      assignment.get(Variable.M) *
      1000 +
      assignment.get(Variable.O) *
      100 +
      assignment.get(Variable.R) *
      10 +
      assignment.get(Variable.E);

    int money =
      assignment.get(Variable.M) *
      10000 +
      assignment.get(Variable.O) *
      1000 +
      assignment.get(Variable.N) *
      100 +
      assignment.get(Variable.E) *
      10 +
      assignment.get(Variable.Y);

    return send + more == money;
  }

  public static void main(String[] args) {
    Map<Variable, Integer> assignment = new HashMap<>();
    Set<Integer> usedDigits = new HashSet<>();

    if (solveCryptarithmetic(assignment, usedDigits)) {
      System.out.println("Solution found:");
      for (Map.Entry<Variable, Integer> entry : assignment.entrySet()) {
        System.out.println(entry.getKey() + " -> " + entry.getValue());
      }
    } else {
      System.out.println("No solution found.");
    }
  }
}
/*
Solution found:
D -> 7
O -> 3
E -> 8
Y -> 5
R -> 6
N -> 1
M -> 0
S -> 2
 */
