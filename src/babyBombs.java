import java.math.BigInteger;

/**
  * babyBombs
  */
 public class babyBombs {
    public static String solution(String x, String y) {
        BigInteger M = new BigInteger(x);
        BigInteger F = new BigInteger(y);
        BigInteger N = BigInteger.valueOf(0);
        BigInteger NumberOfRepetitions;
        while (M.compareTo(F) != 0) {
            if (M.compareTo(F) > 0) {
                //divide instead of just repeating the same multiplication
                NumberOfRepetitions = M.divide(F);
                if (M.mod(F).compareTo(BigInteger.valueOf(0)) == 0) { //we don't want F or M to be 0
                    NumberOfRepetitions=NumberOfRepetitions.subtract(BigInteger.valueOf(1));
                }
                N=N.add(NumberOfRepetitions);
                M = M.subtract(F.multiply(NumberOfRepetitions));
            }
            else{
                NumberOfRepetitions = F.divide(M);
                if (F.mod(M).compareTo(BigInteger.valueOf(0)) == 0) {
                    NumberOfRepetitions=NumberOfRepetitions.subtract(BigInteger.valueOf(1));
                }
                N=N.add(NumberOfRepetitions);
                F = F.subtract(M.multiply(NumberOfRepetitions));
            }
        }
        if (M.compareTo(BigInteger.valueOf(1)) != 0) {
            return "impossible";
        }
        else{
            return N.toString();
        }
        //recursiveCheck(BigInteger.valueOf(1), BigInteger.valueOf(1),, BigInteger.valueOf(0)).toString();
    }
 }