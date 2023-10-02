package src.java.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ConnectWin extends JFrame {
    
    private int WINDOW_HEIGHT = 640;
    private int WINDOW_WIDTH = 720;
    private int WINDOW_POS_X = 300;
    private int WINDOW_POS_Y = 100;
    private String WINDOW_TITLE = "Подключение к серверу";
    private LogHandler logHandler;

    JLabel loginLabel = new JLabel("Введите ваш логин:");
    JTextField loginField = new JTextField();
    JLabel passwordLabel = new JLabel("Введите ваш пароль:");
    JTextField passwordField = new JTextField();
    JLabel serverLabel = new JLabel("Введите адрес сервера и порт для подключения: ");
    JTextField serverField = new JTextField();
    JButton buttonConnect = new JButton("Подключиться");

    JPanel mainGrid = new JPanel(new GridLayout(4,1));
    JPanel loginGrid = new JPanel(new GridLayout(1,2));
    JPanel passwordGrid = new JPanel(new GridLayout(1,2));
    JPanel serverGrid = new JPanel(new GridLayout(1,2));
    ConnectWin(LogHandler logHandler){
        this.logHandler = logHandler;
        //свойства окна
        setTitle(WINDOW_TITLE);
        setBounds(WINDOW_POS_X, WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        loginGrid.add(loginLabel);
        loginGrid.add(loginField);

        passwordGrid.add(passwordLabel);
        passwordGrid.add(passwordField);
        serverGrid.add(serverLabel);
        serverGrid.add(serverField);
        mainGrid.add(loginGrid);
        mainGrid.add(passwordGrid);
        mainGrid.add(serverGrid);
        mainGrid.add(buttonConnect);
        
        add(mainGrid);

        initWindow();


        setVisible(true);
    }

    private void initWindow(){
        buttonConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String login = loginField.getText();
                String server = serverField.getText();
                try {
                    new ChatWin(login, server, logHandler);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                setVisible(false);
            }
        });

    }
}