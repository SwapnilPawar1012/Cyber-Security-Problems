import java.util.Scanner;

public class TicTacToe_NonAI {

  private char[][] board;
  private char currentPlayerMark;

  public TicTacToe_NonAI() {
    board = new char[3][3];
    currentPlayerMark = 'X';
    initializeBoard();
  }

  public void initializeBoard() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        board[i][j] = '-';
      }
    }
  }

  public void printBoard() {
    System.out.println("-------------");
    for (int i = 0; i < 3; i++) {
      System.out.print("| ");
      for (int j = 0; j < 3; j++) {
        System.out.print(board[i][j] + " | ");
      }
      System.out.println();
      System.out.println("-------------");
    }
  }

  public boolean isBoardFull() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[i][j] == '-') {
          return false;
        }
      }
    }
    return true;
  }

  public boolean checkForWin() {
    return (
      checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin()
    );
  }

  private boolean checkRowsForWin() {
    for (int i = 0; i < 3; i++) {
      if (checkRowCol(board[i][0], board[i][1], board[i][2])) {
        return true;
      }
    }
    return false;
  }

  private boolean checkColumnsForWin() {
    for (int i = 0; i < 3; i++) {
      if (checkRowCol(board[0][i], board[1][i], board[2][i])) {
        return true;
      }
    }
    return false;
  }

  private boolean checkDiagonalsForWin() {
    return (
      (checkRowCol(board[0][0], board[1][1], board[2][2])) ||
      (checkRowCol(board[0][2], board[1][1], board[2][0]))
    );
  }

  private boolean checkRowCol(char c1, char c2, char c3) {
    return ((c1 != '-') && (c1 == c2) && (c2 == c3));
  }

  public boolean placeMark(int row, int col) {
    if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == '-') {
      board[row][col] = currentPlayerMark;
      return true;
    }
    return false;
  }

  public void changePlayer() {
    if (currentPlayerMark == 'X') {
      currentPlayerMark = 'O';
    } else {
      currentPlayerMark = 'X';
    }
  }

  public static void main(String[] arg) {
    Scanner sc = new Scanner(System.in);
    TicTacToe_NonAI game = new TicTacToe_NonAI();
    game.initializeBoard();

    while (!game.isBoardFull() && !game.checkForWin()) {
      game.printBoard();
      int row, col;

      do {
        System.out.print(
          "Player " + game.currentPlayerMark + ", enter row(0-2): "
        );
        row = sc.nextInt();
        System.out.println(
          "Player " + game.currentPlayerMark + ", enter column(0-2): "
        );
        col = sc.nextInt();
      } while (!game.placeMark(row, col));

      game.changePlayer();
    }

    game.printBoard();
    if (game.checkForWin()) {
      game.changePlayer();
      System.out.println("Player " + game.currentPlayerMark + " wins!");
    } else {
      System.out.println("It's a tie!");
    }

    sc.close();
  }
}