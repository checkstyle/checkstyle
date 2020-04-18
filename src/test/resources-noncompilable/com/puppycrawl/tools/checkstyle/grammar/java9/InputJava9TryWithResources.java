//non-compiled with javac: Compilable with Java9
package com.puppycrawl.tools.checkstyle.grammar.java9;

/**
 * Input for Java 9 try-with-resources.
 */
public class InputJava9TryWithResources
{
    public static class MyResource implements AutoCloseable {
        @Override
        public void close() throws Exception { }
    }

    public static void main(String[] args) throws Exception {
        MyResource resource = new MyResource();
        try (resource) { } finally { }

        final MyResource resource1 = new MyResource();
        final MyResource resource2 = new MyResource();
        try (resource1;resource2) { } finally { }
    }
}

class TwrForVariable1 implements AutoCloseable  {
    private static int closeCount = 0;
    public static void main(String... args) throws Exception{

        TwrForVariable1 v = new TwrForVariable1();


        try (v) {
        }

        try (v.finalWrapper.finalField) {
        }

        try (new TwrForVariable1() { }.finalWrapper.finalField) {
        }

        try ((args.length > 0 ? v : new TwrForVariable1()).finalWrapper.finalField) {
        }

        //More than one resource
        TwrForVariable1 i1 = new TwrForVariable1();
        try (i1; TwrForVariable1 i2 = new TwrForVariable1(true)) {
        }

        TwrForVariable1 m1 = new TwrForVariable1();
        try (m1; TwrForVariable1 m2 = m1; TwrForVariable1 m3 = m2;) {
        }

        // Nested try
        try {
            throw new CloseableException();
        } catch (CloseableException ex) {
            try (ex) {
            }
        }
        // null test cases
        TwrForVariable1 n = null;
        try (n) {
        }

        // anonymous class implementing AutoCloseable as variable in twr
        AutoCloseable a = new AutoCloseable() {
            public void close() { };
        };
        try (a) {
        } catch (Exception e) {}
    }

    public void close() {
        closeCount++;
    }

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

    static class CloseableException extends Exception implements AutoCloseable {
        @Override
        public void close() {
            closeCount++;
        }
    }

    public TwrForVariable1(boolean throwException) {
        if (throwException)
            throw new RuntimeException("Initialization exception");
    }

    public TwrForVariable1() {
        this(false);
    }
}
