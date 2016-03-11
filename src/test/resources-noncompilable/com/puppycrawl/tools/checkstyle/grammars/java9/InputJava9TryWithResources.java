//Compilable with Java9
package com.puppycrawl.tools.checkstyle.grammars;

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
    }
}
