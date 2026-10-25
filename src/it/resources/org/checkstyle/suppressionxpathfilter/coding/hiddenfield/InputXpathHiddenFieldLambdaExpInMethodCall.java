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

package org.checkstyle.suppressionxpathfilter.coding.hiddenfield;

import java.util.Arrays;
import java.util.List;

public class InputXpathHiddenFieldLambdaExpInMethodCall {
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
    Integer value = Integer.valueOf(1);
    {
        // violation below ''value' hides a field.'
        numbers.forEach((Integer value) -> String.valueOf(value)); //warn
    }
}
