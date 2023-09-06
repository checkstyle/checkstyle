/*
LambdaParameterName
format = ^(id)|([a-z][a-z0-9][a-zA-Z0-9]+)$


*/

package com.puppycrawl.tools.checkstyle.checks.naming.lambdaparametername;

import java.util.function.BiFunction;
import java.util.function.Function;

public class InputLambdaParameterName {

    Function<String, String> badNamedParameterWithoutParenthesis = s -> // violation
            s.trim().toLowerCase();

    Function<String, String> badNamedParameterWithParenthesis = (st) -> // violation
            st.trim().toLowerCase();

    BiFunction<String, String, String> twoBadNamedParameters = (s1, // violation
                                                                s2) -> s1 + s2; // violation

    BiFunction<String, String, String> badNamedParameterInBiFunction =
            (first, s) -> first + s; // violation

    Function<String, Integer> goodNamedParameterWithoutParenthesis =
            notTrimmedString -> notTrimmedString.trim().length();

    Function<String, Integer> goodNamedParameterWithParenthesis =
            (notTrimmedString) -> notTrimmedString.trim().length();

    BiFunction<String, String, Integer> goodNamedParameters =
            (first, second) -> (first + second).length();

}
