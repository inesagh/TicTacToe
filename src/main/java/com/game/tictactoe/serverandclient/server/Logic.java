package com.game.tictactoe.serverandclient.server;


import java.util.Arrays;

import static com.game.tictactoe.serverandclient.server.Server.xo;

public class Logic {
    private String[][] grid = new String[3][3];

    public String sendGrid(String[][] xo){
        String grid = Arrays.deepToString(xo[0]) + "\n" +
                Arrays.deepToString(xo[1]) + "\n" +
                Arrays.deepToString(xo[2]) + "\n";
        return grid;
    }

    public String[][] modify(int row, int column, String xOro){
        if(xo[row][column].equals("")) {
            xo[row][column] = xOro;
        }
        return xo;
    }

    public String check(String[][] grid){
        if(!grid[0][0].equals("") && ((grid[0][0].equals(grid[0][1]) && grid[0][1].equals(grid[0][2])) ||
                                    (grid[0][0].equals(grid[1][1]) && grid[0][0].equals(grid[2][2])) ||
                                    (grid[0][0].equals(grid[1][0]) && grid[0][0].equals(grid[2][0])))){
            return grid[0][0] + " wins!";
        }else if(!grid[0][2].equals("") && ((grid[0][2].equals(grid[1][1]) && grid[0][2].equals(grid[2][0])) ||
                                            (grid[0][2].equals(grid[1][2]) && grid[0][2].equals(grid[2][2])))){
            return grid[0][2] + " wins!";
        }else if(!grid[1][1].equals("") && ((grid[0][1].equals(grid[1][1]) && grid[1][1].equals(grid[2][1])) ||
                                            (grid[1][0].equals(grid[1][1]) && grid[1][1].equals(grid[1][2])))){
            return grid[1][1] + " wins!";
        }else if(!grid[2][2].equals("") && (grid[2][0].equals(grid[2][1]) && grid[2][1].equals(grid[2][2]))){
            return grid[2][2] + " wins!";
        }
        else{
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if(grid[i][j].equals("")) return "";
                }
            }
            return "draw";
        }
    }
}
