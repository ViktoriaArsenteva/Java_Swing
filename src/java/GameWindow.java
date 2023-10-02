package src.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {
        static public final int WINDOW_HEIGHT = 640;
        static public final int WINDOW_WIDTH = 720;
        static public final int WINDOW_POS_X = 300;
        static public final int WINDOW_POS_Y = 100;
        static public final String WINDOW_NAME = "Игра в крестики-нолики";
        SettingsWindow settingsWindow;
        Map gameMap;
        GameWindow(){
            //свойства окна
            setBounds(WINDOW_POS_X, WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setTitle(WINDOW_NAME);
            setResizable(false);

            //элементы основная часть
            settingsWindow = new SettingsWindow(this);
            gameMap = new Map(settingsWindow.sliderSizeValue, settingsWindow.sliderSizeValue);
            //элементы нижняя часть
            JButton startButton = new JButton("Start");
            JButton stopButton = new JButton("Exit");
            JPanel controPanel = new JPanel(new GridLayout(1,2));
            
            //обработчики кнопок
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    settingsWindow.setVisible(true);
                }
            });


            stopButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    System.exit(0);
                }
            });
            
            //отрисовка
            controPanel.add(startButton);
            controPanel.add(stopButton);
            add(controPanel, BorderLayout.PAGE_END);
            add(gameMap);
            gameMap.setVisible(false);
            setVisible(true);
            
        }
        void startNewGame(boolean mode, int size_x, int size_y, int win_len){
            gameMap.setVisible(true);
            gameMap.startNewGame(mode, size_x, size_y, win_len);
        }
}