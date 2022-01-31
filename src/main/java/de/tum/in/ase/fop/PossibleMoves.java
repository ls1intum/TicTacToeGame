package de.tum.in.ase.fop;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static de.tum.in.ase.fop.Constants.NONE;

class PossibleMoves implements Iterator<Integer> {
    private final PlayGround playGround;
    private int next = 0;
    public PossibleMoves(PlayGround playGround) {
        this.playGround = playGround;
    }

    public boolean hasNext() {
        if (playGround.winner() != NONE) {
            return false;
        }
        for (; next < 9; next++) {
            if (playGround.isFree(next)) {
                return true;
            }
        }
        return false;
    }

    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return next++;
    }
}
