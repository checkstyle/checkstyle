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
