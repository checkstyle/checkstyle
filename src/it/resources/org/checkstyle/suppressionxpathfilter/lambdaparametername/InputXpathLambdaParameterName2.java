package org.checkstyle.suppressionxpathfilter.lambdaparametername;

import java.util.function.Function;

public class InputXpathLambdaParameterName2 {
    void test() {
        Function<String, String> trimmer = (s) -> s.trim(); // warn
    }
}
