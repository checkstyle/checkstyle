/*
InlineVariable

*/
package com.puppycrawl.tools.checkstyle.checks.coding.inlinevariable;

import java.io.PrintStream;

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

    private PrintStream used222out() {
        String inlineVariable1 = used2();
        String inlineVariable2 = used2();
        PrintStream out = System.out; // violation, Inline 'out' for immediate return or throw.
        return out;
    }

//    private PrintStream used222outUsed() {
//        String inlineVariable1 = used2();
//        String inlineVariable2 = used2();
//        PrintStream out = System.out;
//        out.println("out");
//        return out;
//    }

    private void used3() {
        String inlineVariable1 = used2();
        String inlineVariable2 = used2();
        String inlineVariable = used2();
        // test comment
    }

    private String used() {
        String inlineVariable1 = used2();
        String inlineVariable2 = used2();
        String inlineVariable3 = used2();
        String in = used2(); // violation, Inline 'in' for immediate return or throw.
        return in;
    }

    private String usedThrow() {
        String inlineVariable1 = used2();
        String inlineVariable2 = used2();
        String inlineVariable3 = used2();
        String throw_ = used2(); // violation, Inline 'throw_' for immediate return or throw.
        throw new RuntimeException(throw_);
    }

    private String used2() {
        return "String";
    }

}
