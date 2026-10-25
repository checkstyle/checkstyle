/*
UnnecessarySemicolonAfterTypeMemberDeclaration
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, VARIABLE_DEF, \
         ANNOTATION_FIELD_DEF, STATIC_INIT, INSTANCE_INIT, CTOR_DEF, METHOD_DEF, \
         ENUM_CONSTANT_DEF, COMPACT_CTOR_DEF, RECORD_DEF


*/

package org.checkstyle.suppressionxpathfilter.coding.unnecessarysemicolonaftertypememberdeclaration;

public enum InputXpathUnnecessarySemicolonAfterTypeMemberDeclarationEnum {
    VALUE;
    void method() {}; // violation 'Unnecessary semicolon\.'
}
