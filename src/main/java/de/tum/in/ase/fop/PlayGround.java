package de.tum.in.ase.fop;

import static de.tum.in.ase.fop.Constants.*;

public class PlayGround {
    private final int[] arena;
    private int playerToMove = MIN; // starting player, either MIN or MAX

    public PlayGround() {
        arena = new int[9];
    }

    public PlayGround(PlayGround playGround) {
        arena = playGround.arena.clone();
        playerToMove = playGround.playerToMove;
    }

    public PlayGround makeMove(int place) {
        PlayGround playGround = new PlayGround(this);
        playGround.arena[place] = playerToMove;
        playGround.playerToMove = -playerToMove;
        return playGround;
    }

    public boolean isFree(int place) {
        return (arena[place] == NONE);
    }

    public int getPlayerToMove() {
        return playerToMove;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int row = 0; row < 3; row++) {
            for (int column = 3 * row; column < 3 * row + 3; column++) {
                stringBuilder.append(arena[column] == MIN ? "x" : arena[column] == MAX ? "o" : ".");
            }
            if (row < 2) {
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }

    public PossibleMoves getMoves() {
        return new PossibleMoves(this);
    }

    // 8 possible winning configurations
    private static final int[][] winConfigurations = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, { 0, 4, 8 }, { 2, 4, 6 } };

    // who is either MIN (-1) or MAX (+1)
    private boolean hasWon(int who) {
        LineLoop:
        // check for all 8 win configurations, that all 3 positions on the arena have the either value MIN (-1) or value MAX (+1)
        for (int winConfig = 0; winConfig < 8; ++winConfig) {
            for (int i = 0; i < 3; i++) {
                int position = winConfigurations[winConfig][i];
                if (arena[position] != who) {
                    continue LineLoop;
                }
            }
            return true;
        }
        return false;
    }

    public int winner() {
        if (hasWon(MAX)) {
            return MAX;
        }
        if (hasWon(MIN)) {
            return MIN;
        }
        return NONE;
    }

    private int force(int winConfig, int who) {
        int result = -1;
        int[] currentWinConfig = winConfigurations[winConfig];
        int currentPlace;
        int currentValue;
        int squares = 0;
        for (int i = 0; i < 3; i++) {
            currentPlace = currentWinConfig[i];
            currentValue = arena[currentPlace];
            if (currentValue == NONE) {
                result = currentPlace;
            } else {
                squares += who * currentValue;
            }
        }
        if (squares == 2) {
            return result;
        }
        return -1;
    }

    public int forcedWin(int type) {
        int result;
        for (int winConfig = 0; winConfig < 8; winConfig++) {
            result = force(winConfig, type);
            if (result != -1) {
                return result;
            }
        }
        return -1;
    }
}



