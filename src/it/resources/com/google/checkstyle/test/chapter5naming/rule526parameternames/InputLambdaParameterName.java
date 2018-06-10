package com.google.checkstyle.test.chapter5naming.rule526parameternames;

import java.util.function.BiFunction;
import java.util.function.Function;

public class InputLambdaParameterName {

    Function<String, String> badNamedParameterWithoutParenthesis =
            S -> S.trim().toLowerCase(); // warn

    Function<String, String> badNamedParameterWithParenthesis =
            (sT) -> sT.trim().toLowerCase(); // warn

    BiFunction<String, String, String> twoBadNamedParameters = (sT1, sT2) -> sT1 + sT2; // warn

    BiFunction<String, String, String> badNamedParameterInBiFunction =
            (first, _s) -> first + _s; // warn

    Function<String, Integer> goodNamedParameterWithoutParenthesis =
            notTrimmedString -> notTrimmedString.trim().length();

    Function<String, Integer> goodNamedParameterWithParenthesis =
            (notTrimmedString) -> notTrimmedString.trim().length();

    BiFunction<String, String, Integer> goodNamedParameters =
            (first, second) -> (first + second).length();

}
