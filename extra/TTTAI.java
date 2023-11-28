package extra;
import java.util.Scanner;

public class TTTAI {

  public static int SIZE = 3;
  public static int EMPTY = 0;
  public static int HUMAN_PLAYER = 1;
  public static int AI_PLAYER = 2;

  public static int[][] board = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };

  public static boolean gameOver() {
    return (
      checkForWin(AI_PLAYER) || checkForWin(HUMAN_PLAYER) || (isBoardFull())
    );
  }

  public static boolean checkForWin(int player) {
    for (int i = 0; i < SIZE; i++) {
      if (
        (
          board[i][0] == player &&
          board[i][1] == player &&
          board[i][2] == player
        ) ||
        (
          board[0][i] == player &&
          board[1][i] == player &&
          board[2][i] == player
        )
      ) {
        return true;
      }
    }
    return (
      (
        board[0][0] == player && board[1][1] == player && board[2][2] == player
      ) ||
      (board[0][2] == player && board[1][1] == player && board[2][0] == player)
    );
  }

  public static boolean isBoardFull() {
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        if (board[i][j] == EMPTY) {
          return false;
        }
      }
    }
    return true;
  }

  public static int[] minimax(int player) {
    int[] bestMove = { -1, -1 };
    int bestScore = (player == AI_PLAYER)
      ? Integer.MIN_VALUE
      : Integer.MAX_VALUE;

    while (gameOver()) {
      int score = (checkForWin(AI_PLAYER))
        ? 10
        : (checkForWin(HUMAN_PLAYER)) ? -10 : 0;
      return new int[] { score, -1, -1 };
    }

    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        if (board[i][j] == EMPTY) {
          board[i][j] = player;

          int[] currentMove = minimax(
            (player == AI_PLAYER) ? HUMAN_PLAYER : AI_PLAYER
          );

          if (
            (player == AI_PLAYER && currentMove[0] > bestScore) ||
            (player == HUMAN_PLAYER && currentMove[0] < bestScore)
          ) {
            bestScore = currentMove[0];
            bestMove = new int[] { bestScore, i, j };
          }
          board[i][j] = EMPTY;
        }
      }
    }
    return bestMove;
  }

  public static void makeAIMove() {
    int move[] = minimax(AI_PLAYER);
    int row = move[1];
    int col = move[2];
    board[row][col] = AI_PLAYER;
  }

  public static void printBoard() {
    System.out.println("-------------");
    for (int i = 0; i < SIZE; i++) {
      System.out.print("| ");
      for (int j = 0; j < SIZE; j++) {
        if (board[i][j] == EMPTY) {
          System.out.print(" " + " | ");
        } else if (board[i][j] == AI_PLAYER) {
          System.out.print("O" + " | ");
        } else {
          System.out.print("X" + " | ");
        }
      }
      System.out.println();
      System.out.println("-------------");
    }
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    while (!gameOver()) {
      printBoard();

      System.out.println("Available Moves");
      System.out.println("1 2 3");
      System.out.println("4 5 6");
      System.out.println("6 7 8");
      System.out.println("Enter your move(1-9): ");
      int position = sc.nextInt();

      int row = (position - 1) / SIZE;
      int col = (position - 1) % SIZE;

      if (position < 1 && position > 9 && board[row][col] != EMPTY) {
        System.out.println("Invalid move, please try again!");
        continue;
      } else {
        board[row][col] = HUMAN_PLAYER;

        if (!gameOver()) {
          makeAIMove();
        }
      }
    }
    sc.close();

    printBoard();
    if (checkForWin(AI_PLAYER)) {
      System.out.println("AI wins!");
    } else if (checkForWin(HUMAN_PLAYER)) {
      System.out.println("You win!");
    } else {
      System.out.println("It's a tie.");
    }
  }
}
