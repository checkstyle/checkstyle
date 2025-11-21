/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = ARRAY_DECLARATOR,INDEX_OP


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

public class InputNoWhitespaceAfterArrayDeclarations
{
    Object[] someStuff = {}; //Correct
    Object [] someStuff1 = {}; // violation, ''Object' is followed by whitespace.'
    Object someStuff2[] = {}; //Correct
    Object someStuff3 [] = {}; // violation, ''someStuff3' is followed by whitespace.'
    int [] a = {}; // violation, ''int' is followed by whitespace.'
    String s [] = {}; // violation, ''s' is followed by whitespace.'
    double d [] = {}; // violation, ''d' is followed by whitespace.'
    char[] c = {}; //Correct
    short sh[] = {}; //Correct
    long[] l = {}; //Correct
    byte b[] = {}; //Correct
    int get() [] { // violation, ''get' is followed by whitespace.'
        return a;}
    int [] receive() { return a; } // violation, ''int' is followed by whitespace.'
    int get1(int k, int c, int b) [] { // violation, ''get1' is followed by whitespace.'
        return null;
    }
    private String[] getLines() { //Correct
        return new String[] { //Correct
                "s"
        };
    }
    String aOptions[][]; //Correct
    int [][][] abc; // violation, ''int' is followed by whitespace.'
    int cba [][][]; // violation, ''cba' is followed by whitespace.'
    private String[][][] getSeveralLines() { //Correct
        return new String [][][] { // violation, ''String' is followed by whitespace.'
                new String [][] { // violation, ''String' is followed by whitespace.'
                        new String[] { //Correct
                                "s"
                        }
                }
        };
    }
    int ar [] = new int [] {1, 2};
    // 2 violations above:
    //           ''ar' is followed by whitespace.'
    //           ''int' is followed by whitespace.'
    private int [][][] getMultiArray() { // violation, ''int' is followed by whitespace.'
        return null;
    }
    //violation below, ''getLongMultiArray' is followed by whitespace.'
    private long getLongMultiArray(int someParam, String value) [][][] {
        return null;
    }
    int aa = new int[]{1}[0];//Correct
    int bb = new int[]{1} [0]; // violation, ''}' is followed by whitespace.'
    int aaa = new int[][]{{1},{2}}[0][0];//Correct
    int bbb = new int [][]{{1},{2}}[0][0]; // violation, ''int' is followed by whitespace.'
    int ccc = new int[] []{{1},{2}}[0][0]; // violation, '']' is followed by whitespace.'
    int ddd = new int[][]{{1},{2}} [0][0]; // violation, ''}' is followed by whitespace.'
    int eee = new int[][]{{1},{2}}[0] [0]; // violation, '']' is followed by whitespace.'
    int in1 = new int[][]{{1},{2}}[ 0][0];//Correct
    int in2 = new int[][]{{1},{2}}[0 ][0];//Correct
    int in3 = new int[][]{{1},{2}}[0][ 0];//Correct
    int in4 = new int[][]{{1},{2}}[0][0 ];//Correct
}
