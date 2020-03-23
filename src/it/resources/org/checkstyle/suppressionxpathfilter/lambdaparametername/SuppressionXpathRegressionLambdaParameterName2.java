package org.checkstyle.suppressionxpathfilter.lambdaparametername;

import java.util.function.Function;

public class SuppressionXpathRegressionLambdaParameterName2 {
    void test() {
        Function<String, String> trimmer = (s) -> s.trim(); // warn
    }
}
