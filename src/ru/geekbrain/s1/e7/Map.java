package ru.geekbrain.s1.e7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Map extends JPanel{

    String info;

    Map() {
        setBackground(new Color(210, 210, 210));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mouseRelease(e);
            }
        });
    }
    public void mouseRelease(MouseEvent e) {
        if (e.getX() < 201 && e.getY() < 170) System.out.println("Box 1");
        else if (e.getX() > 201 && e.getY() < 170 && e.getX() < 400) System.out.println("Box 2");
        else if (e.getX() > 400 && e.getY() < 170 && e.getX() < 600) System.out.println("Box 3");
        else if (e.getX() < 201 && e.getY() > 170 && e.getY() < 336) System.out.println("Box 4");
        else if (e.getX() > 201 && e.getY() > 170 && e.getY() < 336 && e.getX() < 400) System.out.println("Box 5");
        else if (e.getX() > 201 && e.getY() > 170 && e.getY() < 336 && e.getX() < 600) System.out.println("Box 6");
        else if (e.getX() < 201 && e.getY() > 336 && e.getY() < 506) System.out.println("Box 7");
        else if (e.getX() > 201 && e.getY() > 336 && e.getY() < 506 && e.getX() < 400) System.out.println("Box 8");
        else if (e.getX() > 201 && e.getY() > 336 && e.getY() < 506 && e.getX() < 600) System.out.println("Box 9");
    }

    public void startNewGame() {
        System.out.println("click");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.drawLine(200, 0, 200, 600);
        g.drawLine(400, 0, 400, 600);
        g.drawLine(0, 169, 600, 169);
        g.drawLine(0, 336, 600, 336);
    }
}
