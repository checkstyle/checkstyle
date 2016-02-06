//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.blocks;
public class InputSingleLineLambda {
    
    static Runnable r1 = ()->System.out.println("Hello world one!");
    static Runnable r2 = () -> System.out.println("Hello world two!");
    static Runnable r3 = () -> 
        System.out.println("Hello world two!");
    static Runnable r4 = () -> {System.out.println("Hello world two!");};
}