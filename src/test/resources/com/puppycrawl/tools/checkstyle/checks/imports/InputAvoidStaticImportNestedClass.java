package com.puppycrawl.tools.checkstyle.checks.imports;

public class InputAvoidStaticImportNestedClass{
    public static Integer zero=0;
    
    public static class InnerClass {
        public static Integer one=1;
    }
}
