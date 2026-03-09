/*
StringLiteralEquality


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.coding.stringliteralequality;

public class InputStringLiteralEqualityCheckTextBlocks {
    void method() {
        String status1 = "pending";

        boolean flag1 = (status1 == "done");
        // violation above 'Literal Strings should be compared using equals(), not '=='.'
        boolean flag2 = (status1.equals("done"));


        String status2 = """
                pending""";

        if (status2 == """
                done""") {}
        // violation 2 lines above 'Literal Strings should be compared using equals(), not '=='.'
        while (status2 != """
                done""") {}
        // violation 2 lines above 'Literal Strings should be compared using equals(), not '!='.'
        boolean flag3 = (status2 == """
                done""");
        // violation 2 lines above 'Literal Strings should be compared using equals(), not '=='.'
        boolean flag4 = (status2.equals("""
                done"""));

    }
}
