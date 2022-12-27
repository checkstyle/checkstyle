//non-compiled with javac: Compilable with Java19
package com.puppycrawl.tools.checkstyle.grammar.java19;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class InputJava19PatternsInSwitchLabels {
    static void m1(Object o) {
        // patterns in switch are guarded by 'when'
        switch (o) {
            case Integer i when i >= 0 -> System.out.println(i);
            case Integer i -> System.out.println(i << 1);
            default -> throw new IllegalStateException(
                    "Unexpected value: " + o);
        }
    }

    static <T> void m2(Object o) {
        // Note that pattern variable cannot be cast to generic type
        switch (o) {
            case List list1 when (list1.size() >= 0
                    && list1.size() < 10) || list1.equals(new ArrayList<>()) ->
                    System.out.println(list1);
            case List list2 when (list2.size() >= 0
                    && list2.size() < 20) || list2.equals(new LinkedList()) ->
                    System.out.println("list2");
            case List list3 when list3.size() >= 0
                    && list3.size() < 45 || list3.equals(new LinkedList()) ->
                    System.out.println("list3");
            case Set set when "test".equals(set.toString()) ->
                    System.out.println(set);
            default -> throw new IllegalStateException(
                    "Unexpected value: " + o);
        }
    }

    static String m3(Object o) {
        // patterns in switch are guarded by 'when'
        return switch (o) {
            case List list1 when list1.size() >= 0
                    || list1.size() < 20 -> {
                yield "list1!";
            }
            case List list1 when list1.size() >= 0
                    || list1.size() < 50 -> "list1!";

            case Integer i1 when i1 < 3 && i1 > 0 -> "small integer!";
            case Integer i2 when i2 < 0 || i2 < -5 -> "small negative integer!";
            case A a when a instanceof B b && b.f == 2 ->
                    "it's a 'b'!";
            case A a when !(a instanceof B b) || a instanceof B  b1 && b.f < 0 ->
                    "it's not a 'b' or it's a negative 'b'";
            case A a when !(a instanceof B b) || a instanceof B b2 && b.f < -5 ->
                    "it's not a 'b' or it's a negative 'b'...";
            case null -> "it's null!";
            default -> throw new IllegalStateException(
                    "Unexpected value: " + o);
        };
    }

}

sealed class A permits B {

}

non-sealed class B extends A {
    public int f = 2;
}
