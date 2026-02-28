/*
HiddenField
ignoreFormat = (default)null
ignoreConstructorParameter = (default)false
ignoreSetter = true
setterCanReturnItsClass = (default)false
ignoreAbstractMethods = (default)false
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, PATTERN_VARIABLE_DEF, LAMBDA, RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.hiddenfield;

class InputHiddenField4Misc {

    // we should ignore this if user wants (ignoreAbstractMethods is true)
    abstract static class InputHiddenFieldBug10845124 {
        String x;

        public abstract void methodA(String x); // violation, ''x' hides a field'
    }

    static class Bug33709464 {
        private int xAxis;

        public void setxAxis(int xAxis) {
            this.xAxis = xAxis;
        }
    }

    /** tests chain-setter */
    static class PropertySetter34 {

        private int prop;

        /**
         * if setterCanReturnItsClass == false then
         *     violation - not a void method
         *
         * if setterCanReturnItsClass == true then
         *     success as it is then considered to be a setter
         */
        public PropertySetter34 setProp(int prop) // violation, ''prop' hides a field'
        {
            this.prop = prop;
            return this;
        }
    }

    /** tests setters (both regular and the chain one) on the enum */
    enum PropertySetter44 {
        INSTANCE;

        private int prop;
        private int prop2;

        public void setProp(int prop) {
            this.prop = prop;
        }

        /**
         * if setterCanReturnItsClass == false then
         *     violation - not a void method
         *
         * if setterCanReturnItsClass == true then
         *     success as it is then considered to be a setter
         */
        public PropertySetter44 setProp2(int prop2) // violation, ''prop2' hides a field'
        {
            this.prop2 = prop2;
            return this;
        }
    }

    /** Tests setter for one letter field (issue #730). */
    static class OneLetterField4 {

        int i;

        void setI(int i) {
            this.i = i;
        }

        enum Inner {}
    }

    static class DuplicateFieldFromPreviousClass4 {

        public void method() {
            int i = 0;
        }
    }

    static class NestedEnum4 {

        enum Test {
            A, B, C;
            int i;
        }

        void method(int i) {}
    }
}
