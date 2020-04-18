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

class Foo {
    static final Bar BAR = new Bar();
    static class Bar implements AutoCloseable {
        public void close() {
        }
    }


    final AutoCloseable closable = () -> {};

    public void method1(TestClass cls) throws Exception {
        try (closable) {
        }
        try (cls.closable) {
        }

    }

    void method() {
        Foo.Bar bar = new Foo.Bar();
        try (Foo.BAR; BAR) {
        }
        try(this.BAR.bar){}
        try(this.BAR){
        }
        try(Foo.Bar b = new Foo.Bar()) {
        }
        try(Foo.super.BAR) {
        }
        try (new TwrForVariable1() { }.finalWrapper.finalField) {
        }
        try ((args.length > 0 ? v : new TwrForVariable1()).finalWrapper.finalField) {
        }
    }
}
