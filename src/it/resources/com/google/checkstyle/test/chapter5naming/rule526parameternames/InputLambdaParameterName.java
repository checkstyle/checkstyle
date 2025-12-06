package com.google.checkstyle.test.chapter5naming.rule526parameternames;

import java.util.function.BiFunction;
import java.util.function.Function;

/** Some javadoc. */
public class InputLambdaParameterName {

  Function<String, String> badNamedParameterWithoutParenthesis =
      S -> S.trim().toLowerCase(); // violation 'Lambda parameter name 'S' must match pattern'

  Function<String, String> badNamedParameterWithParenthesis = (sT) -> sT.trim().toLowerCase();
  // violation above 'Lambda parameter name 'sT' must match pattern'

  BiFunction<String, String, String> twoBadNamedParameters = (sT1, sT2) -> sT1 + sT2;
  // 2 violations above:
  //                    'Lambda parameter name 'sT1' must match pattern'
  //                    'Lambda parameter name 'sT2' must match pattern'

  BiFunction<String, String, String> badNamedParameterInBiFunction =
      (first, _s) -> first + _s; // violation 'Lambda parameter name '_s' must match pattern'

  Function<String, Integer> goodNamedParameterWithoutParenthesis =
      notTrimmedString -> notTrimmedString.trim().length();

  Function<String, Integer> goodNamedParameterWithParenthesis =
      (notTrimmedString) -> notTrimmedString.trim().length();

  BiFunction<String, String, Integer> goodNamedParameters =
      (first, second) -> (first + second).length();
}
