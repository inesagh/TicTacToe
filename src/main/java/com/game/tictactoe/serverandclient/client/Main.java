package com.game.tictactoe.serverandclient.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    static String grid = "";
    static int running = 0;
    static boolean quit = false;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            Scanner scanner = new Scanner(System.in);

            Thread firstGrid = new Thread(() -> {
                try {
                    InputStream inputStream = socket.getInputStream();
                    ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                    String emptyGrid = (String) objectInputStream.readObject();
                    System.out.println(emptyGrid);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            firstGrid.start();
            firstGrid.join();


            while (!quit) {
                String coordinates = "";
                coordinates = scanner.nextLine();
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(coordinates);
                System.out.println(running);

                InputStream inputStream = socket.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                grid = (String) objectInputStream.readObject();
                System.out.println(grid);
                if (grid.equals("x wins!") || grid.equals("o wins!") || grid.equals("draw")) {
                    quit = true;
//                    System.exit(0);
                }

            }


        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

