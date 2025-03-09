/*
InlineVariable

*/
package com.puppycrawl.tools.checkstyle.checks.coding.inlinevariable;

public class InputInlineVariableSimple {
    private String used() {
        String inlineVariable1 = used2();
        String inlineVariable2 = used2();
        String inlineVariable3 = used2();
        String in = used2(); // violation, Inline 'in' for immediate return or throw.
        return in;
    }

    private String used2() {
        return "String";
    }

}
