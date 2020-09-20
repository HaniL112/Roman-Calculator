/**
 * @Author Hani Ly 073142739@gapps.yrdsb.ca
 */

package com.example.hani.romancalculator;
import java.util.Hashtable;

public class Roman {

    private String mRomanString; // String representing the roman numerals
    private int mRomanInt; // Integer value of the roman numerals

    // Used for converting int to String
    private static Hashtable<Integer, Hashtable<Integer,String>> sIntToRoman = new Hashtable<>();
    // Used for converting from character to int
    private static Hashtable<Character, Integer> sRomanToInt = new Hashtable<>();

    static {

        // Ones column
        Hashtable<Integer, String> onesToRoman = new Hashtable<>();
        sIntToRoman.put(1,  onesToRoman);
        onesToRoman.put(1,  "I");
        onesToRoman.put(2,  "II");
        onesToRoman.put(3,  "III");
        onesToRoman.put(4,  "IV");
        onesToRoman.put(5,  "V");
        onesToRoman.put(6,  "VI");
        onesToRoman.put(7,  "VII");
        onesToRoman.put(8,  "VIII");
        onesToRoman.put(9,  "IX");

        // Tens column
        Hashtable<Integer, String> tensToRoman = new Hashtable<>();
        sIntToRoman.put(10,  tensToRoman);
        tensToRoman.put(1,  "X");
        tensToRoman.put(2,  "XX");
        tensToRoman.put(3,  "XXX");
        tensToRoman.put(4,  "XL");
        tensToRoman.put(5,  "L");
        tensToRoman.put(6,  "LX");
        tensToRoman.put(7,  "LXX");
        tensToRoman.put(8,  "LXXX");
        tensToRoman.put(9,  "XC");

        // Hundreds column
        Hashtable<Integer, String> hundredsToRoman = new Hashtable<>();
        sIntToRoman.put(100,  hundredsToRoman);
        hundredsToRoman.put(1,  "C");
        hundredsToRoman.put(2,  "CC");
        hundredsToRoman.put(3,  "CCC");
        hundredsToRoman.put(4,  "CD");
        hundredsToRoman.put(5,  "D");
        hundredsToRoman.put(6,  "DC");
        hundredsToRoman.put(7,  "DCC");
        hundredsToRoman.put(8,  "DCCC");
        hundredsToRoman.put(9,  "CM");

        // Thousands column
        Hashtable<Integer, String> thousandsToRoman = new Hashtable<>();
        sIntToRoman.put(1000,  thousandsToRoman);
        thousandsToRoman.put(1,  "M");
        thousandsToRoman.put(2,  "MM");
        thousandsToRoman.put(3,  "MMM");
        thousandsToRoman.put(4, "MMMM");

        // Character to integer conversions
        sRomanToInt.put('I', 1);
        sRomanToInt.put('V', 5);
        sRomanToInt.put('X', 10);
        sRomanToInt.put('L', 50);
        sRomanToInt.put('C', 100);
        sRomanToInt.put('D', 500);
        sRomanToInt.put('M', 1000);
    }

    public Roman(int n) {
        set(n);
    }

    public Roman(String s) {
        set(s);
    }

    /**
     * Converts given integer to a String of roman numerals.
     * Returns null if given integer is invalid.
     * @param n - integer between 0 to 4999 inclusive
     * @return String of roman numerals
     */
    public static String convertToString(int n) {

        if (!validInt(n)) return null;

        String inRoman = ""; // converted to roman characters
        for (int i = 1; n > 0; i*=10) {
            if (n % 10 != 0) {
                inRoman = sIntToRoman.get(i).get(n % 10) + inRoman;
            }
            n /= 10;
        }
        return inRoman;
    }

    /**
     * Converts given String of roman numerals to an integer value.
     * Returns 0 if integer value given is not valid.
     * @param s - valid String of roman numerals (all upper case)
     * @return integer value of given String of roman numerals
     */
    public static int convertToInt(String s) {

        int total =0;
        int a, b;

        // https://www.geeksforgeeks.org/converting-roman-numerals-decimal-lying-1-3999/
        for (int i=0; i<s.length(); i++) {
            a = sRomanToInt.get(s.charAt(i)); // integer value of current char

            if (i+1 < s.length()) {
                b = sRomanToInt.get(s.charAt(i+1)); // look at char ahead

                if (a >= b) { // Value of current character is greater than value of next
                    total += a;
                } else {
                    total = total + b - a;
                    i++;
                }
            } else {
                total += a;
                i++;
            }

        }

        return total;
    }

    /**
     * Returns String representing roman numerals
     * @return String representing roman numerals
     */
    public String toRoman() {
        return this.mRomanString;
    }

    /**
     * Returns integer value of roman numerals
     * @return Integer value of roman numerals
     */
    public int toInt() {
        return this.mRomanInt;
    }

    /**
     * Sets the String of roman numerals to given String and changes integer
     * value accordingly if given parameter is valid.
     * @param s - String of roman numerals
     * @return true if valid parameter
     */
    private boolean set(String s) {
        if (validRoman(s)) {
            this.mRomanString = s;
            this.mRomanInt = convertToInt(s);
            return true;
        } return false;
    }

    /**
     * Sets the integer value of the Roman object and changes the String of roman
     * numerals accordingly. If given integer value is not valid, no attributes will
     * be reassigned.
     * @param n - integer between 1 and 4999 inclusively
     * @return true if valid parameter
     */
    private boolean set(int n) {
        if (validInt(n)) {
            this.mRomanInt = n;
            this.mRomanString = convertToString(n);
            return true;
        } return false;
    }

    /**
     * Adds given value. If result cannot be represented in roman numerals,
     * or parameter is invalid, addition will not occur.
     * @param n - integer
     * @return true if valid parameter
     */
    public boolean add(int n) {
        return set(this.toInt() + n);
    }

    /**
     * Adds given value. If sum cannot be represented in roman numerals,
     * or parameter is invalid, subtraction will not occur.
     * @param r - Roman object
     * @return true if valid parameter
     */
    public boolean add(Roman r) {
        if (r.toRoman() == null) return false;
        return set(this.toInt() + r.toInt());
    }

    /**
     * Subtracts given value. If difference cannot be represented in roman numerals,
     * or parameter is invalid, subtraction will not occur.
     * @param n - integer
     * @return true if valid parameter
     */
    public boolean subtract(int n) {
        return set(this.toInt() - n);
    }

    /**
     * Subtracts given value. If difference cannot be represented in roman numerals,
     * or parameter is invalid, subtraction will not occur.
     * @param r - Roman object
     * @return true if valid parameter
     */
    public boolean subtract(Roman r) {
        if (r.toRoman() == null) return false;
        return set(this.toInt() - r.toInt());
    }

    /**
     * Checks if integer value given can be converted to roman numerals.
     * @param n - integer
     * @return true if valid, false if not
     */
    public static boolean validInt(int n) {
        return (n > 0 && n < 5000); // between 1 and 4999
    }

    /**
     * Checks if String of given roman numerals is valid.
     * @param x - String of roman numerals
     * @return true if String is valid, false if not
     */
    public static boolean validRoman(String x) {
        if (x == null) return false;
        for (int i=0; i < x.length(); i++) { // Check for unknown characters
            if (sRomanToInt.get(x.charAt(i)) == null) return false;
        }
        if (convertToString(convertToInt(x)) == null) return false;
        if (!convertToString(convertToInt(x)).equals(x)) return false;
        return true;
    }
}
