/*
ClassMemberImpliedModifier
violateImpliedStaticOnNestedEnum = (default)true
violateImpliedStaticOnNestedInterface = (default)true
violateImpliedStaticOnNestedRecord = (default)true


*/

//non-compiled with javac: Compilable with Java19
package com.puppycrawl.tools.checkstyle.checks.modifier.classmemberimpliedmodifier;

public class InputClassMemberImpliedModifierRecords {
    public static interface GoodInterface {}
    // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
    public interface BadInterface {} // violation

    public static enum GoodEnum {}
    // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
    public enum BadEnum {} // violation

    public static record GoodRecord() {}
    // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
    public record BadRecord() {} // violation

    public static record OuterRecord() {
        public static record InnerRecord1(){}
        // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
        public record InnerRecord2(){} // violation

        public static interface InnerInterface1 {}
        // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
        public interface InnerInterface2 {} // violation

        public static enum InnerEnum1{}
        // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
        public enum InnerEnum2{} // violation
    }

    Object obj = new Object() {
        // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
        public record BadRecord() {} // violation
        public static record OkRecord() {}
    };
}
