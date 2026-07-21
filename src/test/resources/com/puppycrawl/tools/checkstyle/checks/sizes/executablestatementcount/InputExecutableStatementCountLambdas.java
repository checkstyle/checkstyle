/*
ExecutableStatementCount
max = 1
tokens = LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.executablestatementcount;

import java.util.function.Consumer;
import java.util.function.Function;

public class InputExecutableStatementCountLambdas {

    // violation below 'Executable statement count is 6'
    Consumer a = (o) -> {
        o.toString(); // 1
        o.toString(); // 2
        o.toString(); // 3
        o.toString(); // 4
        o.toString(); // 5
        o.toString(); // 6
    };

    // violation below 'Executable statement count is 2'
    Consumer b = (x) -> {
        // violation below 'Executable statement count is 2'
        Consumer c = (s) -> {
            String str = s.toString();
            str = str + "!";
        };
        // violation below 'Executable statement count is 4'
        Consumer d = (s) -> {
            String str = s.toString();
            Consumer t = a -> a.toString().trim();
            Function x1 = a -> a;
            Function y = a -> null;
        };
    };
}
