import java.util.Arrays;

public class doomsdayFuel {
    private static int firstStatePosition = 0;

    public static int[] solution(int[][] m){
        if (m[0].length <= 1){
            return new int[]{1, 1};
        }
        System.out.println("Input:");
        for (int[] arr : m){
            for (int num : arr){
                System.out.print(num + " ");
            }
            System.out.println();
        }
        /*
        * The problem can be seen as "Find the probabilities for the terminal states of the given Markov chain"
        * */
        int[][] standardForm = getStandardForm(m);

        System.out.println("standard form:");
        for (int[] arr : standardForm){
            System.out.println(Arrays.toString(arr));
        }


        int numOfAbsorbingStates = getNumOfAbsorbingStates(m); //known to be non-zero

        System.out.println("Num of absorbing states:");
        System.out.println(numOfAbsorbingStates);
        System.out.println("Position of state 0:");
        System.out.println(doomsdayFuel.firstStatePosition-numOfAbsorbingStates);

        /*
        * when using an absorbing markov chain, there is of calculating the terminating probabilities
        * the standard form looks like this:
        *     [ I | 0 ]
        * S = |-------|
        *     [ R | Q ]
        * getting the terminating probabilities involves calculating the matrix S^n as n approaches infinity,
        * there is a formula for calculating it:
        *
        *       [       I       |      0      ]
        * S^∞ = |-----------------------------|
        *       [ ((I-Q)^-1)* R |      0      ]
        *
        * */
        fraction[][] R = getRMatrix(standardForm, numOfAbsorbingStates);
        if (R == null){
            int[] finalProbabilities = new int[numOfAbsorbingStates];
            finalProbabilities[0] = 1;
            return finalProbabilities;
        }

        System.out.println("R:");
        for (fraction[] arr : R){
            for (fraction f : arr){
                System.out.print(f.getNumerator() + "/" + f.getDenominator() + " ");
            }
            System.out.println();
        }


        fraction[][] Q = getQMatrix(standardForm, numOfAbsorbingStates);

        System.out.println("Q:");
        assert Q != null;
        for (fraction[] arr : Q){
            for (fraction f : arr){
                System.out.print(f.getNumerator() + "/" + f.getDenominator() + " ");
            }
            System.out.println();
        }


        fraction[][] sInfinity = getFinalMatrix(Q, R);

        System.out.println("Final:");
        for (fraction[] arr : sInfinity){
            for (fraction f : arr){
                System.out.print(f.getNumerator() + "/" + f.getDenominator() + " ");
            }
            System.out.println();
        }

        //we got our probabilities now
        //we are always starting in state 0 and thus we only care about the first row
        int firstStatePos = doomsdayFuel.firstStatePosition-numOfAbsorbingStates;
        if (firstStatePos < 0){
            return new int[]{1,1};
        }
        int length = sInfinity[firstStatePos].length;
        int[] probabilities = new int[length + 1];
        probabilities[length] = sInfinity[firstStatePos][0].getDenominator();
        for (int i = 1; i < length; i++) {
            probabilities[length] = fraction.smallestCommonDivisor(probabilities[length],
                                                                                sInfinity[0][i].getDenominator());
        }
        for (int i = 0; i < length; i++) {
            probabilities[i] = sInfinity[firstStatePos][i].expandTo(probabilities[length]);
        }

        return probabilities;
    }


    private static int[][] getStandardForm(int[][] m ){
        int numOfAbsorbingStates = 0;
        int[][] standardized = new int[m.length][m.length];
        boolean firstPositionSwapped;
        for(int i = 0; i < m.length; i++)
            standardized[i] = m[i].clone();
        for(int i=0; i<m.length; i++){
            firstPositionSwapped = false;
            fractionMatrixUtilities.swapRows(standardized, i, numOfAbsorbingStates);
            fractionMatrixUtilities.swapColumns(standardized, i, numOfAbsorbingStates);
            if (doomsdayFuel.firstStatePosition == numOfAbsorbingStates){
                doomsdayFuel.firstStatePosition = i;
                firstPositionSwapped = true;
            }
            numOfAbsorbingStates++;
            for(int j=0; j<m[i].length; j++){
                if (i != j && m[i][j] != 0){
                    numOfAbsorbingStates--;
                    if(firstPositionSwapped){
                        doomsdayFuel.firstStatePosition = numOfAbsorbingStates;
                    }
                    fractionMatrixUtilities.swapColumns(standardized, i, numOfAbsorbingStates);
                    fractionMatrixUtilities.swapRows(standardized, i, numOfAbsorbingStates);
                    break;
                }
            }
        }
        for (int i = 0; i< numOfAbsorbingStates; i++){
            standardized[i][i] = 1; //make sure the absorbing states are explicitly terminal
        }
        return standardized;
    }

    //we might note that Q is a square matrix
    private static fraction[][] getFinalMatrix(fraction[][] q, fraction[][] r) {
        fraction[][] fundamentalMatrix = new fraction[q.length][q.length];

        for (int i = 0; i < fundamentalMatrix.length; i++){ // calculate I - Q
            for (int j = 0; j < fundamentalMatrix.length; j++){
                if (i == j){
                    fundamentalMatrix[i][i] = (new fraction(1,1)).add(q[i][i].mul(-1));
                }
                else{
                    fundamentalMatrix[i][j] = q[i][j].mul(-1);
                }
            }
        }

        fractionMatrixUtilities.inverseMatrix(fundamentalMatrix); // (I-Q)^-1

        return fractionMatrixUtilities.multiplyMatrix(fundamentalMatrix, r);
    }


    private static fraction[][] getQMatrix(int[][] standardForm, int numOfAbsorbingStates) {
        if (standardForm.length == numOfAbsorbingStates){
            return null;
        }
        fraction[][] Q = new fraction[standardForm.length - numOfAbsorbingStates][standardForm.length - numOfAbsorbingStates];
        fraction temp = new fraction(0,1);
        for (int i = 0; i < Q.length; i++) {
            temp.setDenominator(standardForm[numOfAbsorbingStates+i][0]);
            for (int j = 1; j < standardForm.length; j++) {
                temp.setDenominator(temp.getDenominator() + standardForm[numOfAbsorbingStates+i][j]);
            }
            for (int j = numOfAbsorbingStates; j<standardForm.length; j++){
                temp.setNumerator(standardForm[numOfAbsorbingStates+i][j]);
                Q[i][j-numOfAbsorbingStates] = temp.copy();
            }
        }
        return Q;
    }

    private static fraction[][] getRMatrix(int[][] standardForm, int numOfAbsorbingStates) {
        if (standardForm.length == numOfAbsorbingStates){
            return null;
        }
        fraction[][] R = new fraction[standardForm.length - numOfAbsorbingStates][numOfAbsorbingStates];
        fraction temp = new fraction(0,1);
        for (int i = 0; i < R.length; i++) {
            temp.setDenominator(standardForm[numOfAbsorbingStates+i][0]);
            for (int j = 1; j < standardForm.length; j++) {
                temp.setDenominator(temp.getDenominator() + standardForm[numOfAbsorbingStates+i][j]);
            }
            for (int j = 0; j<numOfAbsorbingStates; j++){
                temp.setNumerator(standardForm[numOfAbsorbingStates+i][j]);
                R[i][j] = temp.copy();
            }
        }

        return R;
    }

    private static int getNumOfAbsorbingStates(int[][] m) {
        int numOfAbsorbingStates = 0;
        for(int i=0; i<m.length; i++){
            numOfAbsorbingStates++;
            for(int j=0; j<m[i].length; j++){
                if (i != j && m[i][j] != 0){
                    numOfAbsorbingStates--;
                    break;

                }
            }
        }
        return numOfAbsorbingStates;
    }
}
