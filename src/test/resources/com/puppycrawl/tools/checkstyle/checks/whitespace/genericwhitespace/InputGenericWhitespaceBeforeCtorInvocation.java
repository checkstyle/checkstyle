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
        new java.util.HashMap<Integer, Integer> (); // violation, ''>' is followed by whitespace.'
        new ArrayList<Very <Gen<String,String>>> (); // 2 violations
    }

    static final Very<Gen<String, String>> veryGen =
            new Very<Gen<String, String>> () {}; // violation, ''>' is followed by whitespace.'

    <T> void mustPatTheCroc(boolean mustPat, T[] crocs) {
        Very.swampPuppy(new Gen<String, String> ()); // violation, ''>' is followed by whitespace.'
        if (!mustPat) {
            mustPatTheCroc(true, new Very<?> []{}); // violation, ''>' is followed by whitespace.'
        }
    }

    class Gen<T, U> {}

    static class Very<Q> {
        static void swampPuppy(Gen<String, String> croc) {}
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
