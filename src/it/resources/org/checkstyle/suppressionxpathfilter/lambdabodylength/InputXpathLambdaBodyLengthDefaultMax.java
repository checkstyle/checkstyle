package org.checkstyle.suppressionxpathfilter.lambdabodylength;

import java.util.function.Function;

public class InputXpathLambdaBodyLengthDefaultMax {
    void test() {
        Function<String, String> trimmer = (s) -> // warn
            s.trim()
             .trim()
             .trim()
             .trim()
             .trim()
             .trim()
             .trim()
             .trim()
             .trim()
             .trim()
             .trim();
    }
}
