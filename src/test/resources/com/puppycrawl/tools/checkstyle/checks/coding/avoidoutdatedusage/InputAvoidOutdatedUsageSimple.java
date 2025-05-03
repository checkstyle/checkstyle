/*
AvoidOutdatedUsage

*/
package com.puppycrawl.tools.checkstyle.checks.coding.avoidoutdatedusage;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class InputAvoidOutdatedUsageSimple {

    static String FOO = "foo";

    static {
        toList(); // violation, Outdated api usage 'Collectors.toList()'
        Collectors.toList(); // violation, Outdated api usage 'Collectors.toList()'
        Stream.of(FOO).collect(Collectors.toList()); // violation, Outdated api usage 'Collectors.toList()'
        // Stream.of(FOO).toList()); // ok
    }

}
