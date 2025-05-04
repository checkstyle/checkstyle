/*
AvoidOutdatedUsage

*/
package com.puppycrawl.tools.checkstyle.checks.coding.avoidoutdatedusage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class InputAvoidOutdatedUsageForCollectors {

    static String FOO = "foo";

    private static void Collectors() {
        // TODO Usage of API documented as @since 16+
        // Stream.of(FOO).toList(); // ok, as modern
        Stream.of(FOO).collect(
        Collectors.toList()); // violation, Avoid ''.collect(Collectors.toList()) -> .toList()''.
        // Can be replaced with 'java.util.ArrayList' constructor
        new ArrayList<>(List.of(FOO)); // ok, as modern
        Collector<Object, ?, List<Object>> list =
            Collectors.toList(); // violation, Avoid ''.collect(Collectors.toList()) -> .toList()''.
        toList(); // violation, Avoid ''.collect(Collectors.toList()) -> .toList()''.
        // TODO Usage of API documented as @since 16+
        // List.of(FOO).stream().toList(); // ok, as modern
        // Stream.of(FOO).toList(); // ok, as modern
        // Test other Collector methods
        Collectors.toCollection(ArrayList::new); // ok, modern API
        // FIXME: but was       : [22, 23, 26, 27, 28, 35]
        // Foo.toList(); // ok, as custom
    }

    static class Foo {
        public static Collector<Object, ?, List<Object>> toList() {
            return toList(); // violation, Avoid ''.collect(Collectors.toList()) -> .toList()''.
        }
    }
}
