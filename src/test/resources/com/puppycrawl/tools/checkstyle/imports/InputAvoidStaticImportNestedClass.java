package com.puppycrawl.tools.checkstyle.imports;

public class InputAvoidStaticImportNestedClass{
    public static Integer zero=0;
    
    public static class InnerClass {
        public static Integer one=1;
    }
}
