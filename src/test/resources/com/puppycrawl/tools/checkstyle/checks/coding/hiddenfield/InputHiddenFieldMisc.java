/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = (default)false
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

class InputHiddenFieldMisc {
    abstract static class InputHiddenFieldBug10845121 {
        String x;
        public abstract void methodA(String x);
    }

    static class Bug33709461 {
        private int xAxis;

        public void setxAxis(int xAxis) {
            this.xAxis = xAxis;
        }
    }

    /** Tests setter for one letter field (issue #730). */
    static class OneLetterField1 {
        int i;

        void setI(int i) {
            this.i = i;
        }

        enum Inner {}
    }

    static class DuplicateFieldFromPreviousClass1 {
        public void method() {
            int i = 0;
        }
    }

    static class NestedEnum1 {
        enum Test { A, B, C; int i; }

        void method(int i) {}
    }
}
