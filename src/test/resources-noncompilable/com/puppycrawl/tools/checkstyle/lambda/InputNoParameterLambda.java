//Compilable with Java8
package com.puppycrawl.tools.checkstyle.checks.lambda;

public class InputNoParameterLambda {
    static Runnable r1 = ()-> System.out.println("Hello world one!");
}
