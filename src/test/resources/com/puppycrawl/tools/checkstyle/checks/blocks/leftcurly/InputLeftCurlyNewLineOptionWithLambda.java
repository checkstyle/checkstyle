package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;


public class InputLeftCurlyNewLineOptionWithLambda
{
    static Runnable r1 = () -> {
        String.valueOf("Hello world one!");
    };

    static Runnable r2 = () -> String.valueOf("Hello world two!");

    static Runnable r3 = () -> {String.valueOf("Hello world two!");};

    static Runnable r4 = () ->
    {
        String.valueOf("Hello world one!");
    };
}
