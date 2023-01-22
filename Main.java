import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        //System.out.println(solution("Very Cool Braille"));
        
        for (int tests = 0; tests < 20; tests++) {            
                int[] pegs = new int[rand.nextInt(2, 20)];
                pegs[0] = rand.nextInt(2, 5);
                for (int i = 1; i < pegs.length; i++) {
                    pegs[i] = pegs[i-1] + rand.nextInt(8, 30);
                }
                System.out.println(Arrays.toString(pegs));
                System.out.println(Arrays.toString(Gear.solution((pegs))));
        }
        System.out.println(Arrays.toString(Gear.solution((new int[]{4, 26, 46, 66, 83, 95, 113, 123}))));
    }
    
    private static Map<Character, String> getBrailleMap(){
        Map<Character, String> map = new HashMap<Character, String>();
        map.put(' ', "000000");
        map.put('a', "100000");
        map.put('b', "110000");
        map.put('c', "100100");
        map.put('d', "100110");
        map.put('e', "100010");
        map.put('f', "110100");
        map.put('g', "110110");
        map.put('h', "110010");
        map.put('i', "010100");
        map.put('j', "010110");
        map.put('k', "101000");
        map.put('l', "111000");
        map.put('m', "101100");
        map.put('n', "101110");
        map.put('o', "101010");
        map.put('p', "111100");
        map.put('q', "111110");
        map.put('r', "111010");
        map.put('s', "011100");
        map.put('t', "011110");
        map.put('u', "101001");
        map.put('v', "111001");
        map.put('w', "010111");
        map.put('x', "101101");
        map.put('y', "101111");
        map.put('z', "101011");
        return map;
    }
    
    public static String solution(String s) {
        Map<Character, String> map = getBrailleMap();
        StringBuilder finalBraille = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char letter = s.charAt(i);

            if (Character.isLetter(letter)){
                if (Character.isUpperCase(letter)){
                    finalBraille.append("000001");
                    letter = Character.toLowerCase(letter);
                }
                finalBraille.append(map.get(letter));
            }
            else if (letter == ' '){
                finalBraille.append(map.get(letter));
            }
        }

        return finalBraille.toString();
    }
}
