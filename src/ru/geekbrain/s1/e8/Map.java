package ru.geekbrain.s1.e8;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Map extends JPanel {

    //Messages
    private static final String DRAW_MSG = "Ничья!";
    private static final String PLAYER_1_WIN_MSG = "Выиграл игрок 1!";
    private static final String PLAYER_2_WIN_MSG = "Выиграл игрок 2!";

    //Field values
    private static final int EMPTY = 0;
    private static final int PLAYER_1 = 1;
    private static final int PLAYER_2 = 2;
    String charImage_1 = "x_1";
    String charImage_2 = "o_1";

    //Design
    private static final int DOTS_MARGIN = 4;

    private enum GameState {UNINITIALIZED, PLAYING, WIN_PLAYER_1, WIN_PLAYER_2, DRAW}

    private GameState gameState = GameState.UNINITIALIZED;

    private final Random rnd = new Random();
    private int[][] field;
    private int fieldSizeX;
    private int fieldSizeY;
    private int winLen;

    private float cellWidth;
    private float cellHeight;

    Map() {
        setBackground(new Color(220, 220, 220));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                mouseRelease(e);
            }
        });
    }

    private void mouseRelease(MouseEvent e) {
        if(gameState != GameState.PLAYING) return;
        int m = (int)(e.getY() / cellHeight);
        int n = (int)(e.getX() / cellWidth);
        if(!isEmptyCell(m, n)) return;
        field[m][n] = PLAYER_1;
        repaint();
        if(checkWin(PLAYER_1)) {
            gameState = GameState.WIN_PLAYER_1;
            return;
        }
        if(isMapFull()) {
            gameState = GameState.DRAW;
            return;
        }
        aiTurn();
        repaint();
        if(checkWin(PLAYER_2)) {
            gameState = GameState.WIN_PLAYER_2;
            return;
        }
        if(isMapFull()) gameState = GameState.DRAW;
    }

    void startNewGame(int fieldSizeX, int fieldSizeY, int winLen) {
        this.fieldSizeX = fieldSizeX;
        this.fieldSizeY = fieldSizeY;
        this.winLen = winLen;
        field = new int[fieldSizeY][fieldSizeX];
        gameState = GameState.PLAYING;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(gameState == GameState.UNINITIALIZED) return;
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        cellWidth = panelWidth / (float) fieldSizeX;
        cellHeight = panelHeight / (float) fieldSizeY;
        drawLines(g, panelWidth, panelHeight);
        try {
            drawField(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(gameState != GameState.PLAYING) showGameOverMsg(g);
    }

    private final Font font = new Font("Times new roman", Font.BOLD, 48);

    private void showGameOverMsg(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 200, getWidth(), 70);
        g.setColor(Color.YELLOW);
        g.setFont(font);
        switch (gameState) {
            case DRAW:
                g.drawString(DRAW_MSG, 180, getHeight() / 2);
                break;
            case WIN_PLAYER_1:
                g.drawString(PLAYER_1_WIN_MSG, 60, getHeight() / 2);
                break;
            case WIN_PLAYER_2:
                g.drawString(PLAYER_2_WIN_MSG, 60, getHeight() / 2);
        }
    }

    private void drawLines(Graphics g, int width, int height) {
        for (int i = 1; i < fieldSizeY; i++) {
            int y = (int)(i * cellHeight);
            g.drawLine(0, y, width, y);
        }
        for (int i = 1; i < fieldSizeX; i++) {
            int x = (int) (i * cellWidth);
            g.drawLine(x, 0, x, height);
        }
    }

    private void drawField(Graphics g) throws IOException {

        Image imagePlayerOne = ImageIO.read(new File(charImage_1 + ".png"));
        Image imagePlayerTwo = ImageIO.read(new File(charImage_2 + ".png"));
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if(isEmptyCell(i, j)) continue;
                if(field[i][j] == PLAYER_1) {
                    g.drawImage(
                            imagePlayerOne,
                            (int)(j * cellWidth) + DOTS_MARGIN,
                            (int)(i * cellHeight) + DOTS_MARGIN,
                            (int)(cellWidth - 2 * DOTS_MARGIN),
                            (int)(cellHeight - 2 * DOTS_MARGIN),
                            null);
//                    g.setColor(Color.BLUE);
                } else if(field[i][j] == PLAYER_2){
                    g.drawImage(
                            imagePlayerTwo,
                            (int)(j * cellWidth) + DOTS_MARGIN,
                            (int)(i * cellHeight) + DOTS_MARGIN,
                            (int)(cellWidth - 2 * DOTS_MARGIN),
                            (int)(cellHeight - 2 * DOTS_MARGIN),
                            null);
//                    g.setColor(Color.RED);
                } else {
                    throw new RuntimeException("Unknown value = " + field[i][j]);
                }
//                g.fillOval(
//                        (int)(j * cellWidth) + DOTS_MARGIN,
//                        (int)(i * cellHeight) + DOTS_MARGIN,
//                        (int)(cellWidth - 2 * DOTS_MARGIN),
//                        (int)(cellHeight - 2 * DOTS_MARGIN)
//                );
            }
        }
    }


    private void aiTurn() {
        if (turnAIWin()) return;
        if (turnHumanWinBlock()) return;
        int m, n;
        do {
            m = rnd.nextInt(fieldSizeY);
            n = rnd.nextInt(fieldSizeX);
        } while (!isEmptyCell(m, n));
        field[m][n] = PLAYER_2;
    }

    private boolean turnAIWin() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (isEmptyCell(i, j)) {
                    field[i][j] = PLAYER_2;
                    if (checkWin(PLAYER_2)) return true;
                    field[i][j] = EMPTY;
                }
            }
        }
        return false;
    }

    private boolean turnHumanWinBlock() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (isEmptyCell(i, j)) {
                    field[i][j] = PLAYER_1;
                    if (checkWin(PLAYER_1)) {
                        field[i][j] = PLAYER_2;
                        return true;
                    }
                    field[i][j] = EMPTY;
                }
            }
        }
        return false;
    }

    private boolean checkWin(int c) {
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (checkLine(i, j, 1, 0, winLen, c)) return true;
                if (checkLine(i, j, 1, 1, winLen, c)) return true;
                if (checkLine(i, j, 0, 1, winLen, c)) return true;
                if (checkLine(i, j, 1, -1, winLen, c)) return true;
            }
        }
        return false;
    }

    private boolean checkLine(int n, int m, int vx, int vy, int len, int c) {
        final int farN = n + (len - 1) * vx;
        final int farM = m + (len - 1) * vy;
        if (!isValidCell(farM, farN)) return false;
        for (int i = 0; i < len; i++) {
            if (field[m + i * vy][n + i * vx] != c) return false;
        }
        return true;
    }

    private boolean isMapFull() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == EMPTY) return false;
            }
        }
        return true;
    }

    private boolean isValidCell(int m, int n) {
        return n >= 0 && n < fieldSizeX && m >= 0 && m < fieldSizeY;
    }

    private boolean isEmptyCell(int m, int n) {
        return field[m][n] == EMPTY;
    }
}
