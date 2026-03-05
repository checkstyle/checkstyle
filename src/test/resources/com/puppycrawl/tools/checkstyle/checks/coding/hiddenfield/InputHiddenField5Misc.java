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

class InputHiddenField5Misc {

    // we should ignore this if user wants (ignoreAbstractMethods is true)
    abstract static class InputHiddenFieldBug10845125 {

        String x;

        public abstract void methodA(String x); // violation, ''x' hides a field'
    }

    static class Bug33709465 {

        private int xAxis;

        public void setxAxis(int xAxis) {
            this.xAxis = xAxis;
        }
    }

    /** Tests setter for one letter field (issue #730). */
    static class OneLetterField5 {

        int i;

        void setI(int i) {
            this.i = i;
        }

        enum Inner {}
    }

    static class DuplicateFieldFromPreviousClass5 {

        public void method() {
            int i = 0;
        }
    }

    static class NestedEnum5 {

        enum Test {
            A, B, C;
            int i;
        }

        void method(int i) {}
    }
}
