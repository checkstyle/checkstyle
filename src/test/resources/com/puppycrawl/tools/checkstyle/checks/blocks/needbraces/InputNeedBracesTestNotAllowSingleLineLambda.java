/*
NeedBraces
allowSingleLineStatement = (default)false
allowEmptyLoopBody = (default)false
tokens = LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesTestNotAllowSingleLineLambda {

    static Runnable r1 = ()->String.CASE_INSENSITIVE_ORDER.equals("Hello world one!"); // violation
    static Runnable r2 = () -> String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");//violation
    static Runnable r3 = () -> // violation
        String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");
    static Runnable r4 = () -> {String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");};
    Runnable r5 = () -> java.util.Objects.hash(1, 2, // violation
        3,4);
    Runnable r6 = () -> {java.util.Objects.hash(1, 2,
        3,4);};
    {
        java.util.concurrent.CompletableFuture.runAsync(() -> System.out.println( // violation
            (Runnable) () -> System.out.println( // violation
                123)));
    }
    {
        java.util.concurrent.CompletableFuture.runAsync(() -> {System.out.println(
            (Runnable) () -> {System.out.println(
                123);});});
    }
}
