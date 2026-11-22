/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

class InputEqualsAvoidNullInheritedStaticField extends EqualsBaseStatic {

    static String own = "hello";

    boolean inherited() {
        // ok, inherited field type cannot be resolved so it is skipped
        return InputEqualsAvoidNullInheritedStaticField.text.equals("hello");
    }

    boolean direct() {
        // violation below 'String literal expressions should be on the left side of an equals comparison.'
        return InputEqualsAvoidNullInheritedStaticField.own.equals("hello");
    }
}

class EqualsBaseStatic {

    static String text = "hello";
}
