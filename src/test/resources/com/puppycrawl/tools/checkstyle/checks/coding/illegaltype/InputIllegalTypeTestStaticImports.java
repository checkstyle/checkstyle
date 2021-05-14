package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import static com.puppycrawl.tools.checkstyle.utils.CheckUtil.isElseIf;
import static com.puppycrawl.tools.checkstyle.utils.CheckUtil.*;
import static com.puppycrawl.tools.checkstyle.checks.coding.illegaltype.InputIllegalType.SomeStaticClass;
import java.lang.String;

/*
 * Config:
 * illegalClassNames = { SomeStaticClass }
 * ignoredMethodNames = { foo1 }
 */
public class InputIllegalTypeTestStaticImports
{
     private boolean foo(String s) {
         return isElseIf(null);
     }
     SomeStaticClass staticClass; // violation
     private static SomeStaticClass foo1() { return null;}
     private static void foo2(SomeStaticClass s) {} // violation
}
