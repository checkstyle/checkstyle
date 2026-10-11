/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/


package com.puppycrawl.tools.checkstyle.grammar.java9;

/**
 * Input for Java 9 try-with-resources.
 */
public class InputJava9TryWithResources // ok
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
