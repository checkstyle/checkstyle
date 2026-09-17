/*
NoLineWrap
tokens = CLASS_DEF, METHOD_DEF, CTOR_DEF, ENUM_DEF, INTERFACE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
skipAnnotations = false

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;
// violation below 'CLASS_DEF statement should not be line-wrapped.'
@SuppressWarnings("unused")
public class InputNoLineWrapSkipAnnotationsFalse {

    // violation below 'CTOR_DEF statement should not be line-wrapped.'
    @Deprecated
    public InputNoLineWrapSkipAnnotationsFalse() {
    }

    // violation below 'METHOD_DEF statement should not be line-wrapped.'
    @SafeVarargs
    public final <T> void foo(T... elements) {

    }

    // violation below 'ENUM_DEF statement should not be line-wrapped.'
    @Deprecated
    public enum InputNoLineWrapSkipAnnotationsFalseEnum {
        FOO
    }

    // violation below 'RECORD_DEF statement should not be line-wrapped.'
    @Deprecated
    public record InputNoLineWrapSkipAnnotationsFalseRecord(String foo) {
        // violation below 'COMPACT_CTOR_DEF statement should not be line-wrapped.'
        @Deprecated
        public InputNoLineWrapSkipAnnotationsFalseRecord {
        }
    }
}
