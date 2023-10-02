package src.java;

import javax.swing.*;


import java.awt.*;

import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;



public class Map extends JPanel{
    private static final Random RANDOM = new Random();
    private static final int DOT_PADDING = 5;

    private final int HUMAN_DOT = 1;
    private final int AI_DOT = 2;
    private final int EMPTY_DOT = 0;
    private int fieldSizeY = 3;
    private int fieldSizeX = 3;
    private int winCount = 3;
    private char [][] field;

    private int paneWidth;
    private int paneHeight;
    private int cellHeight = 200;
    private int cellWidth = 200;

    private int gameOverType;
    private static final int STATE_DRAW = 0;
    private static final int STATE_WIN_HUMAN = 1;
    private static final int STATE_WIN_AI = 2;

    private static final String MSG_WIN_HUMAN = "Победил игрок!";
    private static final String MSG_WIN_AI = "Победил компьютер!";
    private static final String MSG_DRAW = "Ничья!";

    private boolean isGameOver;
    private boolean isInitialized;

    public int sizeX;
    public int sizeY;

    Map(int sizeX, int sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    Map(){
        
        isInitialized = false;
        
        addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e){
                update(e); 
                
            }
        });

    }


    private void update(MouseEvent e) {
        if (isGameOver || !isInitialized) return;

        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeight;
        if (!isValideCell(cellX, cellY) || !isEmptyCell(cellX, cellY)) return;
        field[cellY][cellX] = HUMAN_DOT;
        

        if (checkEndGame(HUMAN_DOT, STATE_WIN_HUMAN)) return;
        aiTurn();
        repaint();
        if (checkEndGame(HUMAN_DOT, STATE_WIN_AI)) return;

        repaint();
    }

    private boolean checkEndGame(int dot, int gameOverType) {
        if (checkWin(dot, winCount, fieldSizeX, fieldSizeY)) {
            this.gameOverType = gameOverType; 
            isGameOver = true;
            repaint();
            return true;
        }
        if (isMapFull()) {
            this.gameOverType = STATE_DRAW;
            isGameOver = true;
            repaint();
            return true;
        }
        return false;
    }

    void startNewGame(boolean mode, int fSzX,int fSzY, int winLen) {
        System.out.printf("Mode: %d; \nSize: x=%d, y=%d;\nWin Length: %d", mode, fSzX, fSzY, winLen);
        initMap();
        isGameOver = false;
        isInitialized = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        render(g);
    }

/*
 * Метод для отрисовки поля игры
 */
    private void render(Graphics g){
        if (!isInitialized) return;

        paneWidth = getWidth();
        paneHeight = getHeight();
        cellHeight = paneHeight / 3;
        cellWidth = paneWidth / 3;


        g.setColor(Color.BLACK);
        for (int h = 0; h < 3; h++){
            int y = h * cellHeight;
            g.drawLine(0, y, paneWidth, y);
        }
        for (int w = 0; w < 3; w++){
            int x = w * cellWidth;
            g.drawLine(x, 0, x, paneHeight);
        }

        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == EMPTY_DOT) continue;

                if (field[y][x] == HUMAN_DOT) {
                    g.setColor(Color.BLUE);
                    g.fillOval(x * cellWidth + DOT_PADDING,
                                y * cellHeight + DOT_PADDING,
                                cellWidth - DOT_PADDING * 2,
                                cellHeight - DOT_PADDING * 2);

                } else if (field[y][x] == AI_DOT) {
                    g.setColor(Color.RED);
                    g.fillOval(x * cellWidth + DOT_PADDING,
                                y * cellHeight + DOT_PADDING,
                                cellWidth - DOT_PADDING * 2,
                                cellHeight - DOT_PADDING * 2);

                } else {
                    throw new RuntimeException("unexpected value " + field[y][x] + " in cell: x=" + x + " y=" + y);
                }
            }
        }
        if (isGameOver) showMessageGameOver(g);

    }

    private void showMessageGameOver (Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 200, getWidth(), 70);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Times new roman", Font.BOLD, 48));
        switch (gameOverType) {
            case STATE_DRAW:
                g.drawString(MSG_DRAW, 180, getHeight()/2); break;
            case STATE_WIN_AI:
                g.drawString(MSG_WIN_AI, 20, getHeight()/2); break;
            case STATE_WIN_HUMAN:
                g.drawString(MSG_WIN_HUMAN, 70, getHeight()/2); break;
            default:
                throw new RuntimeException("Unexpected gameOver state: " + gameOverType);
        }
    }
/**
 * Логика игры
 */

    private void initMap(){
        fieldSizeY = 3;
        fieldSizeX = 3;
        field = new char[fieldSizeY][fieldSizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX;j++){
                field[i][j] = EMPTY_DOT;
            }
        }
    }

    private boolean isValideCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    private boolean isEmptyCell(int x, int y) {
        return field[y][x] == EMPTY_DOT;
    }

    private void aiTurn() {
        int x, y;
        boolean check = false;
        for (int i = 0; i < fieldSizeX; i++){
            if (check == true){break;}
            for (int j = 0; j < fieldSizeY; j++){
                if (isEmptyCell(i, j)){
                    field[i][j] = AI_DOT;
                    if (checkWin(AI_DOT,winCount,fieldSizeX,fieldSizeY)){
                        check = true;
                        break;
                    }
                    else {
                        field[i][j] = EMPTY_DOT;
                    }
                    field[i][j] = HUMAN_DOT;
                    if (checkWin(HUMAN_DOT,winCount,fieldSizeX,fieldSizeY)){
                        field[i][j] = AI_DOT;
                        check = true;
                        break;
                    }
                    else{
                        field[i][j] = EMPTY_DOT;
                    }
                }

            }
        }
        if (check == false) {
            do
            {
                x = RANDOM.nextInt(fieldSizeX);
                y = RANDOM.nextInt(fieldSizeY);
            }
            while (!isEmptyCell(x, y));
            field[x][y] = AI_DOT;
        
        }
    }

    private boolean checkWin(int c, int winCount, int fieldSizeX, int fieldSizeY ) {
        for (int x = 0; x < fieldSizeX; x++){
            for (int y = 0; y < fieldSizeY; y++){
                if (!isEmptyCell(x, y) && field[x][y] == c){
                    if (x + winCount <= fieldSizeX){
                        for (int i = 1; i <= winCount; i++){
                            if (i == winCount){
                                return true;
                            }
                            else if (field[x + i][y] == c){
                                continue;
                            }
                            else{
                                break;
                            }
    
                        }
                    }
                    if (y + winCount <= fieldSizeY){
                        for (int i = 1; i <= winCount; i++){
                            if (i == winCount){
                                return true;
                            }
                            else if (field[x][y + i] == c){
                                continue;
                            }
                            else{
                                break;
                            }
                        }
                    }
                    if ((x + winCount <= fieldSizeX) && (y + winCount <= fieldSizeY)){
                        for (int i = 1; i <= winCount; i++){
                            if (i == winCount){
                                return true;
                            }
                            else if (field[x + i][y + i] == c){
                                continue;
                            }
                            else{
                                break;
                            }
                        }
                    }
                    if ((x + winCount <= fieldSizeX) && (y - winCount + 1 >= 0)){
                        for (int i = 1; i <= winCount; i++){
                            if (i == winCount){
                                return true;
                            }
                            else if (field[x + i][y - i] == c){
                                continue;
                            }
                            else{
                                break;
                            }
                        }
                    
                    }
                }
                
            }
        }

        return false;

    }

    private boolean isMapFull() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++){
                if (field[i][j] == EMPTY_DOT) return false;
            }
        }
        return true;
    }
   
    
}
