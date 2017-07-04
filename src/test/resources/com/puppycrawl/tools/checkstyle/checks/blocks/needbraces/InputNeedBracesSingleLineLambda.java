package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesSingleLineLambda {
    
    static Runnable r1 = ()->String.CASE_INSENSITIVE_ORDER.equals("Hello world one!");
    static Runnable r2 = () -> String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");
    static Runnable r3 = () ->
        String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");
    static Runnable r4 = () -> {String.CASE_INSENSITIVE_ORDER.equals("Hello world two!");};
}
