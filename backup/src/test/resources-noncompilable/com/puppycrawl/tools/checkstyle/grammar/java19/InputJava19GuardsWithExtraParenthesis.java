//non-compiled with javac: Compilable with Java19
package com.puppycrawl.tools.checkstyle.grammar.java19;

import java.util.Objects;

public class InputJava19GuardsWithExtraParenthesis {

    record Box<V>(V v) {
        static int x = 5;
    }

    int m1(Box<Box<String>> b) {
        return switch (b) {
            case Box<Box<String>>(Box<String>(String s)box)
                    when (("test".equals(s) && box.x != 7))  -> 1;
            case (Box<Box<String>>(Box<String>(String s)box))
                    when (("test".equals(s)) && ((int) box.x != 7))  -> 1;
            case Box<Box<String>>((Box<String>((String s))box))
                    when (boolean)"test".equals(s) && (boolean)Objects.equals(box.v, "box") -> (1);
            case Box<Box<String>>(Box<String>(String s)box)
                    when "test".equals(s) && Objects.equals(box.v, "box")
                        || "something else".equals(s) -> 1;
            case Box<Box<String>>(Box<String>(String s))
                    when "test".equals(s) -> 1;
            case Box<Box<String>>(Box<?> b2)
                    when "test".equals(b2.v) -> 1;
            case Box<Box<String>>(Object o) b2
                    when b2.v != null && "whatever".equals(o.toString())-> 1;
            default -> -1;
        };
    }

    record when<T>(when<T> when){}

    <T> int moreTrickyWhen(when<when<T>> when){
        return switch(when) {
        case when<when<T>>(when<when<T>> w1)
                  when ((when)w1).when.when.when.when.when.equals(null)-> 2;
        case when<when<T>>(when<when<T>> w1)
                  when (((w1.when.when.when.when.when.equals(null))))
                        -> 2;
        case when<when<T>>(when<when<T>>(when<when<T>> w2) w1)
                  when (((w1.when.when.when.when.when.equals(null))))
                        || ((when)w2).equals(((when)w1))-> 2;
        case when<when<T>>(when<when<T>>(when<when<T>> w2) w1)
                  when when != null
                        || ((when)w2).equals(((when)w1))-> 2;
        case when<when<T>>(Object w) when when != null -> 9;
          case default, null -> 1;
        };
    }

    Object m3(when<String> when) {
        if (when instanceof when<String>(when<String> w1)) {
            return w1.when.when().when;
        }
        else if (when instanceof when<String>(when<String>(when<String> s1)) w1) {
            if ("s1".equals(s1)) {
                return s1;
            } else {
                return w1;
            }
        }
        else if (when instanceof when<String>(when<String>(when<String> s1)) w1
                    && s1.toString().equals(w1.toString())) {
            if ("s1".equals(s1)) {
                return s1;
            } else {
                return w1;
            }
        }
        else if (!(when instanceof when<String>(when<String>(when<String> s1)) w1)) {
            return new Object();
        }
        else {
            return s1.toString().equals(w1.toString());
        }
    }
}
