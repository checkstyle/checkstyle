/*
ClassMemberImpliedModifier
violateImpliedStaticOnNestedEnum = (default)true
violateImpliedStaticOnNestedInterface = (default)true
violateImpliedStaticOnNestedRecord = (default)true


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.modifier.classmemberimpliedmodifier;

public class InputClassMemberImpliedModifierRecords {
    public static interface GoodInterface {} // OK
    // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
    public interface BadInterface {} // violation

    public static enum GoodEnum {} // OK
    // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
    public enum BadEnum {} // violation

    public static record GoodRecord() {} // OK
    // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
    public record BadRecord() {} // violation

    public static record OuterRecord() {
        public static record InnerRecord1(){} // ok
        // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
        public record InnerRecord2(){} // violation

        public static interface InnerInterface1 {} // ok
        // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
        public interface InnerInterface2 {} // violation

        public static enum InnerEnum1{} // ok
        // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
        public enum InnerEnum2{} // violation
    }
}
