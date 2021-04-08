package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/*
 * Config:
 * option = nl
 */
public class InputLeftCurlyTestNewLineOptionWithLambda
{ // ok
    static Runnable r1 = () -> { // violation
        String.valueOf("Hello world one!");
    };

    static Runnable r2 = () -> String.valueOf("Hello world two!");

    static Runnable r3 = () -> {String.valueOf("Hello world two!");}; // violation

    static Runnable r4 = () ->
    { // ok
        String.valueOf("Hello world one!");
    };
}
