// non-compiled with javac: Compilable with Java17

package com.google.checkstyle.test.chapter4formatting.rule42blockindentation;

import java.util.Optional;

/**some javadoc.*/
public class InputSwitchExpressionInLambda {
  String testMethod1(Optional<Integer> opt) {
    return opt.map(
        n ->
            switch (n) {
              case 1 -> "Test";
              default -> "Default";
            })
        .orElse("");
  }

  String testMethod2(Optional<Integer> opt) {
    return opt.map(
        n -> {
          return switch (n) {
            case 1 -> "Test";
            default -> "Default";
          };
        })
        .orElse("");
  }
  
  // Methods with indentation violations
  String testMethod3Invalid(Optional<Integer> opt) {
    return opt.map(
        n ->
      switch (n) {       // violation '.* incorrect indentation level 6, expected .* 12.'
        case 1 -> "Test"; // violation '.* incorrect indentation level 8, expected .* 14.'
        default -> "Default"; // violation '.* incorrect indentation level 8, expected .* 14.'
      })                // violation '.* incorrect indentation level 6, expected .* 12.'
        .orElse("");
  }
  
  String testMethod4Invalid(Optional<Integer> opt) {
    return opt.map(
        n ->
          switch (n) {         // violation '.* incorrect indentation level 10, expected .* 12.'
            case 1 -> "Test";   // violation '.* incorrect indentation level 12, expected .* 14.'
            default -> "Default"; // violation '.* incorrect indentation level 12, expected .* 14.'
        })                // violation '.* incorrect indentation level 8, expected .* 12.'
        .orElse("");
  }
  
  String testMethod5Invalid(Optional<Integer> opt) {
    return opt.map(
        n ->
                  switch (n) {     // violation '.* incorrect indentation level 18, expected .* 12.'
                    case 1 -> "Test"; // violation '.* incorrect indentation level 20, expected .* 14.'
                    default -> "Default"; // violation '.* incorrect indentation level 20, expected .* 14.'
                  })              // violation '.* incorrect indentation level 18, expected .* 12.'
        .orElse("");
  }
} 