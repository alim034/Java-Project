import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToeAI extends JFrame 
{
    private JButton[][] buttons;
    private char[][] board;
    private boolean playerTurn;
    private final char PLAYER = 'X';
    private final char AI_PLAYER = 'O';

    public TicTacToeAI() {
        setTitle("Tic-Tac-Toe with AI");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];
        board = new char[3][3];
        playerTurn = true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].setBackground(Color.white);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(new ButtonClickListener(i, j));
                add(buttons[i][j]);
                board[i][j] = ' ';
            }
        }

        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int x;
        private int y;

        public ButtonClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void actionPerformed(ActionEvent e) {
            if (playerTurn && board[x][y] == ' ') {
                buttons[x][y].setText("X");
                buttons[x][y].setEnabled(false);
                board[x][y] = PLAYER;
                playerTurn = false;

                if (isGameFinished(PLAYER)) {
                    displayResult("Player wins!");
                } else {
                    aiMove();
                    if (isGameFinished(AI_PLAYER)) {
                        displayResult("AI wins!");
                    }
                }
            }
        }
    }

    private boolean isGameFinished(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        return board[0][2] == player && board[1][1] == player && board[2][0] == player;
    }

    private void aiMove() {
        int[] bestMove = minimax(board, AI_PLAYER);

        if (bestMove != null) {
            int x = bestMove[0];
            int y = bestMove[1];
            buttons[x][y].setText("O");
            buttons[x][y].setEnabled(false);
            board[x][y] = AI_PLAYER;
            playerTurn = true;
        }
    }

    private int[] minimax(char[][] currentBoard, char player) {
        int[] bestMove = null;
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentBoard[i][j] == ' ') {
                    currentBoard[i][j] = player;
                    int score = minimaxHelper(currentBoard, 0, false);
                    currentBoard[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }
        return bestMove;
    }

    private int minimaxHelper(char[][] currentBoard, int depth, boolean isMaximizing) {
        char opponent = isMaximizing ? PLAYER : AI_PLAYER;

        if (isGameFinished(PLAYER)) {
            return -1;
        }
        if (isGameFinished(AI_PLAYER)) {
            return 1;
        }
        if (isBoardFull(currentBoard)) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (currentBoard[i][j] == ' ') {
                        currentBoard[i][j] = AI_PLAYER;
                        int score = minimaxHelper(currentBoard, depth + 1, false);
                        currentBoard[i][j] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (currentBoard[i][j] == ' ') {
                        currentBoard[i][j] = PLAYER;
                        int score = minimaxHelper(currentBoard, depth + 1, true);
                        currentBoard[i][j] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    private boolean isBoardFull(char[][] currentBoard) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentBoard[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void displayResult(String message) {
        JOptionPane.showMessageDialog(this, message);
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
                board[i][j] = ' ';
            }
        }
        playerTurn = true;
    }

    public static void main(String[] args) {
        new TicTacToeAI();
    }
}
