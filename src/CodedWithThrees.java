import java.util.ArrayList;
import java.util.Collections;

public class CodedWithThrees {
    private static int constructNum(ArrayList<Integer> l){
        int num = 0;
        for (int i = 0; i < l.size(); i++) {
            num += l.get(i) * Math.pow(10, i);
        }
        return num;
    }
    private static int getSum(ArrayList<Integer> l){
        int sum = 0;
        for (Integer integer : l) {
            sum += integer;
        }
        return sum;
    }
    private static void fillArrList(ArrayList<Integer> intArr, int[] l){
        for (int j : l) {
            intArr.add(j);
        }
    }

    public static int solution(int[] l) {
        ArrayList<Integer> digits = new ArrayList<>(); //make the manipulation a little easier
        fillArrList(digits, l);
        Collections.sort(digits);

        int sum = getSum(digits);
        
        while (sum%3 != 0) {
            //divisible by 3
            //there is a number with mod(3)=1 or two numbers with mod(3)=2
            switch (sum % 3) {
                case 1 -> {
                    for (int digit = 1; digit < 10; digit += 3) {
                        if (digits.contains(digit)) {
                            digits.remove(Integer.valueOf(digit));
                            return constructNum(digits);
                        }
                    }
                    for (int digit = 2; digit < 10; digit += 3) {
                        if (digits.contains(digit)) {
                            digits.remove(Integer.valueOf(digit));
                            break;
                        }
                    }
                }
                case 2 -> {
                    for (int digit = 2; digit < 10; digit += 3) {
                        if (digits.contains(digit)) {
                            digits.remove(Integer.valueOf(digit));
                            return constructNum(digits);
                        }
                    }
                    for (int digit = 1; digit < 10; digit += 3) {
                        if (digits.contains(digit)) {
                            digits.remove(Integer.valueOf(digit));
                            break;
                        }
                    }
                }
            }
            sum = getSum(digits);
        }
        return constructNum(digits);
    }

}