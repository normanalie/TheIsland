package org.example.GUI.mainGame;

public class Matrice {

    public int[][] matrix;

    public Matrice(int size) {
        matrix = new int[size][size];
        initializeMatrix();
    }

    private void initializeMatrix() {
        int plage = 1;
        int montagne = 2;
        int foret = 3;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 0;
            }
        }
        matrix[3][3] = 2 ;
        matrix[3][4] = 2 ;
        matrix[3][5] = 1 ;
        matrix[3][6] = 3 ;
        matrix[4][3] = 1 ;
        matrix[4][4] = 3 ;
        matrix[4][5] = 2 ;
        matrix[4][6] = 2 ;
        matrix[4][7] = 1 ;
        matrix[5][2] = 1 ;
        matrix[5][3] = 3 ;
        matrix[5][4] = 2 ;
        matrix[5][5] = 1 ;
        matrix[5][6] = 1 ;
        matrix[5][7] = 3 ;
        matrix[5][8] = 1 ;
        matrix[5][9] = 2 ;
        matrix[6][2] = 1 ;
        matrix[6][3] = 1 ;
        matrix[6][4] = 2 ;
        matrix[6][6] = 2 ;
        matrix[6][7] = 2 ;
        matrix[6][8] = 1 ;
        matrix[7][2] = 3 ;
        matrix[7][3] = 2 ;
        matrix[7][4] = 2 ;
        matrix[7][5] = 1 ;
        matrix[7][6] = 1 ;
        matrix[7][7] = 1 ;
        matrix[7][8] = 3 ;
        matrix[7][9] = 2 ;
        matrix[8][3] = 1 ;
        matrix[8][4] = 3 ;
        matrix[8][5] = 2 ;
        matrix[8][6] = 2 ;
        matrix[8][7] = 1 ;
        matrix[9][3] = 2 ;
        matrix[9][4] = 1;
        matrix[9][5] = 2 ;
        matrix[9][6] = 3 ;











    }

    public void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }


}
