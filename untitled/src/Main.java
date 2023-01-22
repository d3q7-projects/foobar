import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] sol = solution(new int[]{4,30});
        System.out.println(Arrays.toString(sol));
    }

    public static int[] solution(int[] pegArr){
        /*
        * suppose we have n pegs
        * we will let x be the radius of the first gear
        * this means that the distance between the first 2 pegs minus x is the radius of the next peg
        * by repeating this we then find the radius of the last gear
        * we know the gear ratio is 1/2, hence its radius is half of the first(x)
        * we should also notice that the calculated gear doesn't require the immediate calculation of x,
        * so we can take him out of the equation and "calculate" him last
        *
        * by rearranging the final equation we thus get the following algorithm
        * */
        int sigma = 0;
        int firstGearRadNum;
        int firstGearRadDen = 3;
        for (int i = 1; i < pegArr.length; i++) { //calculating the distance between each peg compared to the one before
            sigma = (pegArr[i] - pegArr[i-1]) - sigma;
        }
        if (pegArr.length % 2 == 0){ //odd amount of pegs means we get (sigma-x=0.5x)
            firstGearRadNum = 2*sigma;
            if (firstGearRadNum%3 == 0){
                firstGearRadNum =firstGearRadNum/3;
                firstGearRadDen = 1;
            }
        }
        else { //we get (sigma+x=0.5x)
            firstGearRadNum = -sigma*2; //already an simplest form
            firstGearRadDen = 1;
        }
        return firstGearRadNum > 0 ? new int[]{(int) firstGearRadNum, firstGearRadDen} : new int[]{-1, -1} ;
    }
}