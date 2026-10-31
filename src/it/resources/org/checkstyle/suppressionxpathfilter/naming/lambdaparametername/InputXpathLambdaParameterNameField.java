/*
LambdaParameterName
format = (default)^([a-z][a-zA-Z0-9]*|_)$


*/

package org.checkstyle.suppressionxpathfilter.naming.lambdaparametername;

import java.util.function.Function;

public class InputXpathLambdaParameterNameField {
    // violation below 'Name 'S' must match pattern '^([a-z][a-zA-Z0-9]*|_)$'.'
    Function<String, String> trimmer = S -> S.trim();
}
