package de.tum.in.ase.fop;

public class GameTreeNodeD extends GameTreeNode {

    public GameTreeNodeD(PlayGround playGround) {
        this(playGround, Constants.MIN, Constants.MAX);
    }

    private GameTreeNodeD(PlayGround playGround, int goalMin, int goalMax) {
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

        int move = playGround.forcedWin(type);
        if (move != -1) {
            bestMove = move;
            value = type;
            return;
        }
        move = playGround.forcedWin(-type);
        if (move != -1) {
            bestMove = move;
            children[move] = new GameTreeNodeD(playGround.makeMove(move), goalMin, goalMax);
            value = children[move].value;
            return;
        }
        value = -2 * type;
        while (moves.hasNext()) {
            move = moves.next();
            children[move] = new GameTreeNodeD(playGround.makeMove(move), goalMin, goalMax);

            if (type == Constants.MIN && children[move].value < value || type == Constants.MAX && children[move].value > value) {
                value = children[move].value;
                bestMove = move;
                // update goals
                if (type == Constants.MIN && goalMax > value) {
                    goalMax = value;
                }
                if (type == Constants.MAX && goalMin < value) {
                    goalMin = value;
                }
                // leave if goal is reached
                if (goalMin >= goalMax) {
                    return;
                }
            }
        }
    }

}
