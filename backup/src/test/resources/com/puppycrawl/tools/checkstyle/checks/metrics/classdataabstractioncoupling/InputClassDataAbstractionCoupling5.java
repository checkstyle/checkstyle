/*
ClassDataAbstractionCoupling
max = 0
excludedClasses =
excludeClassesRegexps =
excludedPackages = (default)


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.classdataabstractioncoupling;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputClassDataAbstractionCoupling5 {
    public void method(String... filenames) {
        final List<File> files = Arrays.stream(filenames)
                .map(File::new) // ok
                .collect(Collectors.toList());
    }
}
