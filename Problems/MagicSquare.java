package Problems;

public class MagicSquare {

  // Function to generate a magic square of odd order
  public static int[][] generateMagicSquare(int n) {
    int[][] magicSquare = new int[n][n];

    int num = 1;
    int i = n / 2;
    int j = n - 1;

    while (num <= n * n) {
      if (i == -1 && j == n) {
        j = n - 2;
        i = 0;
      } else {
        if (j == n) {
          j = 0;
        }
        if (i < 0) {
          i = n - 1;
        }
      }

      if (magicSquare[i][j] != 0) {
        j -= 2;
        i++;
        continue;
      } else {
        magicSquare[i][j] = num++;
      }

      j++;
      i--;
    }

    return magicSquare;
  }

  // Function to print the magic square
  public static void printMagicSquare(int[][] magicSquare) {
    for (int[] row : magicSquare) {
      for (int num : row) {
        System.out.print(num + "\t");
      }
      System.out.println();
    }
  }

  public static void main(String[] args) {
    int n = 3; // Change n for different odd-order magic squares

    if (n % 2 == 0) {
      System.out.println(
        "Please enter an odd number for odd-order magic square."
      );
      return;
    }

    int[][] magicSquare = generateMagicSquare(n);
    System.out.println("Magic Square of order " + n + ":");
    printMagicSquare(magicSquare);
  }
}
