package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class handling examples for lambda expressions with hidden fields.
 */
public class InputHiddenFieldLambdas4 {

  /**
     * Example 14: lambda typed with complex generics.
     */
  List<Double> justSomeList;
  Map<String, Object> justSomeMap;

  {
    FunctionWithComplexGenerics<List<Double>, Map<String, Object>> someWierdFunc =
                (List<Double> justSomeList, Map<String, Object> justSomeMap) -> { // 2 violations
                  String.valueOf(justSomeList);
                  String.valueOf(justSomeMap);
                  return new HashMap<>();
                };
  }

  /**
     * Example 15: lambda stored in field (with typed parameter)
     * hides other field.
     */

  Object someObject = new Object();
  FunctionWithOneParameter objectToString = (Object someObject) -> { // violation
    return someObject.toString();
  };

  /**
     * Example 16: lambda stored in field (with untyped parameter)
     * hides other field.
     */
  FunctionWithOneParameter otherObjectToString = someObject -> { // violation
    return someObject.toString();
  };

  private final String paramL = "";

  private interface NestedInterface {
    void print(String paramL);  // Renamed 'l' to 'paramL' to follow naming conventions
  }
}
