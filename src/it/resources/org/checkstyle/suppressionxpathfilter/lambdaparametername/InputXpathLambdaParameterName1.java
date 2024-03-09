package org.checkstyle.suppressionxpathfilter.lambdaparametername;

import java.util.function.Function;

public class InputXpathLambdaParameterName1 {
    void test() {
        Function<String, String> trimmer = S -> S.trim(); // warn
    }
}
