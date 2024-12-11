package Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSelector {
    private JFrame frame;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;

    public GameSelector() {
        frame = new JFrame("Select Game Difficulty");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        easyButton = new JButton("Easy");
        easyButton.setBounds(50, 30, 200, 30);
        easyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame(1);
            }
        });

        mediumButton = new JButton("Medium");
        mediumButton.setBounds(50, 70, 200, 30);
        mediumButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame(2);
            }
        });

        hardButton = new JButton("Hard");
        hardButton.setBounds(50, 110, 200, 30);
        hardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame(3);
            }
        });

        frame.add(easyButton);
        frame.add(mediumButton);
        frame.add(hardButton);
        frame.setVisible(true);
    }

    private void startGame(int difficulty) {
        frame.dispose();
        TicTacToe game = new TicTacToe();
    }

    public static void main(String[] args) {
        new GameSelector();
    }
}
