//non-compiled with javac: Compilable with Java9
package com.puppycrawl.tools.checkstyle.grammar.java9;

/**
 * Input for Advance Java 9 try-with-resources.
 */
public class InputAdvanceJava9TryWithResources implements AutoCloseable
{
    //Constructor
    public InputAdvanceJava9TryWithResources(boolean throwException) {
        if (throwException)
            throw new RuntimeException("Initialization exception");
    }

    //Constructor
    public InputAdvanceJava9TryWithResources() {
        this(false);
    }

    //Main method
    public static void main(String[] args) throws Exception {
        InputAdvanceJava9TryWithResources v = new InputAdvanceJava9TryWithResources();

        try (v.finalWrapper.finalField) {
        }

        try (new InputAdvanceJava9TryWithResources() { }.finalWrapper.finalField) {
        }

        try ((args.length > 0 ? v
                : new InputAdvanceJava9TryWithResources()).finalWrapper.finalField) {
        }

        //More than one resource
        InputAdvanceJava9TryWithResources i1 = new InputAdvanceJava9TryWithResources();
        try (i1; InputAdvanceJava9TryWithResources i2 =
                    new InputAdvanceJava9TryWithResources(true)) {
        }

        InputAdvanceJava9TryWithResources m1 = new InputAdvanceJava9TryWithResources();
        try (m1; InputAdvanceJava9TryWithResources m2 = m1;
                InputAdvanceJava9TryWithResources m3 = m2;) {
        }

        // Nested try
        try {
        } catch (Exception ex) {
            try (v) {
            }
        }
        // null test cases
        InputAdvanceJava9TryWithResources n = null;
        try (n) {
        }

        // anonymous class implementing AutoCloseable as variable in try
        AutoCloseable a = new AutoCloseable() {
            public void close() { };
        };
        try (a) {
        } catch (Exception e) {}


    }

    private static int closeCount = 0;


    final static FinalWrapper finalWrapper = new FinalWrapper();
    public void method() throws Exception {
        try(this.finalWrapper.finalField) {
        }
    }
    static class FinalWrapper {
        public final AutoCloseable finalField = new AutoCloseable() {
            @Override
            public void close() throws Exception {
                closeCount++;
            }
        };
    }

    @Override
    public void close() throws Exception {

    }
}
