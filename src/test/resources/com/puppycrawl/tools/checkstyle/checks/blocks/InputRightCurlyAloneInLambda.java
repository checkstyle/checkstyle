package com.puppycrawl.tools.checkstyle.checks.blocks;

/*
This is test class for token LAMBDA.
 */
public class InputRightCurlyAloneInLambda {

    static Runnable r1 = () -> {
        String.valueOf("Test rightCurly one!");
    };

    static Runnable r2 = () -> String.valueOf("Test rightCurly two!");

    static Runnable r3 = () -> {String.valueOf("Test rightCurly three!");};   //violation

    static Runnable r4 = () -> {
        String.valueOf("Test rightCurly four!");};    //violation

    static Runnable r5 = () ->
    {
        String.valueOf("Test rightCurly five!");
    };

    static Runnable r6 = () -> {};    //violation

    static Runnable r7 = () -> {
    };

    static Runnable r8 = () ->
    {
    };
}
