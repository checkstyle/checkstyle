//non-compiled with javac: Compilable with Java10
package com.puppycrawl.tools.checkstyle.grammar.java10;

/**
 * Input for Java 10 local variable type inference.
 */
public class InputJava10LocalVarTypeInference
{
    InputJava10LocalVarTypeInference() {
        var intArray = new int[]{};
        var str = "1" + 2;
    }
}
