package com.game.tictactoe.serverandclient.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class EachPlayerThread implements Runnable {
    private Socket client;
    private String oOrx;
    private Logic logic = new Logic();

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
//            if(oOrx.equals("o")){
//                Thread.sleep(2000);
//            }
            while (true) {
                try {
//                    synchronized (this){
//                        if(!Server.playersss.equals(oOrx)){
//                            Server.playersss = oOrx;
//                            Server.i = true;
//                        }else{
//                            Server.i = false;
//                        }
//                    }

                    if(Server.sequence(oOrx)) {
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
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
