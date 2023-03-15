/*
StringLiteralEquality


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.stringliteralequality;

public class InputStringLiteralEqualityCheckTextBlocks {
    void method() {
        String status1 = "pending";

        boolean flag1 = (status1 == "done"); // violation

        boolean flag2 = (status1.equals("done")); // ok


        String status2 = """
                pending""";

        if (status2 == """
                done""") {} // violation above

        while (status2 != """
                done""") {} // violation above

        boolean flag3 = (status2 == """
                done"""); // violation above

        boolean flag4 = (status2.equals("""
                done""")); // ok

    }
}
