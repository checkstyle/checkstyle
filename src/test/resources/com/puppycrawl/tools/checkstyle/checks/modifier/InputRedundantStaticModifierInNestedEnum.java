package com.puppycrawl.tools.checkstyle.checks.modifier;

public class InputRedundantStaticModifierInNestedEnum {
    static enum NestedEnumWithRedundantStatic {} // violation

    enum CorrectNestedEnum {
        VAL;
        static enum NestedEnumWithRedundantStatic {} // violation
    }
    
    interface NestedInterface {
        static enum NestedEnumWithRedundantStatic {} // violation
    }
}
