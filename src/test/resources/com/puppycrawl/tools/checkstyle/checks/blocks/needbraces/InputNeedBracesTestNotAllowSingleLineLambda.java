/*
NeedBraces
allowSingleLineStatement = (default)false
allowEmptyLoopBody = (default)false
tokens = LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesTestNotAllowSingleLineLambda {

    // violation below ''->' construct must use '{}'s'
    static Runnable r1 = ()->String.CASE_INSENSITIVE_ORDER.equals("Hello world one!");
    // violation below ''->' construct must use '{}'s'
    static Runnable r2 = () -> String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");
    static Runnable r3 = () -> // violation ''->' construct must use '{}'s'
        String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");
    static Runnable r4 = () -> {String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");};
    Runnable r5 = () -> java.util.Objects.hash(1, 2, // violation ''->' construct must use '{}'s'
        3,4);
    Runnable r6 = () -> {java.util.Objects.hash(1, 2,
        3,4);};
    {
        // violation below ''->' construct must use '{}'s'
        java.util.concurrent.CompletableFuture.runAsync(() -> System.out.println(
            (Runnable) () -> System.out.println( // violation ''->' construct must use '{}'s'
                123)));
    }
    {
        java.util.concurrent.CompletableFuture.runAsync(() -> {System.out.println(
            (Runnable) () -> {System.out.println(
                123);});});
    }
}
