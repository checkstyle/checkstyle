/*
OpenjdkLineWrapping


*/

package com.puppycrawl.tools.checkstyle.checks.indentation.openjdklinewrapping;

import java.util.stream.IntStream;

public class InputOpenjdkLineWrapping {
    int value;

    void valid(int that, int takes, int a, int lengthy, int list, int of, int arguments,
            int is, int computed, int using, int complex, int expression) {
        int anInteger = method(that, takes,
                a, lengthy, list, of, arguments);
        anInteger = that * (is + computed) / using
                            + a * complex - expression;
        int aligned = method(that,
                             takes,
                             a);
        double chain = IntStream.of(that)
                                .map(Math::abs)
                                .sum();
        value = method(method(that,
                              takes),
                       method(a,
                              of));
        value = method(that,
                takes,
                a);
        value = method(that,
                takes,
                        a);
        value = that
                + takes
                + a;
        value = method(that,
                // comments do not establish indentation anchors
                takes);
        value = method(that,

                takes);
        value = method(
                that,
                takes
        );
        Runnable lambda = () -> {
            value = that
                    + takes;
        };
        Object anonymous = new Object() {
            int inner = that
                    + takes;
        };
    }

    void invalid(int first, int second) {
        value = first
             + second; // violation 'incorrect indentation level 13'
        value = method(first,
                   second); // violation 'incorrect indentation level 19'
        int later = first
                     + second; // violation 'incorrect indentation level 21'
    }

    int method(int... arguments) { return 0; }
}
