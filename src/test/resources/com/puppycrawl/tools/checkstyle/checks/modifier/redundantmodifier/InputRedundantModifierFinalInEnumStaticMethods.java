package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public class InputRedundantModifierFinalInEnumStaticMethods {
    public enum TestEnum {
        VALUE {
           @Override public void someMethodToOverride() {
                // do it differently
            }
         };

        public void someMethodToOverride() { }
        public static final void someStaticMethod() { }    //violation
        public static void someMethod() { }
    }
}

