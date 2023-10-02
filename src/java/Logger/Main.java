package src.java.Logger;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        
        new ConnectWin(new LogHandler("./Logger"));
    }
}