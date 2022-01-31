package de.tum.in.ase.fop;

import static de.tum.in.ase.fop.Constants.*;

public class GameTreeNodeB extends GameTreeNode {

    public GameTreeNodeB(PlayGround playGround) {
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
            children[move] = new GameTreeNodeB(playGround.makeMove(move));
            if (type == MIN && children[move].value < value || type == MAX && children[move].value > value) {
                value = children[move].value;
                bestMove = move;
                // we won; don't search further
                if (value == type) {
                    return;
                }
            }
        }
    }
}
