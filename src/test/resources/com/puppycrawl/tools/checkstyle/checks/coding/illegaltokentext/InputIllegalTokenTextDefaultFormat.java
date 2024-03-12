/*
IllegalTokenText
format = (default)^$
ignoreCase = (default)false
message = (default)
tokens = STRING_LITERAL


*/


package com.puppycrawl.tools.checkstyle.checks.coding.illegaltokentext;

public class InputIllegalTokenTextDefaultFormat {
    public void myTest() {
        String test = "a href";
        String test2 = "A href";
    }
}
