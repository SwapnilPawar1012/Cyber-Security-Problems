package Problems;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class PredicateLogic {

  // Define a predicate P(x)
  static Predicate<Integer> predicateP = x -> x > 0; // Example predicate: "x is greater than 0"

  // Function to evaluate the statement "For all x, P(x) is true."
  static boolean evaluateForAll(Predicate<Integer> predicate) {
    return Stream
      .of(-3, -2, -1, 0, 1, 2, 3) // A range of values for x
      .allMatch(predicate);
  }

  public static void main(String[] args) {
    // Evaluate the predicate logic statement "∀x P(x)"
    boolean result = evaluateForAll(predicateP);

    if (result) {
      System.out.println("The statement 'For all x, P(x) is true' holds true.");
    } else {
      System.out.println(
        "The statement 'For all x, P(x) is true' does not hold true for all values of x."
      );
    }
  }
}