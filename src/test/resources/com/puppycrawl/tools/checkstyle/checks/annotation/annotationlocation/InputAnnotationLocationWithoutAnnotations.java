package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;


/* Config:
 *
 * tokens = CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF,
 *         ANNOTATION_DEF, ANNOTATION_FIELD_DEF
 */

public class InputAnnotationLocationWithoutAnnotations {
    public static void main(String[] args) { // ok
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

    static abstract class Bar<T> { // ok
        abstract void foo();
    }
}
