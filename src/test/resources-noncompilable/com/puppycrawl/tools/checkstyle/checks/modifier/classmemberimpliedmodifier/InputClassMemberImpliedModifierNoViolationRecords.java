//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.modifier.classmemberimpliedmodifier;
/* Config:
 * violateImpliedStaticOnNestedEnum = true
 * violateImpliedStaticOnNestedInterface = true
 * violateImpliedStaticOnNestedRecord = false
 */
public class InputClassMemberImpliedModifierNoViolationRecords {
    public static interface GoodInterface {} // OK
    // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
    public interface BadInterface {}

    public static enum GoodEnum {} // OK
    // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
    public enum BadEnum {}

    public static record GoodRecord() {} // OK

    public record BadRecord() {} // ok

    public static record OuterRecord() {
        public static record InnerRecord1(){} // ok

        public record InnerRecord2(){} // ok

        public static interface InnerInterface1 {} // ok
        // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
        public interface InnerInterface2 {}

        public static enum InnerEnum1{} // ok
        // Implied modifier 'static' should be explicit. [ClassMemberImpliedModifier]
        public enum InnerEnum2{}
    }
}
