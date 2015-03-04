//Compilable with Java8
package com.puppycrawl.tools.checkstyle.blocks;
public class InputSingleLineLambda {
    
    static Runnable r1 = ()->System.out.println("Hello world one!");
    static Runnable r2 = () -> System.out.println("Hello world two!");
}