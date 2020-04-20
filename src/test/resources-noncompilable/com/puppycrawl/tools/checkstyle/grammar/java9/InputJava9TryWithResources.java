//non-compiled with javac: Compilable with Java9
package com.puppycrawl.tools.checkstyle.grammar.java9;

/**
 * Input for Java 9 try-with-resources.
 */
public class InputJava9TryWithResources implements AutoCloseable
{
    //Constructor
    public InputJava9TryWithResources(boolean throwException) {
        if (throwException)
            throw new RuntimeException("Initialization exception");
    }

    //Constructor
    public InputJava9TryWithResources() {
        this(false);
    }

    //Main method
    public static void main(String[] args) throws Exception {
        InputJava9TryWithResources v = new InputJava9TryWithResources();

        try (v.finalWrapper.finalField) {
        }

        try (new InputJava9TryWithResources() { }.finalWrapper.finalField) {
        }

        try ((args.length > 0 ? v : new InputJava9TryWithResources()).finalWrapper.finalField) {
        }

        //More than one resource
        InputJava9TryWithResources i1 = new InputJava9TryWithResources();
        try (i1; InputJava9TryWithResources i2 = new InputJava9TryWithResources(true)) {
        }

        InputJava9TryWithResources m1 = new InputJava9TryWithResources();
        try (m1; InputJava9TryWithResources m2 = m1; InputJava9TryWithResources m3 = m2;) {
        }

        // Nested try
        try {
        } catch (Exception ex) {
            try (v) {
            }
        }
        // null test cases
        InputJava9TryWithResources n = null;
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
