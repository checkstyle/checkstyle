/*
No config


*/

package com.puppycrawl.tools.checkstyle.checks.imports.avoidstaticimport;

public class InputAvoidStaticImportNestedClass{ // ok
    public static Integer zero=0;

    public static class InnerClass {
        public static Integer one=1;
    }
}
