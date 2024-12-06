/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, PATTERN_VARIABLE_DEF, LAMBDA, RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

import java.lang.Integer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * A class demonstrating various examples of lambda parameters hiding fields.
 */
public class InputHiddenFieldLambdas {

  /**
     * Example 1: lambda parameter 'value' on line 16
     * hides a field 'value' on line 14.
     */
  List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
  Integer value = new Integer(1);

  {
    numbers.forEach((Integer value) -> String.valueOf(value)); // violation
  }

  /**
     * Example 2: lambda parameter 'name' on line 27
     * does not hide a field 'name' on line 25, because
     * field 'name' cannot be referenced from a static context.
     */
  static List<String> firstNames = Arrays.asList("Andrei", "Michal", "Roman", "Vladislav");
  String name = new String();

  static {
    firstNames.forEach((String name) -> String.valueOf(name));
  }
  /**
     * Example 3: lambda parameter 'brand' on line 38 (which type is omitted)
     * does not hide a field 'brand' on line 36, because
     * field 'brand' cannot be referenced from a static context.
     */

  static List<String> carBrands = Arrays.asList("BMW", "Mazda", "Volkswagen");
  String brand = new String();

  static {
    carBrands.forEach(brand -> String.valueOf(brand));
  }
  /**
     * Example 4: lambda parameter 'languageCode' on line 48
     * hides a field 'languageCode' on line 46.
     */

  static List<String> languageCodes = Arrays.asList("de", "ja", "fr", "pt");
  static String languageCode = new String();

  {
    languageCodes.forEach(languageCode -> String.valueOf(languageCode)); // violation
  }

  /**
     * Example 5: lambda parameter 'number' on line 57
     * hides a field 'number' on line 55.
     */
  int number = 1;

  Optional<Object> foo1(int i) {
    return Optional.of(5).map(number -> { // violation
      if (number == 1) {
        return true;
      } else if (number == 2) {
        return true;
      } else {
        return false;
      }
    });
  }

  /**
     * Example 6: lambda parameter 'id' on line 70
     * hides a field 'id' on line 68.
     */
  static long id = 1;

  Optional<Object> foo2(int i) {
    return Optional.of(5).map(id -> { // violation
      if (id == 1) {
        return true;
      } else if (id == 2) {
        return true;
      } else {
        return false;
      }
    });
  }


  /**
     * Example 7: lambda parameter 'age' on line 84
     * does not hide a field 'age' on line 82,
     * because field 'age' cannot be referenced from a static context.
     */
  int age = 21;

  static Optional<Object> foo3(int i) {
    return Optional.of(5).map(age -> {
      if (age == 1) {
        return true;
      } else if (age == 2) {
        return true;
      } else {
        return false;
      }
    });
  }
  /**
     * Example 8: lambda parameter 'note' on line 98
     * hides a field 'note' on line 95.
     */

  static String note = new String();

  private void foo4() {
    List<String> acceptableNotes = Arrays.asList("C", "D", "E", "F", "G", "A", "B");
    acceptableNotes.forEach(note -> String.valueOf(note)); // violation
  }
}
