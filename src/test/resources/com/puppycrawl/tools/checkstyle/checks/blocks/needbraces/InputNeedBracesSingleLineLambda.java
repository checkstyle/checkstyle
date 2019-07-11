package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesSingleLineLambda {

    static Runnable r1 = ()->String.CASE_INSENSITIVE_ORDER.equals("Hello world one!");
    static Runnable r2 = () -> String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");
    static Runnable r3 = () ->
        String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");
    static Runnable r4 = () -> {String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");};
    Runnable r5 = () -> java.util.Objects.hash(1, 2,
        3,4);
    Runnable r6 = () -> {java.util.Objects.hash(1, 2,
        3,4);};
    {
        java.util.concurrent.CompletableFuture.runAsync(() -> System.out.println(
            (Runnable) () -> System.out.println(
                123)));
    }
    {
        java.util.concurrent.CompletableFuture.runAsync(() -> {System.out.println(
            (Runnable) () -> {System.out.println(
                123);});});
    }
}
