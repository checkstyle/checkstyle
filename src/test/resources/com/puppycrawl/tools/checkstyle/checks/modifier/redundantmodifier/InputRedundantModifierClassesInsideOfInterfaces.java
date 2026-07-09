/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public interface InputRedundantModifierClassesInsideOfInterfaces {

    // Class inside of interface can be abstract and non abstract, but always public static.
    abstract class A {}

    class B {}

    // All classes inside interfaces are public static classes, and static modifier is redundant.
    static class C { // violation 'Redundant 'static' modifier.'
        public static boolean verifyState( A a ) {
            return true;
        }
    }

    public class E {} // violation 'Redundant 'public' modifier.'

    // Enums are static implicit subclasses of Enum class.
    public enum Role1 { // violation 'Redundant 'public' modifier.'
        ADMIN,
        EDITOR,
        VANILLA;
    }

    static enum Role2 { // violation 'Redundant 'static' modifier.'
        ADMIN,
        EDITOR,
        VANILLA;
    }
}
