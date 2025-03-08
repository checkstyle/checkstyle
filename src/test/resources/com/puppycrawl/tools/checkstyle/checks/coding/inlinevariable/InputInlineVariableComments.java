/*
InlineVariable

*/
package com.puppycrawl.tools.checkstyle.checks.coding.inlinevariable;

public class InputInlineVariableComments {

    private String used22() {
        String inlineVariable1 = used2();
        String inlineVariable2 = used2();
        String inlineVariable = used2();
        String inlineVariable3 = used2();
        return inlineVariable;
    }

    private String used222() {
        String inlineVariable1 = used2();
        String inlineVariable2 = used2();
        String inlineVariable = used2();
        // test comment
        return inlineVariable;
    }

    private String used() {
        String inlineVariable1 = used2();
        String inlineVariable2 = used2();
        String inlineVariable3 = used2();
        String in = used2(); // violation, Inline1 variable 'in' for immediate return or throw.
        return in;
    }

    private String used2() {
        return "String";
    }

}
