/*
AvoidOutdatedUsage

*/
package com.puppycrawl.tools.checkstyle.checks.coding.avoidoutdatedusage;

import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

public class InputAvoidOutdatedUsageSimple {

    static {
        // _violation below, Avoid ''.collect(Collectors.toList())''.
        toList(); // violation, Avoid using ''.collect(Collectors.toList())''.
        // _violation below, Avoid ''.collect(Collectors.toList())''.
        Collectors.toList(); // violation, Avoid using ''.collect(Collectors.toList())''.
        // Stream.of(FOO).toList()); // ok, as successor
    }

}
