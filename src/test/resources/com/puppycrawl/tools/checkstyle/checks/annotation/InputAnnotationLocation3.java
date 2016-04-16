package com.puppycrawl.tools.checkstyle.checks.annotation;

public class InputAnnotationLocation3 {
    public static void main(String[] args) {
        final Foo foo = new Foo();
        foo.bar(new Bar<Foo>() {
            public void foo() {
            }
        });
    }

    static class Foo {
        void bar(Bar<Foo> bar) {
        }
    }

    static abstract class Bar<T> {
        abstract void foo();
    }
}
