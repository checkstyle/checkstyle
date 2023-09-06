//non-compiled with javac: Compilable with Java16
package com.puppycrawl.tools.checkstyle.javaparser;

import java.util.concurrent.Callable;

/**
 * This file is able to be compiled by Java 8, but takes a very long time.
 */
public class InputJavaParserNoFreezeOnDeeplyNestedLambdas {
    void test() {
        m(null, () -> m(null, () -> m(null, () -> m(null, () -> m(null, () -> m(null, () -> m(null,
                () -> m(null, () -> m(null, () -> m(null, () -> m(null, () -> m(null, () -> m(null,
                () -> m(null, () -> m(null, () -> m(null, () -> m(null, () -> m(null, () -> m(null,
                () -> m(null, () -> m(null, () -> m(null, () -> m(null, () -> m(null, () -> m(null,
                () -> m(null, () -> m(null, () -> m(null, () -> m(null, () -> m(null, () -> m(null,
                (Callable<String>)null)))))))))))))))))))))))))))))));
    }
    static class A0 { }
    static class A1 { }
    static class A2 { }
    static class A3 { }
    static class A4 { }
    <Z extends A0> Z m(A0 t, Callable<Z> ct) { return null; }
    <Z extends A1> Z m(A1 t, Callable<Z> ct) { return null; }
    <Z extends A2> Z m(A2 t, Callable<Z> ct) { return null; }
    <Z extends A3> Z m(A3 t, Callable<Z> ct) { return null; }
    <Z extends A4> Z m(A4 t, Callable<Z> ct) { return null; }
    <Z> Z m(Object o, Callable<Z> co) { return null; }
}
