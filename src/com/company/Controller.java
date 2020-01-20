package com.company;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Controller extends KeyAdapter {
    private static final int WINNING_TILE = 2048;
    private Model model;
    private View view;

    public Controller(Model model) {
        this.model = model;
        this.view = new View(this);
    }

    public Model getModel() {
        return model;
    }

    public Tile[][] getGameTiles() {
        return model.getGameTiles();
    }

    public int getScore() {
        return model.score;
    }

    // который позволит вернуть игровое поле в начальное состояние.
    // Необходимо обнулить счет, установить флаги isGameWon и isGameLost
    // у представления в false и вызывать метод resetGameTiles у модели.
    //
    // Примечание: устанавливай значение полей напрямую, без использования сеттеров.
    public void resetGame() {
        view.isGameLost = false;
        view.isGameWon = false;
        model.resetGameTiles();
        model.score = 0;
    }

    // Логика метода должна быть следующей:
    //
    // 1. Если была нажата клавиша ESC - вызови метод resetGame.
    // 2. Если метод canMove модели возвращает false - установи флаг isGameLost в true.
    // 3. Если оба флага isGameLost и isGameWon равны false - обработай варианты движения:
    // а) для клавиши KeyEvent.VK_LEFT вызови метод left у модели;
    // б) для клавиши KeyEvent.VK_RIGHT вызови метод right у модели;
    // в) для клавиши KeyEvent.VK_UP вызови метод up у модели;
    // г) для клавиши KeyEvent.VK_DOWN вызови метод down у модели.
    // 4. Если поле maxTile у модели стало равно WINNING_TILE, установи флаг isGameWon в true.
    // 5. В самом конце, вызови метод repaint у view.
    //
    // P.S. Для получения кода нажатой клавиши используй метод getKeyCode класса KeyEvent.
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if ((keyEvent.getKeyCode()) == KeyEvent.VK_ESCAPE) {
            resetGame();
        }
        if (!model.canMove()) {
            view.isGameLost = true;
        }
        if (!view.isGameLost && !view.isGameWon) {
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.VK_LEFT :
                    model.left();
                    break;
                case KeyEvent.VK_RIGHT :
                    model.right();
                    break;
                case KeyEvent.VK_UP :
                    model.up();
                    break;
                case KeyEvent.VK_DOWN :
                    model.down();
                    break;
                case KeyEvent.VK_Z :
                    model.rollback();
                    break;
                case KeyEvent.VK_R :
                    model.randomMove();
                    break;
                case KeyEvent.VK_A :
                    model.autoMove();
                    break;
            }
            if (model.maxTile == WINNING_TILE) {
                view.isGameWon = true;
            }
        }
        view.repaint();
    }

    public View getView() {
        return view;
    }
}
