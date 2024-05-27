package org.example.GUI.mainGame;

import org.example.Logic.Model.TileType;

public class Matrice {
    public TileType[][] matrix;

    public Matrice(int size) {
        matrix = new TileType[size][size];
        initializeMatrix();
    }

    private void initializeMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = TileType.NONE;
            }
        }
        matrix[3][3] = TileType.MOUNTAIN;
        matrix[3][4] = TileType.MOUNTAIN;
        matrix[3][5] = TileType.LAND;
        matrix[3][6] = TileType.FOREST;
        matrix[4][3] = TileType.LAND;
        matrix[4][4] = TileType.FOREST;
        matrix[4][5] = TileType.MOUNTAIN;
        matrix[4][6] = TileType.MOUNTAIN;
        matrix[4][7] = TileType.LAND;
        matrix[5][2] = TileType.LAND;
        matrix[5][3] = TileType.FOREST;
        matrix[5][4] = TileType.MOUNTAIN;
        matrix[5][5] = TileType.LAND;
        matrix[5][6] = TileType.LAND;
        matrix[5][7] = TileType.FOREST;
        matrix[5][8] = TileType.LAND;
        matrix[5][9] = TileType.MOUNTAIN;
        matrix[6][2] = TileType.LAND;
        matrix[6][3] = TileType.LAND;
        matrix[6][4] = TileType.MOUNTAIN;
        matrix[6][6] = TileType.MOUNTAIN;
        matrix[6][7] = TileType.MOUNTAIN;
        matrix[6][8] = TileType.LAND;
        matrix[7][2] = TileType.FOREST;
        matrix[7][3] = TileType.MOUNTAIN;
        matrix[7][4] = TileType.MOUNTAIN;
        matrix[7][5] = TileType.LAND;
        matrix[7][6] = TileType.LAND;
        matrix[7][7] = TileType.LAND;
        matrix[7][8] = TileType.FOREST;
        matrix[7][9] = TileType.MOUNTAIN;
        matrix[8][3] = TileType.LAND;
        matrix[8][4] = TileType.FOREST;
        matrix[8][5] = TileType.MOUNTAIN;
        matrix[8][6] = TileType.MOUNTAIN;
        matrix[8][7] = TileType.LAND;
        matrix[9][3] = TileType.MOUNTAIN;
        matrix[9][4] = TileType.LAND;
        matrix[9][5] = TileType.MOUNTAIN;
        matrix[9][6] = TileType.FOREST;
    }

    public void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public TileType getHexagonType(TileType matrixValue) {
        switch (matrixValue) {
            case LAND:
                return TileType.LAND;
            case FOREST:
                return TileType.FOREST;
            case MOUNTAIN:
                return TileType.MOUNTAIN;
            default:
                return TileType.NONE;
        }
    }
}
