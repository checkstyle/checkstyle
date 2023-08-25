package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;
import java.util.HashMap;
import java.util.Map;

/**
 * It is class.
 */
public class InputMissingJavadocMethodSetterGetter3 {
    private int[] array;
    private String name;

    public void someRandomMethod() {
        System.out.println("This is a random method.");
    }

    public void anotherRandomMethod(String name) {
        this.name = name;
    }

    public Map<String, Integer> getField() {
        return new HashMap<>();
    }

    public final void setName(String name) {
        this.name = name;
    }
}
