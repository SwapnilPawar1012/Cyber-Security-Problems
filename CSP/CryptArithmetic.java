package CSP;

import java.util.*;

public class CryptArithmetic {

  // send + more = money
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

  static boolean solveCryptArithmetic(
    Map<Variable, Integer> assignment,
    Set<Integer> usedDigit
  ) {
    if (assignment.size() == 8) {
      return checkEquation(assignment);
    }

    Variable unassignedVariable = null;
    for (Variable variable : Variable.values()) {
      if (!assignment.containsKey(variable)) {
        unassignedVariable = variable;
        break;
      }
    }

    if (unassignedVariable != null) {
      for (int digit = 0; digit <= 9; digit++) {
        if (!usedDigit.contains(digit)) {
          assignment.put(unassignedVariable, digit);
          usedDigit.add(digit);

          if (solveCryptArithmetic(assignment, usedDigit)) {
            return true;
          }

          assignment.remove(unassignedVariable);
          usedDigit.remove(digit);
        }
      }
    }
    return false;
  }

  static boolean checkEquation(Map<Variable, Integer> assignment) {
    int send =
      (
        assignment.get(Variable.S) *
        1000 +
        assignment.get(Variable.E) *
        100 +
        assignment.get(Variable.N) *
        10 +
        assignment.get(Variable.D)
      );

    int more =
      (
        assignment.get(Variable.M) *
        1000 +
        assignment.get(Variable.O) *
        100 +
        assignment.get(Variable.R) *
        10 +
        assignment.get(Variable.E)
      );

    int money =
      (
        assignment.get(Variable.M) *
        10000 +
        assignment.get(Variable.O) *
        1000 +
        assignment.get(Variable.N) *
        100 +
        assignment.get(Variable.E) *
        10 +
        assignment.get(Variable.Y)
      );

    return send + more == money;
  }

  public static void main(String[] args) {
    Map<Variable, Integer> assignment = new HashMap<>();
    Set<Integer> usedDigit = new HashSet<>();

    if (solveCryptArithmetic(assignment, usedDigit)) {
      System.out.println("Solution found: ");
      for (Map.Entry<Variable, Integer> entry : assignment.entrySet()) {
        System.out.println(entry.getKey() + " -> " + entry.getValue());
      }
    } else {
      System.out.println("Solution not found.");
    }
  }
}
