/*
ClassMemberImpliedModifier
violateImpliedStaticOnNestedEnum = (default)true
violateImpliedStaticOnNestedInterface = (default)true
violateImpliedStaticOnNestedRecord = false


*/

//non-compiled with javac: Compilable with Java19
package com.puppycrawl.tools.checkstyle.checks.modifier.classmemberimpliedmodifier;

public class InputClassMemberImpliedModifierNoViolationRecords {
    public static interface GoodInterface {}
    // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
    public interface BadInterface {} // violation

    public static enum GoodEnum {}
    // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
    public enum BadEnum {} // violation

    public static record GoodRecord() {}

    public record BadRecord() {}

    public static record OuterRecord() {
        public static record InnerRecord1(){}

        public record InnerRecord2(){}

        public static interface InnerInterface1 {}
        // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
        public interface InnerInterface2 {} // violation

        public static enum InnerEnum1{}
        // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
        public enum InnerEnum2{} // violation
    }

    Object obj = new Object() {
        public record BadRecord() {}
        public static record OkRecord() {}
    };
}
