package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputRightCurlyAloneLambda {

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

    static Runnable r6 = () -> {};

    static Runnable r7 = () -> {
    };

    static Runnable r8 = () ->
    {
    };

    static Runnable r9 = () -> {
        String.valueOf("Test rightCurly nine!");
    }; int i;       // violation

    void foo1() {
        Stream.of("Hello").filter(s -> {
                return s != null;
            }
        ).collect(Collectors.toList());

        Stream.of("Hello").filter(s -> {
                return s != null;
        }).collect(Collectors.toList());    // violation
    }
}
