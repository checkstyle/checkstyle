/*
MatchXpath
query = //METHOD_DEF[./IDENT[@text='wrongName' or @text='nonExistingMethod']]


*/

package com.puppycrawl.tools.checkstyle.checks.coding.matchxpath;

public class InputMatchXpath4 {
    public void test() { }

    public void foo() { }

    public void correct() { }
}
