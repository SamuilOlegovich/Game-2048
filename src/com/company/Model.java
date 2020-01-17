package com.company;


import com.company.Move;
import com.company.MoveEfficiency;

import java.util.*;


// Он будет ответственен за все манипуляции производимые с игровым полем.
public class Model {
    private static final int FIELD_WIDTH = 8;
    private Stack<Tile[][]> previousStates;              // предыдущие состояния игрового поля
    private Stack<Integer> previousScores;               // предыдущие счета
    boolean isSaveNeeded = true;
    private Tile[][] gameTiles;
    public int maxTile;                                  // максимальное значение плитки
    public int score;                                    // счет - увеличивается каждый раз после слияния плитки



    public Model() {
        this.gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        this.previousStates = new Stack();
        this.previousScores = new Stack();
        resetGameTiles();
        this.maxTile = 0;
        this.score = 0;
    }

    // выбирает лучший из возможных ходов и выполнять его
    public void autoMove() {
        PriorityQueue<MoveEfficiency> priorityQueue =
                new PriorityQueue<>(4, Collections.reverseOrder());

        priorityQueue.offer(getMoveEfficiency(this::right));
        priorityQueue.offer(getMoveEfficiency(this::down));
        priorityQueue.offer(getMoveEfficiency(this::left));
        priorityQueue.offer(getMoveEfficiency(this::up));

        priorityQueue.peek().getMove().move();
    }


    // принимает один параметр типа move, и возвращает объект типа MoveEfficiency
    //      описывающий эффективность переданного
    public MoveEfficiency getMoveEfficiency(Move move) {
        move.move();
        if (!hasBoardChanged()) {
            rollback();
            return new MoveEfficiency(-1, 0, move);
        }

        int emptyTilesCount = getEmptyTiles().size();
        MoveEfficiency moveEfficiency = new MoveEfficiency(emptyTilesCount, score, move);
        rollback();
        return moveEfficiency;
    }


    // будет возвращать true, в случае, если вес плиток в массиве gameTiles отличается от веса плиток
    //      в верхнем массиве стека previousStates. Обрати внимание на то, что мы не должны удалять из
    //      стека верхний элемент, используй метод peek.
    public boolean hasBoardChanged() {
        int gameTilesMaxValue = 0;
        int previousStatesMaxValue = 0;

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                gameTilesMaxValue = gameTilesMaxValue + gameTiles[i][j].value;
            }
        }

        Tile[][] temp = previousStates.peek();

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                previousStatesMaxValue = previousStatesMaxValue + temp[i][j].value;
            }
        }

        return gameTilesMaxValue != previousStatesMaxValue ? true : false;
    }

    public void randomMove() {
        switch (((int) (Math.random() * 100)) % 4) {
            case 0 : left();
                break;
            case 1 : right();
                break;
            case 2 : up();
                break;
            case 3 : down();
        }
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    // будет сохранять текущее
    //    игровое состояние и счет в стеки с помощью метода push
    //    и устанавливать флаг isSaveNeeded равным false
    private void saveState(Tile[][] tiles) {
        Tile[][] tile = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                tile[i][j] = new Tile(tiles[i][j].value);
            }
        }
        Integer integer = score;
        previousScores.push(integer);
        previousStates.push(tile);
        isSaveNeeded = false;
    }

    // будет устанавливать текущее игровое состояние равным последнему
    //    находящемуся в стеках с помощью метода pop.
    public void rollback() {
        if (!previousStates.isEmpty() && !previousScores.isEmpty()) {
            gameTiles = previousStates.pop();
            score = previousScores.pop();
        }
    }

    // возвращающий true в случае, если в текущей позиции возможно сделать ход так,
    //      чтобы состояние игрового поля изменилось. Иначе - false.
    public boolean canMove() {

        for (int i = 0; i < FIELD_WIDTH ; i++) {
            for (int j = 0; j < FIELD_WIDTH ; j++) {
                if (gameTiles[i][j].value == 0) {
                    return true;
                }
                if ((i + 1) < gameTiles.length
                        && gameTiles[i][j].value == gameTiles[i + 1][j].value) {
                    return true;
                }
                if ((j + 1) < gameTiles.length
                        && gameTiles[i][j].value == gameTiles[i][j + 1].value) {
                    return true;
                }
            }
        }
        return false;
    }

    // Метод getEmptyTiles должен возвращать список пустых плиток в массиве gameTiles.
    private List getEmptyTiles() {
        List<Tile> list = new ArrayList<>();

        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                if (gameTiles[i][j].isEmpty()) {
                    list.add(gameTiles[i][j]);
                }
            }
        }
        return list;
    }

    // Метод addTile должен изменять значение случайной пустой плитки в массиве gameTiles
    // на 2 или 4 с вероятностью 0.9 и 0.1 соответственно.
    public void addTile() {
        List<Tile> emptyTiles = getEmptyTiles();

        if(emptyTiles.size() > 0) {
            emptyTiles.get((int) (emptyTiles.size() * Math.random())).value = (Math.random() < 0.9 ? 2 : 4);
        }
    }

    // Метод resetGameTiles должен заполнять массив gameTiles новыми плитками и менять значение
    // двух из них с помощью двух вызовов метода addTile.
    public void resetGameTiles() {
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_WIDTH; j++) {
                this.gameTiles[i][j] = new Tile();
            }
        }
        addTile();
        addTile();
    }

    // Для каждого ряда или столбца, происходят на самом деле две вещи:
    //      а) Сжатие плиток, таким образом, чтобы все пустые плитки были справа,
    //          т.е. ряд {4, 2, 0, 4} становится рядом {4, 2, 4, 0}
    private boolean compressTiles(Tile[] tiles) {
        boolean modification = false;

        for (int i = tiles.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {

                if (tiles[j].isEmpty() && tiles[j].value != tiles[j + 1].value) {
                    tiles[j].value = tiles[j + 1].value;
                    tiles[j + 1].value = 0;
                    modification = true;
                }
            }
        }
        return modification;
    }

    // Для каждого ряда или столбца, происходят на самом деле две вещи:
    //      б) Слияние плиток одного номинала, т.е. ряд {4, 4, 2, 0} становится рядом {8, 2, 0, 0}.
    // Обрати внимание, что ряд {4, 4, 4, 4} превратится в {8, 8, 0, 0}, а {4, 4, 4, 0} в {8, 4, 0, 0}.
    private boolean mergeTiles(Tile[] tiles) {
        boolean modification = false;

        for (int i = 0; i < tiles.length; i++) {

            if (i != tiles.length - 1) {

                if (tiles[i].value == tiles[i + 1].value && tiles[i].value != 0) {
                    tiles[i].value = tiles[i].value + tiles[i + 1].value;
                    score = score + tiles[i].value;
                    tiles[i + 1].value = 0;
                    modification = true;

                    if (tiles[i].value > maxTile) {
                        maxTile = tiles[i].value;
                    }
                    compressTiles(tiles);
                }
            }
        }
        return modification;
    }

    // Реализуем метод left, который будет для каждой строки массива gameTiles вызывать методы
    //      compressTiles и mergeTiles и добавлять одну плитку с помощью метода addTile в том случае,
    //              если это необходимо.
    public void left() {
        if (isSaveNeeded) {
            saveState(gameTiles);
        }
        boolean modification = false;
        Tile[][] mass = gameTiles;

        for (Tile[] tiles : mass) {
            if (compressTiles(tiles) || mergeTiles(tiles)) {
                modification = true;
            }
        }

        if (modification) {
            addTile();
        }
        isSaveNeeded = true;
    }

    public void down(){
        saveState(gameTiles);
        turnArrayToTheRight();
        left();
        turnArrayToTheRight();
        turnArrayToTheRight();
        turnArrayToTheRight();
    }

    public void right(){
        saveState(gameTiles);
        turnArrayToTheRight();
        turnArrayToTheRight();
        left();
        turnArrayToTheRight();
        turnArrayToTheRight();
    }

    public void up(){
        saveState(gameTiles);
        turnArrayToTheRight();
        turnArrayToTheRight();
        turnArrayToTheRight();
        left();
        turnArrayToTheRight();
    }

    void turnArrayToTheRight() {
        Tile[][] tempTile = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < gameTiles.length; i++) {
            for (int j = 0; j < gameTiles.length; j++) {
                tempTile[i][FIELD_WIDTH - 1 - j] = gameTiles[j][i];
            }
        }
        gameTiles = tempTile.clone();
    }
}
