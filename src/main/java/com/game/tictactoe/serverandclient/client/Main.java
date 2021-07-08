package com.game.tictactoe.serverandclient.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    static String grid = "";
    static int running = 0;

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

            Thread write = new Thread(() -> {
                while (true) {
                    try {
                        String coordinates = "";
                        coordinates = scanner.nextLine();
                        OutputStream outputStream = socket.getOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                        objectOutputStream.writeObject(coordinates);
                        System.out.println(running);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread read = new Thread(() -> {
                while (true) {
                    try {
                        InputStream inputStream = socket.getInputStream();
                        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                        grid = (String) objectInputStream.readObject();
                        System.out.println(grid);
                        if (grid.equals("x wins!") || grid.equals("o wins!") || grid.equals("draw")) {
                            System.exit(0);
                        }
                        running++;

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

            write.start();
            read.start();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

