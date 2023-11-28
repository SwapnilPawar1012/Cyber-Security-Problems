package extra;
import java.util.Scanner;

public class AI {

  private static final int SIZE = 3;
  private static final int EMPTY = 0;
  private static final int AI_PLAYER = 2;
  private static final int HUMAN_PLAYER = 1;

  private static int[][] board = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };

  private static boolean gameOver() {
    return checkForWin(AI_PLAYER) || checkForWin(HUMAN_PLAYER) || isBoardFull();
  }

  private static boolean checkForWin(int player) {
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

  private static boolean isBoardFull() {
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        if (board[i][j] == EMPTY) {
          return false;
        }
      }
    }
    return true;
  }

  private static int[] minimax(int player) {
    // Initialize the bestMove array to represent the row and column of the best move
    int[] bestMove = { -1, -1 };

    // Initialize the bestScore based on the player (AI_PLAYER or HUMAN_PLAYER)
    int bestScore = (player == AI_PLAYER)
      ? Integer.MIN_VALUE
      : Integer.MAX_VALUE;

    // Check if the game is over
    if (gameOver()) {
      // If the game is over, evaluate the score based on the current state
      int score = (checkForWin(AI_PLAYER))
        ? 10
        : (checkForWin(HUMAN_PLAYER)) ? -10 : 0;
      // Return the score along with the move (-1, -1) indicating no valid move
      return new int[] { score, -1, -1 };
    }

    // Iterate through the board to explore possible moves
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        // If the current cell is empty, try placing the player's symbol (AI or human)
        if (board[i][j] == EMPTY) {
          board[i][j] = player; // Simulate the move

          // Recursively call minimax to evaluate the opponent's move
          int[] currentMove = minimax(
            (player == AI_PLAYER) ? HUMAN_PLAYER : AI_PLAYER
          );

          // Update the bestScore and bestMove based on the evaluation of the current move
          if (
            (player == AI_PLAYER && currentMove[0] > bestScore) ||
            (player == HUMAN_PLAYER && currentMove[0] < bestScore)
          ) {
            bestScore = currentMove[0];
            bestMove = new int[] { bestScore, i, j }; // Update the best move
          }

          board[i][j] = EMPTY; // Reset the cell to EMPTY after simulation
        }
      }
    }
    // Return the bestMove found during the search
    return bestMove;
  }

  private static void makeAIMove() {
    // Get the best move for the AI using the minimax algorithm
    int[] move = minimax(AI_PLAYER);
    int row = move[1]; // Retrieve the row of the best move
    int col = move[2]; // Retrieve the column of the best move
    board[row][col] = AI_PLAYER; // Make the AI move by placing its symbol in the determined position
  }

  //   private static void printBoard() {
  //     for (int[] row : board) {
  //       for (int cell : row) {
  //         if (cell == EMPTY) {
  //           System.out.print("- ");
  //         } else if (cell == AI_PLAYER) {
  //           System.out.print("O ");
  //         } else {
  //           System.out.print("X ");
  //         }
  //       }
  //       System.out.println();
  //     }
  //   }

  private static void printBoard() {
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
    Scanner scanner = new Scanner(System.in);
    while (!gameOver()) {
      printBoard();
      System.out.println("Available moves:");
      System.out.println("1 2 3");
      System.out.println("4 5 6");
      System.out.println("7 8 9");
      System.out.println("Enter position (1-9):");

      int position = scanner.nextInt();
      int row = (position - 1) / SIZE;
      int col = (position - 1) % SIZE;

      if (position < 1 || position > 9 || board[row][col] != EMPTY) {
        System.out.println("Invalid move. Try again.");
        continue;
      }

      board[row][col] = HUMAN_PLAYER;

      if (!gameOver()) {
        makeAIMove();
      }
    }
    scanner.close();

    printBoard();
    if (checkForWin(AI_PLAYER)) {
      System.out.println("AI wins!");
    } else if (checkForWin(HUMAN_PLAYER)) {
      System.out.println("You win!");
    } else {
      System.out.println("It's a tie!");
    }
  }
}
