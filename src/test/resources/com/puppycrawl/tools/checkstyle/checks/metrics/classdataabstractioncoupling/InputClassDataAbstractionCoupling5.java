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
import java.util.Random;
import java.util.stream.Collectors;

public class InputClassDataAbstractionCoupling5 {  // violation, 'Coupling is 2'
    public void method(String... filenames) {
        Random random = new Random();
        final List<File> files = Arrays.stream(filenames)
                .map(File::new)
                .collect(Collectors.toList());
    }
}
