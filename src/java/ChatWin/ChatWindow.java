package src.java.ChatWin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatWindow extends JFrame {
    static private final int WINDOW_HEIGHT = 640;
    static private final int WINDOW_WIDTH = 720;
    static private final int WINDOW_POS_X = 300;
    static private final int WINDOW_POS_Y = 100;
    static private final String WINDOW_NAME = "Чат с сервером";



    JTextArea textOutput = new JTextArea("");
    JLabel label = new JLabel("Введите сообщение серверу: ");
    JTextField textInput = new JTextField(); 

    JButton buttonConnect = new JButton("Отправить");
    
    JPanel grid = new JPanel(new GridLayout(4,1));

    ChatWindow(String login){
        //свойства окна
        setTitle(WINDOW_NAME);
        setBounds(WINDOW_POS_X, WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        textOutput.setEditable(false);
        textOutput.setBackground(Color.GRAY);
        grid.add(textOutput);
        grid.add(label);
        grid.add(textInput);
        grid.add(buttonConnect);

        buttonConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                textOutput.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss")).toString() + " " + login + " : " + textInput.getText() + "\n");
            }
        });

        add(grid);
        setVisible(true);
    }
}