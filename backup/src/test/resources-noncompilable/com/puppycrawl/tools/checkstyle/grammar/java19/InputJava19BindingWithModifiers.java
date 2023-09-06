//non-compiled with javac: Compilable with Java19
package com.puppycrawl.tools.checkstyle.grammar.java19;

public class InputJava19BindingWithModifiers {
    record Box<T>(T t) {
    }

    class A {
    }

    class B extends A {
    }

    sealed interface I permits C, D {
    }

    final class C implements I {
    }

    final class D implements I {
    }

    record Pair<T>(T x, T y) {
    }

    @interface MyAnno1{}
    @interface MyAnno2{}

    @interface MyAnno3{
        String value();
        String[] values();
    }

    record Tuple(Object o1, Object o2, Object o3) {}
    record VarArgs(Object... objArgs){}


    static void test1(Box<Object> bo) {
        if (bo instanceof @MyAnno1 @MyAnno2 Box<Object>(String s)) {
            System.out.println("String " + s);
        }
    }

    static void test2(Box<Object> bo) {
        if (bo instanceof @MyAnno2 final @MyAnno1 Box<?>(var s)) {
            System.out.println("String " + s);
        }
    }

    void test3(Box<I> bo) {
        C c = new C();
        if (bo instanceof @MyAnno1 Box<I>(var s)) {
            System.out.println("String " + s);
        }
        else if (bo instanceof Box b) {

        }
        else if (bo instanceof Box<I> b && bo.t.equals(c)) {

        }
        else if (bo != null && bo.t.equals(c)) {

        }
    }

    static void test4(Pair<I> p) {
        if (p instanceof final Pair<I>(C t1, C t2)) {
            System.out.println(t2);
        }
        else if (p instanceof @MyAnno2 final Pair<I>(D t1, D t2)) {
            System.out.println(t1);
        }
    }

    static void test5(Tuple t) {
        if (t instanceof Tuple(String x, String y, String z)) {
            System.out.println(x + y + z);
        }
        else if (t instanceof final Tuple(
                final Integer x,
                @MyAnno1 Integer y,
                final @MyAnno1 @MyAnno2 Integer z)) {
            System.out.println(x + y + z);
        }
    }

    String m2(Pair<I> p1) {
        return switch (p1) {
            case Pair<I>(final C c,@MyAnno2 I i) -> "T!";
            case final Pair<I>(
                    final @MyAnno3(value = "some value", values = {}) D d,
                    final C c) -> "C!";
            case Pair<I>(
                    @MyAnno3(value = "val", values = {"1", "2"}) D d1,D d2) -> "D!";
        };
    }

    void m3 (Pair<Pair<Integer>> s) {
        switch (s) {
            case final Pair<Pair<Integer>>(
                    @MyAnno1 Pair<Integer>(@MyAnno1 Integer x1, final Integer y1) x,
                    final Pair<Integer>(Integer x2, Integer y2) p2) y
                        -> System.out.println();
            case default -> System.out.println();
        }
    }

    void m4 (Pair<Pair<Integer>> s) {
        switch (s) {
            case @MyAnno2 Pair<Pair<Integer>>(
                    @MyAnno3(value = "", values = {}) Pair<Integer>(
                            final Integer x1,Integer y1) x,
                     @MyAnno3(value = "", values = {}) final Pair<Integer>(
                             @MyAnno2 final Integer x2,Integer y2) p2) y
                                    -> System.out.println();
            case default -> System.out.println();
        }
    }
}
