/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public @interface InputRequireThisAnnotationFieldDefaultValue {

    Object CONST = new Object() {
        @Override
        public String toString() {
            return "ok";
        }
    };

}
