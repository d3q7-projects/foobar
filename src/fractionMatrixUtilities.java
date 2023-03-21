public class fractionMatrixUtilities {
    public static void swapRows(int[][] arr, int source, int destination){
        int[] temp = arr[source];
        arr[source] = arr[destination];
        arr[destination] = temp;
    }

    public static void swapColumns(int[][] arr, int source, int destination){
        int temp;
        for (int i = 0; i < arr.length; i++){
            temp = arr[i][source];
            arr[i][source] = arr[i][destination];
            arr[i][destination] = temp;
        }
    }

    public static fraction[][] multiplyMatrix(fraction[][] fundamentalMatrix, fraction[][] r) {
        fraction[][] multipliedMatrix = new fraction[fundamentalMatrix.length][r[0].length];

        for (int i = 0; i < fundamentalMatrix.length; i++) {
            for (int j = 0; j < r[0].length; j++) {
                multipliedMatrix[i][j] = new fraction(0, 1);
                for (int k = 0; k < fundamentalMatrix[0].length; k++) {
                    multipliedMatrix[i][j] = multipliedMatrix[i][j].add(fundamentalMatrix[i][k].mul(r[k][j]));
                }
            }
        }
        return multipliedMatrix;
    }

    /*usage of the formula:
     *
     * A^-1 = (1/det(A)) * adj(A)
     *
     */
    public static void inverseMatrix(fraction[][] matrix) {
        fraction inverseDeterminant = (new fraction(1,1)).div(getDeterminant(matrix)); //get inverse of the determinant
        fraction[][] adjugateMatrix = getCofactorMatrix(matrix);
        transposeMatrix(adjugateMatrix);
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix.length; j++){
                matrix[i][j] = inverseDeterminant.mul(adjugateMatrix[i][j]);
            }
        }
    }


    private static fraction[][] getMinor(fraction[][] matrix, int row, int column){//minor here isn't the determinant but the matrix itself for convenience
        fraction[][] minor = new fraction[matrix.length - 1][matrix.length - 1];
        int inputLocationI;
        int inputLocationJ;
        for (int i = 0; i < minor.length; i++){
            for (int j = 0; j < minor.length; j++){
                inputLocationI = i;
                inputLocationJ = j;
                if (inputLocationI >= row){
                    inputLocationI++;
                }
                if (inputLocationJ >= column){
                    inputLocationJ++;
                }
                minor[i][j] = matrix[inputLocationI][inputLocationJ];
            }
        }
        return minor;
    }

    private static fraction getDeterminant(fraction[][] matrix){
        if (matrix.length > 2){
            fraction determinant = new fraction(0, 1);
            for (int i = 0; i < matrix.length; i++){
                if (i%2 == 0){
                    determinant = determinant.add(matrix[0][i].mul(getDeterminant(getMinor(matrix, 0, i))));
                }
                else{
                    determinant = determinant.add(matrix[0][i].mul(getDeterminant(getMinor(matrix, 0, i))).mul(-1));
                }
            }
            return determinant;

        }
        else if (matrix.length == 2){
            return (matrix[0][0].mul(matrix[1][1])).subtract(matrix[0][1].mul(matrix[1][0]));
        }
        else if (matrix.length == 1){
            return matrix[0][0];
        }
        else {
            return new fraction(1, 1); //¯\_(ツ)_/¯
        }
    }

    private static fraction[][] getCofactorMatrix(fraction[][] matrix){
        fraction[][] cofactor = new fraction[matrix.length][matrix.length];
        for (int i = 0; i < cofactor.length; i++){
            for (int j = 0; j < cofactor.length; j++){
                cofactor[i][j] = getCofactor(matrix, i, j);
            }
        }
        return cofactor;
    }
    private static fraction getCofactor(fraction[][] matrix, int row, int column){
        if ((row+column) % 2 == 0){
            return getDeterminant(getMinor(matrix,row,column));
        }
        else{
            return getDeterminant(getMinor(matrix,row,column)).mul(-1);
        }
    }

    private static void transposeMatrix(fraction[][] matrix) {
        for (int i  = 0; i < matrix.length; i++){
            for (int j = i+1; j < matrix.length; j++){
                fraction temp = matrix[i][j].copy();
                matrix[i][j] = matrix[j][i].copy();
                matrix[j][i] = temp;
            }
        }
    }
}
