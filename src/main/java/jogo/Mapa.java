package jogo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Mapa {
    public static final int COLS = 20;
    public static final int ROWS = 14;
    public static final int CELL_SIZE = 52;

    // 0 = grass (tower placement), 1 = path, 2 = base
    private int[][] grid;
    private List<Point> caminho; // ordered path cells

    public Mapa() {
        grid = new int[ROWS][COLS];
        caminho = new ArrayList<>();
        buildMap();
    }

    private void buildMap() {
        int[][] pathCoords = {
                {0,0},{1,0},{2,0},{3,0},{4,0},{5,0},{6,0},
                {6,1},{6,2},{6,3},{6,4},{6,5},{6,6},
                {5,6},{4,6},{3,6},{2,6},{1,6},{0,6},
                {0,7},{0,8},{0,9},{0,10},{0,11},
                {1,11},{2,11},{3,11},{4,11},{5,11},{6,11},{7,11},{8,11},
                {8,10},{8,9},{8,8},{8,7},{8,6},
                {9,6},{10,6},{11,6},{12,6},{13,6},
                {13,7},{13,8},{13,9},{13,10},{13,11},
                {14,11},{15,11},{16,11},{17,11},{18,11},{19,11}
        };

        for (int[] coord : pathCoords) {
            int row = coord[0];
            int col = coord[1];
            if (row < ROWS && col < COLS) {
                grid[row][col] = 1;
                caminho.add(new Point(col, row));
            }
        }
        Point last = caminho.get(caminho.size() - 1);
        grid[last.y][last.x] = 2;
    }

    public boolean isPosicaoValida(int col, int row) {
        if (col < 0 || col >= COLS || row < 0 || row >= ROWS) return false;
        return grid[row][col] == 0;
    }

    public int[][] getGrid() { return grid; }
    public List<Point> getCaminho() { return caminho; }

    public int getPixelWidth() { return COLS * CELL_SIZE; }
    public int getPixelHeight() { return ROWS * CELL_SIZE; }
}