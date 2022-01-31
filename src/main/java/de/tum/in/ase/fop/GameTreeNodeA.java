package de.tum.in.ase.fop;

public class GameTreeNodeA extends GameTreeNode {
    public GameTreeNodeA(PlayGround playGround) {
        super(playGround);

        // have I lost already?
        if (playGround.winner() != Constants.NONE) {
            value = playGround.winner();
            return;
        }

        // no more moves --> no winner
        var moves = playGround.getMoves();
        if (!moves.hasNext()) {
            value = Constants.DRAW;
            return;
        }

        value = -2 * type;
        while (moves.hasNext()) {
            int move = moves.next();
            children[move] = new GameTreeNodeA(playGround.makeMove(move));
            if (type == Constants.MIN && children[move].value < value || type == Constants.MAX && children[move].value > value) {
                value = children[move].value;
                bestMove = move;
            }
        }
    }
}
