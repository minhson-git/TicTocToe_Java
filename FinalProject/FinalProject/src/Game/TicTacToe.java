package Game;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Game.Board.Mark;

public class TicTacToe implements ActionListener {

    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    Random random = new Random();
    JFrame frame = new JFrame("Game TicTacToe");
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JPanel control_panel = new JPanel();
    JLabel textfield = new JLabel();
    JLabel scoreLabel = new JLabel();
    JButton[] buttons = new JButton[9];
    JButton reset_Btn;
    JButton newGame_Btn;
    int playerX_score = 0;
    int playerO_score = 0;
    boolean player1_turn;
    boolean playWithAI;
    int difficulty; // 1 for Easy, 2 for Medium, 3 for Hard
    Board board;
    MiniMaxImproved ai;

    TicTacToe() {
        board = new Board(3);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        int x = ((int) dimension.getWidth() / 2 - (frame.getWidth() / 2));
        int y = ((int) dimension.getHeight() / 2 - (frame.getHeight() / 2));
        frame.setLocation(x, y);

        textfield.setBackground(new Color(82, 153, 247));
        textfield.setForeground(new Color(255, 255, 255));
        textfield.setFont(new Font("MV Boli", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        scoreLabel.setBackground(new Color(25, 25, 25));
        scoreLabel.setForeground(new Color(255, 255, 255));
        scoreLabel.setFont(new Font("MV Boli", Font.BOLD, 20));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setText("Score - X: 0, Score - O: 0");
        scoreLabel.setOpaque(true);

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 600, 100);

        button_panel.setLayout(new GridLayout(3, 3));
        button_panel.setBackground(new Color(150, 150, 150));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("Arial", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].setBackground(new Color(255, 255, 255));
            buttons[i].addActionListener(this);
        }

        control_panel.setLayout(new FlowLayout());

        reset_Btn = new JButton("Reset");
        reset_Btn.addActionListener(this);

        newGame_Btn = new JButton("New Game");
        newGame_Btn.addActionListener(this);

        control_panel.add(reset_Btn);
        control_panel.add(newGame_Btn);

        title_panel.add(textfield, BorderLayout.NORTH);
        title_panel.add(scoreLabel, BorderLayout.SOUTH);

        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel);
        frame.add(control_panel, BorderLayout.SOUTH);

        chooseGameMode();
    }

    private void chooseGameMode() {
        String[] options = {"1 vs 1", "1 vs Computer"};
        int choice = JOptionPane.showOptionDialog(frame, "Choose game mode:", "Game mode",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        playWithAI = (choice == 1);
        if (playWithAI) {
            chooseDifficulty();
        }
        firstTurn();
    }

    private void chooseDifficulty() {
        String[] options = {"Easy", "Medium", "Hard"};
        int choice = JOptionPane.showOptionDialog(frame, "Choose difficulty:", "Difficulty",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[2]);

        difficulty = choice + 1; // Easy = 1, Medium = 2, Hard = 3
        ai = new MiniMaxImproved(difficulty);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (player1_turn) {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(255, 0, 0));
                        buttons[i].setText("X");
                        board.setMarkAt(i / 3, i % 3, Mark.X);
                        player1_turn = false;
                        textfield.setText("O turn");
                        check();
                        if (playWithAI && !player1_turn) {
                            aiMove();
                        }
                    }
                } else if (!playWithAI) {
                    if (buttons[i].getText().equals("")) {
                        buttons[i].setForeground(new Color(0, 0, 255));
                        buttons[i].setText("O");
                        board.setMarkAt(i / 3, i % 3, Mark.O);
                        player1_turn = true;
                        textfield.setText("X turn");
                        check();
                    }
                }
            }
        }
        if (e.getSource() == reset_Btn) {
            resetGame();
        }

        if (e.getSource() == newGame_Btn) {
            newGame();
        }
    }

    public void firstTurn() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (random.nextInt(2) == 0) {
            player1_turn = true;
            textfield.setText("X turn");
        } else {
            player1_turn = false;
            textfield.setText("O turn");
            if (playWithAI) {
                aiMove();
            }
        }
    }

    public void check() {
        if (
            (buttons[0].getText().equals("X") && buttons[1].getText().equals("X") && buttons[2].getText().equals("X")) ||
            (buttons[3].getText().equals("X") && buttons[4].getText().equals("X") && buttons[5].getText().equals("X")) ||
            (buttons[6].getText().equals("X") && buttons[7].getText().equals("X") && buttons[8].getText().equals("X")) ||
            (buttons[0].getText().equals("X") && buttons[3].getText().equals("X") && buttons[6].getText().equals("X")) ||
            (buttons[1].getText().equals("X") && buttons[4].getText().equals("X") && buttons[7].getText().equals("X")) ||
            (buttons[2].getText().equals("X") && buttons[5].getText().equals("X") && buttons[8].getText().equals("X")) ||
            (buttons[0].getText().equals("X") && buttons[4].getText().equals("X") && buttons[8].getText().equals("X")) ||
            (buttons[2].getText().equals("X") && buttons[4].getText().equals("X") && buttons[6].getText().equals("X"))
        ) {
            xWins();
        } else if (
            (buttons[0].getText().equals("O") && buttons[1].getText().equals("O") && buttons[2].getText().equals("O")) ||
            (buttons[3].getText().equals("O") && buttons[4].getText().equals("O") && buttons[5].getText().equals("O")) ||
            (buttons[6].getText().equals("O") && buttons[7].getText().equals("O") && buttons[8].getText().equals("O")) ||
            (buttons[0].getText().equals("O") && buttons[3].getText().equals("O") && buttons[6].getText().equals("O")) ||
            (buttons[1].getText().equals("O") && buttons[4].getText().equals("O") && buttons[7].getText().equals("O")) ||
            (buttons[2].getText().equals("O") && buttons[5].getText().equals("O") && buttons[8].getText().equals("O")) ||
            (buttons[0].getText().equals("O") && buttons[4].getText().equals("O") && buttons[8].getText().equals("O")) ||
            (buttons[2].getText().equals("O") && buttons[4].getText().equals("O") && buttons[6].getText().equals("O"))
        ) {
            oWins();
        } else {
            boolean isDraw = true;
            for (int i = 0; i < 9; i++) {
                if (buttons[i].getText().equals("")) {
                    isDraw = false;
                    break;
                }
            }
            if (isDraw) {
                JOptionPane.showMessageDialog(frame, "It's a draw", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                resetGame();
            }
        }
    }

    public void xWins() {
        playerX_score++;
        updateScore();
        JOptionPane.showMessageDialog(frame, "Player X Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        resetGame();
    }

    public void oWins() {
        playerO_score++;
        updateScore();
        JOptionPane.showMessageDialog(frame, "Player O Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        resetGame();
    }

    public void updateScore() {
        scoreLabel.setText("Score - X: " + playerX_score + ", Score - O: " + playerO_score);
    }

    public void resetGame() {
        board = new Board(3);
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
        }
        firstTurn();
    }

    public void newGame() {
        playerX_score = 0;
        playerO_score = 0;
        updateScore();
        resetGame();
    }

    private void aiMove() {
        int bestMove = ai.getBestMove(board, Mark.O);
        buttons[bestMove].setForeground(new Color(0, 0, 255));
        buttons[bestMove].setText("O");
        board.setMarkAt(bestMove / 3, bestMove % 3, Mark.O);
        player1_turn = true;
        textfield.setText("X turn");
        check();
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}