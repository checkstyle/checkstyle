// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.internal.raresyntaxnoexception;

public class InputRareSyntaxNoExceptionFlexibleConstructorBody {
    private final String name;
    private final int value;

    public InputRareSyntaxNoExceptionFlexibleConstructorBody(String name) {
        String checked = validate(name);
        super();
        this.name = checked;
        this.value = checked.length();
    }

    public InputRareSyntaxNoExceptionFlexibleConstructorBody(String name, int value) {
        String checkedName = validate(name);
        int checkedValue;
        try {
            checkedValue = parse(value);
        }
        catch (IllegalArgumentException ex) {
            throw new IllegalStateException("bad value", ex);
        }
        super();
        this.name = checkedName;
        this.value = checkedValue;
    }

    private static String validate(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name must not be empty");
        }
        return name;
    }

    private static int parse(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("value must not be negative");
        }
        return value;
    }

    public String name() {
        return name;
    }

    public int value() {
        return value;
    }
}
