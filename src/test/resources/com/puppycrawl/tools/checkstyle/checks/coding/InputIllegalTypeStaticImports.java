package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.utils.CheckUtils.isElseIf;
import static com.puppycrawl.tools.checkstyle.utils.CheckUtils.*;
import static com.puppycrawl.tools.checkstyle.checks.coding.InputIllegalType.SomeStaticClass;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
//configuration: "illegalClassNames": SomeStaticClass
public class InputIllegalTypeStaticImports
{
     private boolean foo(DetailAST ast) {
         return isElseIf(ast);
     }
     SomeStaticClass staticClass; //WARNING
     private static SomeStaticClass foo1() { return null;}
     private static void foo2(SomeStaticClass s) {}
}
