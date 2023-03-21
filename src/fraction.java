public class fraction {

    fraction(int n, int d){
        this.numerator = n;
        if (d != 0){
            this.denominator = d;
            this.reduce();
        }
        else{
            throw new IllegalArgumentException("Cannot divide by zero");
        }
    }
    public fraction copy(){
        return new fraction(this.numerator, this.denominator);
    }

    public fraction add(fraction second){
        //expand
        fraction result = new fraction(this.numerator * second.denominator + second.numerator * this.denominator,
                                        second.denominator * this.denominator);
        result.reduce();
        return result;
    }
    public fraction subtract(fraction second){
        //expand
        fraction result = new fraction(this.numerator * second.denominator - second.numerator * this.denominator,
                                        second.denominator * this.denominator);
        result.reduce();
        return result;
    }

    public fraction mul(fraction second){
        fraction result = new fraction(this.numerator * second.numerator, this.denominator * second.denominator);
        result.reduce();
        return result;
    }

    public fraction mul(int second){
        fraction result = new fraction(this.numerator * second, this.denominator);
        result.reduce();
        return result;
    }
    public fraction div(fraction second){
        fraction result = new fraction(this.numerator * second.denominator, this.denominator * second.numerator);
        result.reduce();
        return result;
    }

    public static int smallestCommonDivisor(int a, int b) {
        return (a * b) / gcd(a, b);
    }

    public int expandTo(int newDenominator){
        if (newDenominator % this.denominator == 0){
            return this.numerator * (newDenominator/this.denominator);
        }
        else{
            return -1;
        }
    }

    private static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    private void reduce(){
        if (this.numerator < 0 && this.denominator < 0){
            this.numerator *= -1;
            this.denominator *= -1;
            return;
        }
        if (this.numerator == 0 && this.denominator > 1){
            this.denominator = 1;
            return;
        }
        for(int test = Math.min(Math.abs(this.numerator), Math.abs(this.denominator)); test > 1 ; test--){
            if(this.numerator % test == 0 && this.denominator % test == 0){
                this.numerator /= test;
                this.denominator /= test;
                return;
            }
        }
    }

    public int getNumerator() {return numerator;}
    public int getDenominator() {
        return denominator;
    }
    public void setNumerator(int n) {this.numerator = n;}
    public void setDenominator(int d) {this.denominator = d;}

    private int numerator;
    private int denominator;
}
