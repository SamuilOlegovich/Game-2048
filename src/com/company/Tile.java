package com.company;


import java.awt.*;


//Создадим класс Tile описывающий одну плитку.
public class Tile {
    int value;

    // Конструктор без параметров (значение поля value должно быть равно нулю).
    public Tile() { this.value = 0; }

    // Конструктор с параметром, инициализирующий поле value.
    public Tile(int value) { this.value = value; }

    // Метод isEmpty, возвращающий true в случае, если значение поля value равно 0, иначе - false.
    public boolean isEmpty() {
// return this.value == 0 ? true : false;
        if (value == 0) return true;
        else return false;
    }

    // Метод getFontColor, возвращающий новый цвет(объект типа Color) (0x776e65) в случае,
//      если вес плитки меньше 16, иначе - 0xf9f6f2.
    public Color getFontColor() {
// value < 16 ? new Color(0x776e65) : new Color(0xf9f6f2);
        if (value < 16) return new Color(0x776e65);
        else return new Color(0xf9f6f2);
    }

    // Метод getTileColor, возвращающий цвет плитки в зависимости от ее веса
//      в соответствии с нижеприведенными значениями:
    Color getTileColor() {
        Color color = null;
        switch (this.value) {
            case 0:
                color = new Color(0xcdc1b4);
                break;
            case 2:
                color = new Color(0xeee4da);
                break;
            case 4:
                color = new Color(0xede0c8);
                break;
            case 8:
                color = new Color(0xf2b179);
                break;
            case 16:
                color = new Color(0xf59563);
                break;
            case 32:
                color = new Color(0xf67c5f);
                break;
            case 64:
                color = new Color(0xf65e3b);
                break;
            case 128:
                color = new Color(0xedcf72);
                break;
            case 256:
                color = new Color(0xedcc61);
                break;
            case 512:
                color = new Color(0xedc850);
                break;
            case 1024:
                color = new Color(0xedc53f);
                break;
            case 2048:
                color = new Color(0xedc22e);
                break;
            default:
                color = new Color(0xff0000);
                break;
        }
        return color;
    }
}
