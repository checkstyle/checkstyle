package com.puppycrawl.tools.checkstyle.checks.blocks;

/*Test lambda*/
public class InputRightCurlyAloneLambda {

    static Runnable k1 = () -> {
        String.valueOf("Test rightCurly 1!");
    };

    static Runnable k2 = () -> String.valueOf("Test rightCurly 2!");

    static Runnable k3 = () -> {String.valueOf("Test rightCurly 3!");};   // violation

    static Runnable k4 = () -> {
        String.valueOf("Test rightCurly 4!");};                     // violation

    static Runnable k5 = () ->
    {
        String.valueOf("Test rightCurly 5!");
    };

    static Runnable k6 = () -> {};       // violation

    static Runnable k7 = () -> {
    };

    static Runnable k8 = () ->
    {
    };
}
