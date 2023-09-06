/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = true
setterCanReturnItsClass = true
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, PATTERN_VARIABLE_DEF, LAMBDA, RECORD_COMPONENT_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

import java.util.Arrays;
import java.util.List;

public class InputHiddenFieldLambdas2 {
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
    Integer value = Integer.valueOf(1);
    {
        numbers.forEach((Integer value) -> String.valueOf(value)); // violation
    }
}
