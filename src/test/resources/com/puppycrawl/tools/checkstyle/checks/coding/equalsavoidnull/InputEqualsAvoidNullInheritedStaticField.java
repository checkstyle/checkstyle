/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

class InputEqualsAvoidNullInheritedStaticField extends EqualsBaseStatic {
    boolean matches() {
        // ok, inherited field type cannot be resolved so it is skipped
        return InputEqualsAvoidNullInheritedStaticField.text.equals("hello");
    }
}

class EqualsBaseStatic {
    static String text = "hello";
}
