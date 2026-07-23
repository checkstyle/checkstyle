/*
HiddenField
ignoreFormat = ^i.*$
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF,PARAMETER_DEF,CLASS_DEF,ENUM_DEF,ENUM_CONSTANT_DEF, \
          PATTERN_VARIABLE_DEF,LAMBDA,RECORD_DEF,RECORD_COMPONENT_DEF,COMPACT_COMPILATION_UNIT

*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

class InputHiddenField3Misc {
    abstract class InputHiddenFieldBug10845123 {
        String x;
        public abstract void methodA(String x); // violation ''x' hides a field'
    }

    class Bug33709463 {
        private int xAxis;
        public void setxAxis(int xAxis) { // violation ''xAxis' hides a field'
            this.xAxis = xAxis;
        }
    }

    class OneLetterField3 {
        int i;
        void setI(int i) {
            this.i = i;
        }
        enum Inner {}
    }

    class DuplicateFieldFromPreviousClass3 {
        public void method() {
            int i = 0;
        }
    }

    class NestedEnum3 {
        enum Test {
            A, B, C;
            int i;
        }
        void method(int i) {}
    }
}
