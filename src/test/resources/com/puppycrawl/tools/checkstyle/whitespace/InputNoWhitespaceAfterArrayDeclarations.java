package com.puppycrawl.tools.checkstyle.whitespace;

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
}
