package Game;

import java.util.Random;

import Game.Board.Mark;

public class MiniMaxImproved {
    private int difficulty; // 1 for Easy, 2 for Medium, 3 for Hard

    public MiniMaxImproved(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getBestMove(Board board, Mark aiMark) {
        if (difficulty == 1) {
            return getRandomMove(board);
        } else if (difficulty == 2) {
            // Make a random move half of the time
            Random rand = new Random();
            if (rand.nextInt(2) == 0) {
                return getRandomMove(board);
            }
        }
        // Hard mode (original "minimax" logic)
        return minimax(board, aiMark);
    }

    private int getRandomMove(Board board) {
        Random rand = new Random();
        int move;
        do {
            move = rand.nextInt(9);
        } while (board.isTileMarked(move / 3, move % 3));
        return move;
    }

    private int minimax(Board board, Mark aiMark) {
        // Original minimax logic here
        // ...
        return 0; // Placeholder
    }
}