package src.java;

// Первое задание – добавить на экран компоновщик-сетку с одним столбцом и 
//добавить над существующей кнопкой следующие компоненты в заданном порядке: JLabel с 
//заголовком «Выберите режим игры», сгруппированные в ButtonGroup
// переключаемые JRadioButton с указанием режимов «Человек против компьютера» и 
//«Человек против человека», JLabel с заголовком «Выберите размеры
// поля», JLabel с заголовком «Установленный размер поля:», JSlider со значениями 3..10, 
//JLabel с заголовком «Выберите длину для победы», JLabel с заголовком «Установленная длина:», 
//JSlider со значениями 3..10.


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;


public class SettingsWindow extends JFrame {
    static public final int WINDOW_HEIGHT = 240;
    static public final int WINDOW_WIDTH = 320;
    static public int sliderSizeValue = 3;
    static public int sliderWinValue = 3;
    static public boolean modeValue = false;

    JLabel labelMode = new JLabel("Выберите режим игры:");
    
    JRadioButton humanVshumanButton = new JRadioButton("Человек против Человека", true);
    JRadioButton humanVsCPU = new JRadioButton("Человек против Машины", false);

    ButtonGroup topButtonGroup = new ButtonGroup();

    JLabel labelSize = new JLabel(String.format("Установите размер поля: %d", sliderSizeValue));
    JSlider sliderSize = new JSlider(3, 10, sliderSizeValue);
    
    JLabel labelWin = new JLabel(String.format("Установите длину для победы: %d", sliderWinValue));
    JSlider sliderWin = new JSlider(3, 10, sliderWinValue);
    
    JPanel grid = new JPanel(new GridLayout(4, 1));
    
    JPanel top = new JPanel(new GridLayout(2,1));
    JPanel topButton = new JPanel(new GridLayout(2, 1));
    JPanel middle = new JPanel(new GridLayout(2,1));
    JPanel bottom = new JPanel(new GridLayout(2,1));

    JButton buttonStart = new JButton("Запуск игры");

    SettingsWindow(GameWindow gameWindow){
        setTitle("Окно настроек");
        setLocation(gameWindow.getLocation().x, gameWindow.getLocation().y);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

    	topButtonGroup.add(humanVshumanButton);
        topButtonGroup.add(humanVsCPU);
        topButton.add(humanVshumanButton);
        topButton.add(humanVsCPU);

        top.add(labelMode);
        top.add(topButton);

        sliderSize.addChangeListener(new ChangeListener() {
            @Override             
            public void stateChanged(ChangeEvent e){
                sliderSizeValue = sliderSize.getValue();
                labelSize.setText(String.format("Установите размер поля: %d", sliderSizeValue));
            }
        });
        middle.add(labelSize);
        middle.add(sliderSize);

        sliderWin.addChangeListener(new ChangeListener() {
            @Override             
            public void stateChanged(ChangeEvent e){
                sliderWinValue = sliderWin.getValue();
                labelWin.setText(String.format("Установите длину для победы: %d", sliderWinValue));
            }
        });
        bottom.add(labelWin);
        bottom.add(sliderWin);

        grid.add(top);
        grid.add(middle);
        grid.add(bottom);
        grid.add(buttonStart);

        buttonStart.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    modeValue = humanVshumanButton.isSelected();
                    gameWindow.startNewGame(modeValue, sliderSizeValue, sliderSizeValue, sliderWinValue);
                    
                    setVisible(false);
                }
            });

        add(grid);
    }
}