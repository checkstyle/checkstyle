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

public class InputHiddenFieldRecordComponent {
    private static int count;
    private int instanceCount;

    record Inner(int count) { // violation ''count' hides a field'
    }

    record InnerInstance(int instanceCount) { // ok, instance field is not visible in a record
    }

    static class Nested {
        private static int nestedStatic;

        record Deep(int nestedStatic) { // violation ''nestedStatic' hides a field'
        }

        record DeepNoField(int other) { // ok, no field named 'other' in any enclosing type
        }
    }

    record OuterWithStatic(int a) {
        static int shared;

        record InnerShared(int shared) { // violation ''shared' hides a field'
        }
    }

    record NoEnclosingField(int plain) { // ok, no field named 'plain' in any enclosing type
    }
}
