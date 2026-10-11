/*
ClassMemberImpliedModifier
violateImpliedStaticOnNestedEnum = (default)true
violateImpliedStaticOnNestedInterface = (default)true
violateImpliedStaticOnNestedRecord = (default)true


*/
// non-compiled with javac: Compilable with Java21 individually
// non-compiled with eclipse: syntax error but works fine in jdk
package com.puppycrawl.tools.checkstyle.checks.modifier.classmemberimpliedmodifier;

public class InputClassMemberImpliedModifierRecords {
    public static interface GoodInterface {}
    // violation below 'Implied modifier 'static' should be explicit.'
    public interface BadInterface {}

    public static enum GoodEnum {}
    // violation below 'Implied modifier 'static' should be explicit.'
    public enum BadEnum {}

    public static record GoodRecord() {}
    // violation below 'Implied modifier 'static' should be explicit.'
    public record BadRecord() {}

    public static record OuterRecord() {
        public static record InnerRecord1(){}
        // violation below 'Implied modifier 'static' should be explicit.'
        public record InnerRecord2(){}

        public static interface InnerInterface1 {}
        // violation below 'Implied modifier 'static' should be explicit.'
        public interface InnerInterface2 {}

        public static enum InnerEnum1{}
        // violation below 'Implied modifier 'static' should be explicit.'
        public enum InnerEnum2{}
    }

    Object obj = new Object() {
        // violation below 'Implied modifier 'static' should be explicit.'
        public record BadRecord() {}
        public static record OkRecord() {}
    };
}
