/*
GenericWhitespace


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import static java.lang.annotation.ElementType.TYPE_USE;

public class InputGenericWhitespaceBeforeCtorInvocation {
    List<String> music = new ArrayList<>();
    List todo = new ArrayList<> (); // violation, ''>' is followed by whitespace.'

    Gen<String, Integer> eel = new Gen<String, Integer > (); // 2 violations

    Pipe pipe = new Pipe< String>() { // violation, ''<' is followed by whitespace.'
    };

    Class<?>[] classy = new Class<?> []{}; // violation, ''>' is followed by whitespace.'

    void method() {
        new Very.Deep<Integer>();
        new Very.Deep.Rabbit.Hole <  Integer  > (); // 4 violations

        new @A Gen<@A Gen, @A Gen> (); // violation, ''>' is followed by whitespace.'
    }

    class Gen<T, U> {}

    static class Very<Q> {
        static class Deep<D> {
            static class Rabbit<R> {
                static class Hole<H> {

                }
            }
        }
    }

    interface Pipe<T> {
    }

    @Target({TYPE_USE}) @interface A {}
}
