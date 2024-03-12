package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.io.InputStreamReader; //indent:0 exp:0

public class InputIndentationNewHandler //indent:0 exp:0
{ //indent:0 exp:0
    public void test() { //indent:4 exp:4
        Object o = new Object(); //indent:8 exp:8
        Object p = new //indent:8 exp:8
Object(); //indent:0 exp:12 warn
        Object q = new Object//indent:8 exp:8
(); //indent:0 exp:12 warn
        o = new Integer("".indexOf("5")); //indent:8 exp:8
        o = new //indent:8 exp:8
Integer("".indexOf("5")); //indent:0 exp:8 warn
        o = new Integer//indent:8 exp:8
("".indexOf("5")); //indent:0 exp:8 warn
        try (InputStreamReader reader = //indent:8 exp:8
            new InputStreamReader(System.in)) { //indent:12 exp:12
        } catch (Exception e) { //indent:8 exp:8
        } //indent:8 exp:8
    } //indent:4 exp:4

    int[][] array10b //indent:4 exp:4
= new int[][] { //indent:0 exp:8 warn
                  new int[] { 1, 2, 3}, //indent:18 exp:>=8
                  new int[] { 1, 2, 3}, //indent:18 exp:>=8
    }; //indent:4 exp:4

} //indent:0 exp:0
