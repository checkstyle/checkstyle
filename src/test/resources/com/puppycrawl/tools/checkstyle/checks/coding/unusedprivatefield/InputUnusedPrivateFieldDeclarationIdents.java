/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldPattern = (default)serialVersionUID

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedprivatefield;

import java.util.function.Function;

public class InputUnusedPrivateFieldDeclarationIdents {

    private int x; // violation 'Unused private field'

    public Function<Integer, Integer> first = x -> 1;

    private int y; // violation 'Unused private field'

    public Function<Integer, Integer> second = y -> y + 1;

    private int z; // violation 'Unused private field'

    public Function<Integer, Integer> third = (z) -> 1;

    private int used; // ok, private field is used

    public int getUsed() {
        return used;
    }

    private String s; // violation 'Unused private field'

    public int patternVar(Object obj) {
        if (obj instanceof String s) {
            return 1;
        }
        return 0;
    }

    private int size; // violation 'Unused private field'

    public record NestedRecord(int size) {
    }

    private int VALUE; // violation 'Unused private field'

    public enum NestedEnum {
        VALUE
    }
}
