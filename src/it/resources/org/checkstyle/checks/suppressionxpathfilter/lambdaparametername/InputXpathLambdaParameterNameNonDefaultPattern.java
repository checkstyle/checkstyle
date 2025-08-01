package org.checkstyle.checks.suppressionxpathfilter.lambdaparametername;

import java.util.function.Function;

public class InputXpathLambdaParameterNameNonDefaultPattern {
    void test() {
        Function<String, String> trimmer = (s) -> s.trim(); // warn
    }
}
