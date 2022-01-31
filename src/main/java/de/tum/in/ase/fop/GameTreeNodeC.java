package de.tum.in.ase.fop;

import static de.tum.in.ase.fop.Constants.*;

public class GameTreeNodeC extends GameTreeNode {

    public GameTreeNodeC(PlayGround playGround) {
        this(playGround, MIN, MAX);
    }

    private GameTreeNodeC(PlayGround playGround, int goalMin, int goalMax) {
        super(playGround);

        // have I lost already?
        if (playGround.winner() != NONE) {
            value = playGround.winner();
            return;
        }

        // no more moves --> no winner
        var moves = playGround.getMoves();
        if (!moves.hasNext()) {
            value = DRAW;
            return;
        }

        value = -2 * type;
        while (moves.hasNext()) {
            int move = moves.next();
            children[move] = new GameTreeNodeC(playGround.makeMove(move), goalMin, goalMax);
            if (type == MIN && children[move].value < value || type == MAX && children[move].value > value) {
                value = children[move].value;
                bestMove = move;
                // update goals
                if (type == MIN && goalMax > value) {
                    goalMax = value;
                }
                if (type == MAX && goalMin < value) {
                    goalMin = value;
                }
                // leave if goal is reached
                if (type == MIN && value <= goalMin) {
                    return;
                }
                if (type == MAX && value >= goalMax) {
                    return;
                }
            }
        }
    }
}
