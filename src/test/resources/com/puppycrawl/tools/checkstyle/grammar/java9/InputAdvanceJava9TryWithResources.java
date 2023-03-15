
package com.puppycrawl.tools.checkstyle.grammar.java9;

/**
 * Input for Advance Java 9 try-with-resources.
 */
public class InputAdvanceJava9TryWithResources implements AutoCloseable
{
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
                    new InputAdvanceJava9TryWithResources()) {
        }

        InputAdvanceJava9TryWithResources m1 = new InputAdvanceJava9TryWithResources();
        try (m1; InputAdvanceJava9TryWithResources m2 = m1;
                InputAdvanceJava9TryWithResources m3 = m2;) {
        }
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
            }
        };
    }
    @Override
    public void close() throws Exception {
    }
}
