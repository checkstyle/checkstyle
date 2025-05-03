/*
AvoidOutdatedUsage

*/
package com.puppycrawl.tools.checkstyle.checks.coding.avoidoutdatedusage;

import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

public class InputAvoidOutdatedUsageSimple {

    static String FOO = "foo";

    static {
        toList(); // violation, Avoid outdated api ''.collect(Collectors.toList()) -> .toList()''.
        Collectors.toList(); // violation, Avoid outdated api ''.collect(Collectors.toList()) -> .toList()''.
        Stream.of(FOO).collect(Collectors.toList()); // violation, Avoid outdated api ''.collect(Collectors.toList()) -> .toList()''.
        // Stream.of(FOO).toList()); // ok
    }

}
