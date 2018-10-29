package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

public class InputIndentationInvalidDoWhileIndent { //indent:0 exp:0
    public void method1() { //indent:4 exp:4
        boolean test = true; //indent:8 exp:8

do System.getProperty("foo"); while (test); //indent:0 exp:8 warn
do {} while (test); //indent:0 exp:8 warn
do { //indent:0 exp:8 warn
} while (test); //indent:0 exp:8 warn
do {} //indent:0 exp:8 warn
while (test); //indent:0 exp:8 warn
do //indent:0 exp:8 warn
{} while (test); //indent:0 exp:8 warn
do {} //indent:0 exp:8 warn
while //indent:0 exp:8 warn
(test); //indent:0 exp:8 warn
do {} while //indent:0 exp:8 warn
(test); //indent:0 exp:8 warn
do {} while //indent:0 exp:8 warn
( //indent:0 exp:8 warn
test //indent:0 exp:8 warn
); //indent:0 exp:8 warn
    } //indent:4 exp:4
} //indent:0 exp:0
