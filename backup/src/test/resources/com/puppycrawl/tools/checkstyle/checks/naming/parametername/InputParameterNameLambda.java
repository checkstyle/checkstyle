/*
ParameterName
format = (default)^[a-z][a-zA-Z0-9]*$
ignoreOverridden = (default)false
accessModifiers = (default)public, protected, package, private


*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

import java.util.function.BiFunction;
import java.util.function.Function;

/* Config: default */

/*
 * No violation are expected when checking this file by ParameterNameCheck as lambda parameters
 * are checked by LambdaParameterNameCheck.
 */

public class InputParameterNameLambda {

    Function<String, String> badNamedParameterWithoutParenthesis = s -> s.trim().toLowerCase();

    Function<String, String> badNamedParameterWithParenthesis = (st) -> st.trim().toLowerCase();

    BiFunction<String, String, String> twoBadNamedParameters = (s1, s2) -> s1 + s2; // ok

    BiFunction<String, String, String> badNamedParameterInBiFunction = (first, s) -> first + s;

    Function<String, Integer> goodNamedParameterWithoutParenthesis = // ok
            notTrimmedString -> notTrimmedString.trim().length(); // ok

    Function<String, Integer> goodNamedParameterWithParenthesis = // ok
            (notTrimmedString) -> notTrimmedString.trim().length(); // ok

    BiFunction<String, String, Integer> goodNamedParameters = // ok
            (first, second) -> (first + second).length(); // ok

}
