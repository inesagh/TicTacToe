package com.game.tictactoe.serverandclient.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class Server {
    static HashSet<Socket> socketHashSet = new HashSet<>();
    static String grid = "";
    static String[][] xo = new String[3][3];
    static boolean i = true;
    static String playersss = "";

    public static void main(String[] args) {
        Logic logic = new Logic();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                xo[i][j] = "";
            }
        }

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            grid = logic.sendGrid(xo);
            Socket client1 = serverSocket.accept();
            System.out.println("Connected player1");
            Thread player1 = new Thread(new EachPlayerThread(client1, "x"));
            Server.socketHashSet.add(client1);

            Socket client2 = serverSocket.accept();
            System.out.println("Connected player2");
            Thread player2 = new Thread(new EachPlayerThread(client2, "o"));
            Server.socketHashSet.add(client2);

            player1.start();
            player2.start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFromClients(String message) {
        int i = 1;
        try {
            for (Socket socket : socketHashSet) {
                OutputStream outputStream = socket.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(message);
                System.out.println("Player " + i + ":\n" + message);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean sequence(String player){
        if(!playersss.equals(player)){
            playersss = player;
            return true;
        }
        return false;
    }

}


