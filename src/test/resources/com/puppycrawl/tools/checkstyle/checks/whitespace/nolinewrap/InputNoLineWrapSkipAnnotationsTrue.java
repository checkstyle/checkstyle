/*
NoLineWrap
tokens = CLASS_DEF, METHOD_DEF, CTOR_DEF, ENUM_DEF, INTERFACE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
skipAnnotations = (default)true

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nolinewrap;
@SuppressWarnings("unused")
public class InputNoLineWrapSkipAnnotationsTrue {

    @Deprecated
    public InputNoLineWrapSkipAnnotationsTrue() {
    }

    @SafeVarargs
    public final <T> void foo(T... elements) {

    }

    // violation below 'METHOD_DEF statement should not be line-wrapped.'
    @SafeVarargs
    public final <T>
      void bar(T... elements) {

    }

    @Deprecated
    public enum InputNoLineWrapAnnotationsEnum {
        FOO
    }

    @Deprecated
    public record InputNoLineWrapAnnotationRecord(String foo) {
        @Deprecated
        public InputNoLineWrapAnnotationRecord {
        }
    }
}
