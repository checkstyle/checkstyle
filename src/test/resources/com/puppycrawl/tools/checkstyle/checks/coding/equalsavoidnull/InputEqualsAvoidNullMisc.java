/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

import java.util.function.BiFunction;
import java.util.function.Function;

public enum InputEqualsAvoidNullMisc {
    TEST;

    public void method() {}
}
class Receiver {
    public void foo4(Receiver this, String s) {
        s.equals("abc"); // violation '.* left .* of .* equals .*.'
    }

    private class Inner {
        public Inner(Receiver Receiver.this) {}
    }
}
class Lambda {
    Function<String, Integer> field1 =
            notTrimmedString -> notTrimmedString.trim().length();
    BiFunction<String, String, Integer> field2 =
            (first, second) -> (first + second).length();
}
