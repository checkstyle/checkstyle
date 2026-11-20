/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, STATIC_INIT, INSTANCE_INIT, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF, ANNOTATION_FIELD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

public @interface InputEmptyLineSeparatorAnnotationMembers {

    String first();
    String second(); // violation ''ANNOTATION_FIELD_DEF' should be separated from previous line.'

    String third() default "value";
    String fourth(); // violation ''ANNOTATION_FIELD_DEF' should be separated from previous line.'

    int CONSTANT = 1;
    String fifth(); // violation ''ANNOTATION_FIELD_DEF' should be separated from previous line.'

    String sixth();
    int OTHER_CONSTANT = 2; // violation ''VARIABLE_DEF' should be separated from previous line.'

}
