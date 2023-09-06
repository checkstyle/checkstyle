//non-compiled with javac: Compilable with Java19
package com.puppycrawl.tools.checkstyle.grammar.java19;

public class InputRecordPatternsPreview {
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

    record Tuple(Object o1, Object o2, Object o3) {}
    record VarArgs(Object... objArgs){}


    static void test1(Box<Object> bo) {
        if (bo instanceof Box<Object>(String s)) {
            System.out.println("String " + s);
        }
    }

    static void test2(Box<Object> bo) {
        if (bo instanceof Box<?>(var s)) {
            System.out.println("String " + s);
        }
    }

    void test3(Box<I> bo) {
        C c = new C();
        if (bo instanceof Box<I>(var s)) {
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
        if (p instanceof Pair<I>(C t1, C t2)) {
            System.out.println(t2);
        }
        else if (p instanceof Pair<I>(D t1, D t2)) {
            System.out.println(t1);
        }
    }

    static void test5(Tuple t) {
        if (t instanceof Tuple(String x, String y, String z)) {
            System.out.println(x + y + z);
        }
        else if (t instanceof Tuple(Integer x, Integer y, Integer z)) {
            System.out.println(x + y + z);
        }
    }

    static void test5(VarArgs v) {
        if (v instanceof VarArgs(Integer[] ints)) {
            int l = ints.length;
        }
        else if (v instanceof VarArgs(Object[] objects)
            && objects.length > 2) {
            int l = objects.length;
        }
        else if (v instanceof VarArgs(Object[] objects)
            && objects.length < 2){
            int l = objects.length;
        }

    }

    static void m1(Pair<I> p1) {
        switch (p1) {
            case Pair<I>(I i,C c) ->
                    System.out.println("C!");
            case Pair<I>(I i,D d) ->
                    System.out.println("D!");
        }

        switch (p1) {
            case Pair<I>(C c,I i) ->
                    System.out.println("T!");
            case Pair<I>(D d,C c) ->
                    System.out.println("C!");
            case Pair<I>(D d1,D d2) ->
                    System.out.println("D!");
        }
    }

    String m2(Pair<I> p1) {
        return switch (p1) {
            case Pair<I>(C c,I i) -> "T!";
            case Pair<I>(D d,C c) -> "C!";
            case Pair<I>(D d1,D d2) -> "D!";
        };
    }
}
