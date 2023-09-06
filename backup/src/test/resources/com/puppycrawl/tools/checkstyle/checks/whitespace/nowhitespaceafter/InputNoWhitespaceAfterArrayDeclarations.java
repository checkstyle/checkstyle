/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = ARRAY_DECLARATOR,INDEX_OP


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

public class InputNoWhitespaceAfterArrayDeclarations
{
    Object[] someStuff = {}; //Correct
    Object [] someStuff1 = {}; // violation
    Object someStuff2[] = {}; //Correct
    Object someStuff3 [] = {}; // violation
    int [] a = {}; // violation
    String s [] = {}; // violation
    double d [] = {}; // violation
    char[] c = {}; //Correct
    short sh[] = {}; //Correct
    long[] l = {}; //Correct
    byte b[] = {}; //Correct
    int get() [] { // violation
        return a;}
    int [] receive() { return a; } // violation
    int get1(int k, int c, int b) [] { // violation
        return null;
    }
    private String[] getLines() { //Correct
        return new String[] { //Correct
                "s"
        };
    }
    String aOptions[][]; //Correct
    int [][][] abc; // violation
    int cba [][][]; // violation
    private String[][][] getSeveralLines() { //Correct
        return new String [][][] { // violation
                new String [][] { // violation
                        new String[] { //Correct
                                "s"
                        }
                }
        };
    }
    int ar [] = new int [] {1, 2}; // 2 violations
    private int [][][] getMultiArray() { // violation
        return null;
    }
    private long getLongMultiArray(int someParam, String value) [][][] { // violation
        return null;
    }
    int aa = new int[]{1}[0];//Correct
    int bb = new int[]{1} [0]; // violation
    int aaa = new int[][]{{1},{2}}[0][0];//Correct
    int bbb = new int [][]{{1},{2}}[0][0]; // violation
    int ccc = new int[] []{{1},{2}}[0][0]; // violation
    int ddd = new int[][]{{1},{2}} [0][0]; // violation
    int eee = new int[][]{{1},{2}}[0] [0]; // violation
    int in1 = new int[][]{{1},{2}}[ 0][0];//Correct
    int in2 = new int[][]{{1},{2}}[0 ][0];//Correct
    int in3 = new int[][]{{1},{2}}[0][ 0];//Correct
    int in4 = new int[][]{{1},{2}}[0][0 ];//Correct
}
