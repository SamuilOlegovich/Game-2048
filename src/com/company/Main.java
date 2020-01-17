package com.company;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Model model = new Model();
        JFrame jFrame = new JFrame();
        Controller controller = new Controller(model);

        jFrame.setTitle("2048");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(900, 1000);
        jFrame.setResizable(false);
        jFrame.add(controller.getView());
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

    }
}