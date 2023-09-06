package org.checkstyle.suppressionxpathfilter.lambdaparametername;

import java.util.function.Function;

public class SuppressionXpathRegressionLambdaParameterName1 {
    void test() {
        Function<String, String> trimmer = S -> S.trim(); // warn
    }
}
