/*
RedundantModifier
jdkVersion = (default)22
tokens = (default)METHOD_DEF, VARIABLE_DEF, ANNOTATION_FIELD_DEF, INTERFACE_DEF, \
         CTOR_DEF, CLASS_DEF, ENUM_DEF, RESOURCE, ANNOTATION_DEF, RECORD_DEF, \
         PATTERN_VARIABLE_DEF, LITERAL_CATCH, LAMBDA

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.redundantmodifier;

public abstract class InputRedundantModifierFinalInAbstractMethods {
    // violation below 'Redundant 'final' modifier.'
    public abstract void method(final String param);

    public abstract void method2(String param);

    // violation below 'Redundant 'final' modifier.'
    public abstract void method3(String param1, final String param2);
}
interface IWhatever {
    // violation below 'Redundant 'final' modifier.'
    void method(final String param);

    void method2(String param);
}
class CWhatever {
    // violation below 'Redundant 'final' modifier.'
    native void method(final String param);

    native void method2(String param);
}
enum EWhatever {
    TEST() {
        public void method(String s) {};
    };

    // violation below 'Redundant 'final' modifier.'
    public abstract void method(final String s);
}
