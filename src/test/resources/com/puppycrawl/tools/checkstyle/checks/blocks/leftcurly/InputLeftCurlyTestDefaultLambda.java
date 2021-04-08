package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/*
 * Config: default
 */
public class InputLeftCurlyTestDefaultLambda
{ // violation
    static Runnable r1 = () -> { // ok
        String.valueOf("Hello world one!");
    };

    static Runnable r2 = () -> String.valueOf("Hello world two!");

    static Runnable r3 = () -> {String.valueOf("Hello world two!");}; // violation

    static Runnable r4 = () ->
    { // violation
        String.valueOf("Hello world one!");
    };
}
