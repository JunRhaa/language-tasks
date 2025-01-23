class Player {
  String name;
  String symbol; // 'X' or 'O'

  Player(this.name, this.symbol);
}

class Game {
  List<List<String?>> board;
  Player player1;
  Player player2;
  Player currentPlayer;

  Game(this.player1, this.player2)
      : board = List.generate(3, (_) => List.filled(3, null)),
        currentPlayer = player1;

  void displayBoard() {
    for (var row in board) {
      print(row.map((cell) => cell ?? ' ').join(' | '));
      print('---------');
    }
  }

  bool makeMove(int row, int col) {
    if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != null) {
      print('Invalid move. Try again.');
      return false;
    }
    board[row][col] = currentPlayer.symbol;
    return true;
  }

  bool checkWin() {
    // Check rows, columns, and diagonals for a win
    for (int i = 0; i < 3; i++) {
      if (board[i][0] != null && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
        return true; // Row win
      }
      if (board[0][i] != null && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
        return true; // Column win
      }
    }
    if (board[0][0] != null && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
      return true; // Diagonal win
    }
    if (board[0][2] != null && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
      return true; // Diagonal win
    }
    return false;
  }

  void switchPlayer() {
    currentPlayer = currentPlayer == player1 ? player2 : player1;
  }

  void playGame() {
    int moves = 0;
    while (moves < 9) {
      displayBoard();
      print('${currentPlayer.name}, enter your move (row and column):');
      int row = int.parse(stdin.readLineSync()!);
      int col = int.parse(stdin.readLineSync()!);

      if (makeMove(row, col)) {
        moves++;
        if (checkWin()) {
          displayBoard();
          print('${currentPlayer.name} wins!');
          return;
        }
        switchPlayer();
      }
    }
    displayBoard();
    print('It\'s a draw!');
  }
}
