//non-compiled with javac: Compilable with Java11
package com.puppycrawl.tools.checkstyle.grammar.java11;

import java.util.function.Function;

/**
 * Input for Java 11 lambda parameter type inference.
 */
public class InputJava11LambdaParamTypeInference
{
    InputJava11LambdaParamTypeInference() {
        Function<String, String> identity = (@Deprecated var value) -> value;
    }
}
