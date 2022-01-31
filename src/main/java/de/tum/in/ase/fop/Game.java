package de.tum.in.ase.fop;

public class Game {
    private PlayGround playGround;
    private GameTreeNode gameTreeNode;

    public Game() {
        playGround = new PlayGround();
    }

    private void initTree() {
        GameTreeNode.nodeCount = 0;
        gameTreeNode = new GameTreeNodeA(playGround);
        System.out.println("Generate tree... (" + GameTreeNode.nodeCount + " nodes)");
    }

    private void makeMove(int place) {
        playGround = playGround.makeMove(place);
    }

    public void makeBestMove() {
        initTree();
        makeMove(gameTreeNode.getBestMove());
    }

    public boolean isFinished() {
        return !playGround.getMoves().hasNext();
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Game game = new Game();
        for (int i = 0; i < 9; i++) {
            if (!game.isFinished()) {
                game.makeBestMove();
                System.out.println(game.playGround);
            }
            else {
                System.out.println("No more moves!");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("\nEfficiency: " + (end - start) + "ms");
    }
}
