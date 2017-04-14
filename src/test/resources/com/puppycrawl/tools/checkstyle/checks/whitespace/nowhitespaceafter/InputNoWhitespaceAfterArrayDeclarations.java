package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

public class InputNoWhitespaceAfterArrayDeclarations
{
    Object[] someStuff = {}; //Correct
    Object [] someStuff1 = {}; //Incorrect
    Object someStuff2[] = {}; //Correct
    Object someStuff3 [] = {}; //Incorrect
    int [] a = {}; //Incorrect
    String s [] = {}; //Incorrect
    double d [] = {}; //Incorrect
    char[] c = {}; //Correct
    short sh[] = {}; //Correct
    long[] l = {}; //Correct
    byte b[] = {}; //Correct
    int get() [] { //Incorrect
        return a;}
    int [] receive() { return a; } //Incorrect
    int get1(int k, int c, int b) [] { //Incorrect
        return null;
    }
    private String[] getLines() { //Correct
        return new String[] { //Correct
                "s"
        };
    }
    String aOptions[][]; //Correct
    int [][][] abc; //Incorrect
    int cba [][][]; //Incorrect
    private String[][][] getSeveralLines() { //Correct
        return new String [][][] { //Incorrect
                new String [][] { //Incorrect
                        new String[] { //Correct
                                "s"
                        }
                }
        };
    }
    int ar [] = new int [] {1, 2}; //Incorrect (2 warnings)
    private int [][][] getMultArray() { //Incorrect
        return null;
    }
    private long getLongMultArray(int someParam, String value) [][][] { //Incorrect
        return null;
    }
}
