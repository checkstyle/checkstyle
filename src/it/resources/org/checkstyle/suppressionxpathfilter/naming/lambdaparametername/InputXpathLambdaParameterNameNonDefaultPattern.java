/*
LambdaParameterName
format = ^_[a-zA-Z0-9]*$


*/

package org.checkstyle.suppressionxpathfilter.naming.lambdaparametername;

import java.util.function.Function;

public class InputXpathLambdaParameterNameNonDefaultPattern {
    void test() {
        // violation below 'Name 's' must match pattern '^_[a-zA-Z0-9]*$'.'
        Function<String, String> trimmer = (s) -> s.trim();
    }
}
