/*
ClassMemberImpliedModifier
violateImpliedStaticOnNestedEnum = (default)true
violateImpliedStaticOnNestedInterface = (default)true
violateImpliedStaticOnNestedRecord = (default)true


*/

//non-compiled with javac: Compilable with Java16
package com.puppycrawl.tools.checkstyle.checks.modifier.classmemberimpliedmodifier;

public class InputClassMemberImpliedModifierObjNestedRecord {
    Object obj = new Object() {
        public record BadRecord() {} // violation
    };
}
