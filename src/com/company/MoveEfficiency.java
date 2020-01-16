package com.company;


// описывает эффективность хода
public class MoveEfficiency implements Comparable<MoveEfficiency> {
    private int numberOfEmptyTiles;
    private int score;
    private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public int compareTo(MoveEfficiency moveEfficiency) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if (this == moveEfficiency) {
            return EQUAL;
        }

        if (this.numberOfEmptyTiles > moveEfficiency.numberOfEmptyTiles) {
            return AFTER;
        }

        if (this.numberOfEmptyTiles < moveEfficiency.numberOfEmptyTiles) {
            return BEFORE;
        }

        if (this.numberOfEmptyTiles == moveEfficiency.numberOfEmptyTiles) {
            if (this.score > moveEfficiency.score) return AFTER;
            if (this.score < moveEfficiency.score) return BEFORE;
        }

        return EQUAL;
    }
}

