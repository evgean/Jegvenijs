package ru.geekbrain.s1.e8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame implements ActionListener {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameWindow();
            }
        });
    }

    private static final int WIDTH = 506;
    private static final int HEIGHT = 555;

    private final JButton btnExit = new JButton("Exit");
    private final JButton btnNewGame = new JButton("New game");
    private Map map = new Map();

    private GameWindow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLocation(getX() - WIDTH / 2, getY() - HEIGHT / 2);
        setSize(WIDTH, HEIGHT);
        setTitle("Крестики-нолики");

        btnNewGame.addActionListener(this);
        btnExit.addActionListener(this);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(btnNewGame);
        bottomPanel.add(btnExit);
        add(bottomPanel, BorderLayout.SOUTH);
        add(map, BorderLayout.CENTER);

        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if(src == btnNewGame) {
            map.startNewGame(3, 3, 3);
        } else if (src == btnExit) {
            System.exit(0);
        }
    }
}
