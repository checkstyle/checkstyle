package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

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
    } //indent:4 exp:4
} //indent:0 exp:0
