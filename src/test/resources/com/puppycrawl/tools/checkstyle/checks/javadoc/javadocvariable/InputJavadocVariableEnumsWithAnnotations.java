/*
JavadocVariable
accessModifiers = (default)public,protected,package,private
ignoreNamePattern = (default)null
tokens = (default)ENUM_CONSTANT_DEF

*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

public class InputJavadocVariableEnumsWithAnnotations {
    public enum EnumWithAnnotations {
        /** Enum constant. */
        ENUM_CONSTANT1,
        /** Enum constant. */
        @Deprecated
        ENUM_CONSTANT2,
        /** Enum constant. */
        @Deprecated(since = "1.5", forRemoval = true)
        ENUM_CONSTANT3
    }

    private enum EnumWithAnnotationsAndNoJavadoc {
        /** Enum constant. */
        ENUM_CONSTANT1,
        @Deprecated // violation, 'Missing a Javadoc comment'
        ENUM_CONSTANT2,
        ENUM_CONSTANT3 // violation, 'Missing a Javadoc comment'
    }
}
