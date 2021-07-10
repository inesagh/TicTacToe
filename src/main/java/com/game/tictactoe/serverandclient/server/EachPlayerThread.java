package com.game.tictactoe.serverandclient.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class EachPlayerThread implements Runnable {
    private Socket client;
    private String oOrx;
    private Logic logic = new Logic();
    static AtomicReference<String> turn = new AtomicReference<>();

    public EachPlayerThread(Socket client, String oOrx) {
        this.client = client;
        this.oOrx = oOrx;
    }

    @Override
    public void run() {
        try {
            OutputStream outputStream = client.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(Server.grid);

                try {
                    while (true) {

                        if (!oOrx.equals(turn.get())) {
                            turn.set(oOrx);
                            System.out.println("aaaaa");
                            InputStream inputStream = client.getInputStream();
                            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                            String coordinates = (String) objectInputStream.readObject();
                            System.out.println(coordinates);

                            int rowFromClient = Integer.parseInt(coordinates.split("")[0]);
                            int columnFromClient = Integer.parseInt(coordinates.split("")[1]);

                            Server.xo = logic.modify(rowFromClient, columnFromClient, oOrx);
                            Server.grid = logic.sendGrid(Server.xo);


                            Server.readFromClients(Server.grid);
                        }
                        if (logic.check(Server.xo).equals("x wins!") || logic.check(Server.xo).equals("o wins!")
                                || logic.check(Server.xo).equals("draw")) {
                            Server.grid = logic.check(Server.xo);
                            Server.readFromClients(Server.grid);
                            break;
                        }

                    }
                } catch (SocketException e) {
                    System.exit(0);
                }



        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
