/*
GenericWhitespace


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.whitespace.genericwhitespace;

import java.lang.annotation.Target;
import java.util.HashMap;

import static java.lang.annotation.ElementType.TYPE_USE;

public class InputGenericWhitespaceBeforeRecordHeader {
    record License<T>() {}
    record Person<T> () {} // violation, ''>' is followed by whitespace.'
    record Session < T > (T a) {}
    // 4 violations above:
    //                    ''<' is followed by whitespace.'
    //                    ''<' is preceded with whitespace.'
    //                    ''>' is followed by whitespace.'
    //                    ''>' is preceded with whitespace.'

    class InnerClass {
        static <T> void innerMethod(T a) {}
        static <T> void innerMethod(T a, boolean isSession) {}
        record Participant<T>() {
            //            +          +                                         +
            record Stats<T> (HashMap <String, Session<HashMap<String, Integer>>>stats) {
                // 3 violations above:
                //                    ''>' is followed by whitespace.'
                //                    ''<' is preceded with whitespace.'
                //                    ''>' is followed by an illegal character.'

                record Achievements<T> () {} // violation, ''>' is followed by whitespace.'
            }
        }
    }

    int method(Session<HashMap<String, Integer>> ssn) {
        //                                 +                        +
        InnerClass.innerMethod(new Session <HashMap<Integer, String>> (new HashMap()));
        // 2 violations above:
        //                    ''<' is preceded with whitespace.'
        //                    ''>' is followed by whitespace.'

        //          +                                          + +
        new License <@A Session<HashMap<@A Session, @A Session >>> ();
        // 3 violations above:
        //                    ''<' is preceded with whitespace.'
        //                    ''>' is preceded with whitespace.'
        //                    ''>' is followed by whitespace.'

        //                                                    +                +
        new InnerClass.Participant.Stats.Achievements<Session <License<String>>> ();
        // 2 violations above:
        //                   ''<' is preceded with whitespace.'
        //                   ''>' is followed by whitespace.'

        //                         +                        +
        if (ssn instanceof Session <HashMap<String, Integer>> (var sess)) {
            // 2 violations above:
            //                    ''<' is preceded with whitespace.'
            //                    ''>' is followed by whitespace.'

            InnerClass.innerMethod(new Session<?> []{}, true);
            // violation above, ''>' is followed by whitespace.'
        }

        return switch (ssn) {
            //           +                        +            +
            case Session <HashMap<String, Integer>> (@A HashMap< @A String, Integer> hm) -> 1;
            //  3 violations above:
            //                     ''<' is preceded with whitespace.'
            //                     ''>' is followed by whitespace.'
            //                     ''<' is followed by whitespace.'

            //           +       +                    +
            case Session <HashMap< @A String, Integer>>sess -> 1;
            // 3 violations above:
            //                    ''<' is preceded with whitespace.'
            //                    ''<' is followed by whitespace.'
            //                    ''>' is followed by an illegal character.'
        };
    }

    static {
        new Object() {
            //          +                  +  +             +         + +
            record Sys<T> (Session<Session <T >> p, Session <Session<T> > q) {}
            // 6 violations above:
            //                    ''>' is followed by whitespace.'
            //                    ''<' is preceded with whitespace.'
            //                    ''>' is preceded with whitespace.'
            //                    ''<' is preceded with whitespace.'
            //                    ''>' is followed by whitespace.'
            //                    ''>' is preceded with whitespace.'

            //          +                                +
            record Game <T extends Number & Comparable<T>> (T guess) {}
            // 2 violations above:
            //                    ''<' is preceded with whitespace.'
            //                    ''>' is followed by whitespace.'

            void method() {
                //      +      +             +                +
                new Sys <String> (new Session< Session<String>> (new Session<>("a")), null);
                // 4 violations above:
                //                    ''<' is preceded with whitespace.'
                //                    ''>' is followed by whitespace.'
                //                    ''<' is followed by whitespace.'
                //                    ''>' is followed by whitespace.'
            }
        };
    }

    @Target({TYPE_USE}) @interface A {}
}
