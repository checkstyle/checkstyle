/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = INDEX_OP


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

public class InputNoWhitespaceAfterArrayDeclarations
{
    Object[] someStuff = {}; //Correct
    Object [] someStuff1 = {};
    Object someStuff2[] = {}; //Correct
    Object someStuff3 [] = {};
    int [] a = {};
    String s [] = {};
    double d [] = {};
    char[] c = {}; //Correct
    short sh[] = {}; //Correct
    long[] l = {}; //Correct
    byte b[] = {}; //Correct
    int get() [] {
        return a;}
    int [] receive() { return a; }
    int get1(int k, int c, int b) [] {
        return null;
    }
    private String[] getLines() { //Correct
        return new String[] { //Correct
                "s"
        };
    }
    String aOptions[][]; //Correct
    int [][][] abc;
    int cba [][][];
    private String[][][] getSeveralLines() { //Correct
        return new String [][][] {
                new String [][] {
                        new String[] { //Correct
                                "s"
                        }
                }
        };
    }
    int ar [] = new int [] {1, 2};

    private int [][][] getMultiArray() {
        return null;
    }

    private long getLongMultiArray(int someParam, String value) [][][] {
        return null;
    }
    int aa = new int[]{1}[0];//Correct
    int bb = new int[]{1} [0]; // violation ''}' is followed by whitespace.'
    int aaa = new int[][]{{1},{2}}[0][0];//Correct
    int bbb = new int [][]{{1},{2}}[0][0];
    int ccc = new int[] []{{1},{2}}[0][0];
    int ddd = new int[][]{{1},{2}} [0][0]; // violation ''}' is followed by whitespace.'
    int eee = new int[][]{{1},{2}}[0] [0]; // violation '']' is followed by whitespace.'
    int in1 = new int[][]{{1},{2}}[ 0][0];//Correct
    int in2 = new int[][]{{1},{2}}[0 ][0];//Correct
    int in3 = new int[][]{{1},{2}}[0][ 0];//Correct
    int in4 = new int[][]{{1},{2}}[0][0 ];//Correct
    int[] mutArr = {1};
    byte[] mutPixels = new byte[1];

    static class Holder {
        byte y;
    }

    void testIndexOpParenthesized(Holder x) {
        int fromParens = (mutArr )[0]; // violation ''mutArr' is followed by whitespace.'
        ((byte[]) mutPixels) [0] = x.y; // violation '')' is followed by whitespace.'
        int sum = ((byte[]) mutPixels) [0] + x.y;
    }
}
