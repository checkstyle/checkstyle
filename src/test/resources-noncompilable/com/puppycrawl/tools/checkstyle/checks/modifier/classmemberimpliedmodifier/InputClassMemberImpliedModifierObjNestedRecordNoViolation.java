/*
ClassMemberImpliedModifier
violateImpliedStaticOnNestedEnum = (default)true
violateImpliedStaticOnNestedInterface = (default)true
violateImpliedStaticOnNestedRecord = (default)true


*/

//non-compiled with javac: Compilable with Java16
package com.puppycrawl.tools.checkstyle.checks.modifier.classmemberimpliedmodifier;

public class InputClassMemberImpliedModifierObjNestedRecordNoViolation {
    Object obj = new Object() {
        public static record BadRecord() {} // ok
    };
}
