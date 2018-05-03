package com.google.checkstyle.test.chapter5naming.rule526parameternames;

import java.util.function.BiFunction;
import java.util.function.Function;

public class InputParameterNameInLambda {

    Function<String, String> badNamedParameterWithoutParenthesis = sT -> sT.trim().toLowerCase(); // warn

    Function<String, String> badNamedParameterWithParenthesis = (sT) -> sT.trim().toLowerCase(); // warn

    BiFunction<String, String, String> twoBadNamedParameters = (sT1, sT2) -> sT1 + sT2; // warn

    BiFunction<String, String, String> badNamedParameterInBiFunction = (first, sT) -> first + sT;  // warn

    Function<String, Integer> goodNamedParameterWithoutParenthesis =
            notTrimmedString -> notTrimmedString.trim().length();

    Function<String, Integer> goodNamedParameterWithParenthesis =
            (notTrimmedString) -> notTrimmedString.trim().length();

    BiFunction<String, String, Integer> goodNamedParameters = (first, second) -> (first + second).length();

}
