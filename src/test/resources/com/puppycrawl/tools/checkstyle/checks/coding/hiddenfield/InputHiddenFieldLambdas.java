/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF,PARAMETER_DEF,CLASS_DEF,ENUM_DEF,ENUM_CONSTANT_DEF, \
          PATTERN_VARIABLE_DEF,LAMBDA,RECORD_DEF,RECORD_COMPONENT_DEF,COMPACT_COMPILATION_UNIT

*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

import java.lang.Integer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class InputHiddenFieldLambdas {
    /**
     * Example 1: lambda parameter 'value' on line 16
     * hides a field 'value' on line 14.
     */
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
    Integer value = new Integer(1);
    {
        numbers.forEach((Integer value) -> String.valueOf(value));
        // violation above ''value' hides a field'
    }

    /**
     * Example 2: lambda parameter 'name' on line 27
     * does not hide a field 'name' on line 25, because
     * field 'name' can not be referenced from a static context.
     */
    static List<String> firstNames = Arrays.asList("Andrei", "Michal", "Roman", "Vladislav");
    String name = new String();
    static {
        firstNames.forEach((String name) -> String.valueOf(name));
    }

    /**
     * Example 3: lambda parameter 'brand' on line 38 (which type is omitted)
     * does not hide a field 'brand' on line 36, because
     * field 'brand' can not be referenced from a static context.
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
        languageCodes.forEach(languageCode -> String.valueOf(languageCode));
        // violation above ''languageCode' hides a field'
    }

    /**
     * Example 5: lambda parameter 'number' on line 57
     * hides a field 'number' on line 55.
     */
    int number = 1;
    Optional<Object> foo1(int i) {
        return Optional.of(5).map(number -> { // violation ''number' hides a field'
            if (number == 1) return true;
            else if (number == 2) return true;
            else return false;
        });
    }

    /**
     * Example 6: lambda parameter 'id' on line 70
     * hides a field 'id' on line 68.
     */
    static long id = 1;
    Optional<Object> foo2(int i) {
        return Optional.of(5).map(id -> { // violation ''id' hides a field'
            if (id == 1) return true;
            else if (id == 2) return true;
            else return false;
        });
    }
}
