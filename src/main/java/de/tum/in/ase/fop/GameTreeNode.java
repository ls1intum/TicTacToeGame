package de.tum.in.ase.fop;

public abstract class GameTreeNode {

    public static int nodeCount = 0;

    protected int value;
    protected int type; // either MIN (-1) or MAX (1)
    protected int bestMove = -1;

    protected PlayGround playGround;
    protected final GameTreeNode[] children = new GameTreeNode[9];

    public int getBestMove() {
        return bestMove;
    }

    public GameTreeNode(PlayGround playGround) {
        nodeCount++;
        this.playGround = playGround;
        type = playGround.getPlayerToMove();
    }
}
