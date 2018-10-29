package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

import java.util.function.BiFunction;
import java.util.function.Function;

/*
 * No violation are expected when checking this file by ParameterNameCheck as lambda parameters
 * are check by LambdaParameterNameCheck.
 */

public class InputParameterNameLambda {

    Function<String, String> badNamedParameterWithoutParenthesis = s -> s.trim().toLowerCase();

    Function<String, String> badNamedParameterWithParenthesis = (st) -> st.trim().toLowerCase();

    BiFunction<String, String, String> twoBadNamedParameters = (s1, s2) -> s1 + s2;

    BiFunction<String, String, String> badNamedParameterInBiFunction = (first, s) -> first + s;

    Function<String, Integer> goodNamedParameterWithoutParenthesis =
            notTrimmedString -> notTrimmedString.trim().length();

    Function<String, Integer> goodNamedParameterWithParenthesis =
            (notTrimmedString) -> notTrimmedString.trim().length();

    BiFunction<String, String, Integer> goodNamedParameters =
            (first, second) -> (first + second).length();

}
