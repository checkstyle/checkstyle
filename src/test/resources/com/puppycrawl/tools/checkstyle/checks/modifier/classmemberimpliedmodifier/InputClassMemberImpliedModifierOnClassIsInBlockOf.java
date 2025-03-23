/*
ClassMemberImpliedModifier
violateImpliedStaticOnNestedEnum = (default)true
violateImpliedStaticOnNestedInterface = (default)true
violateImpliedStaticOnNestedRecord = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.classmemberimpliedmodifier;

interface TopLevelInterface {}
public class InputClassMemberImpliedModifierOnClassIsInBlockOf {

    static class OuterClass {
        interface NestedInterface {} // violation
    }

    static class OuterClass2 {
        static class InnerClass {
            interface DeeplyNestedInterface {} // violation
        }
    }
}
