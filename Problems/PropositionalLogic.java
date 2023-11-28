package Problems;

public class PropositionalLogic {

  // Logical NOT operation
  public static boolean not(boolean p) {
    return !p;
  }

  // Logical AND operation
  public static boolean and(boolean p, boolean q) {
    return p && q;
  }

  // Logical OR operation
  public static boolean or(boolean p, boolean q) {
    return p || q;
  }

  // Logical XOR operation
  public static boolean xor(boolean p, boolean q) {
    return p ^ q;
  }

  public static void main(String[] args) {
    boolean p = true;
    boolean q = false;

    // Perform logical operations and display results
    System.out.println("NOT p: " + not(p));
    System.out.println("AND p and q: " + and(p, q));
    System.out.println("OR p or q: " + or(p, q));
    System.out.println("XOR p xor q: " + xor(p, q));
  }
}
