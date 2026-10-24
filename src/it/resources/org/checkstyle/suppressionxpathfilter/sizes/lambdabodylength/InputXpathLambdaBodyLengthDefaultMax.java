/*
LambdaBodyLength
max = (default)10


*/

package org.checkstyle.suppressionxpathfilter.sizes.lambdabodylength;

import java.util.function.Function;

public class InputXpathLambdaBodyLengthDefaultMax {
    void test() {
        // violation below 'Lambda body length is 11 lines (max allowed is 10).'
        Function<String, String> trimmer = (s) ->
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
