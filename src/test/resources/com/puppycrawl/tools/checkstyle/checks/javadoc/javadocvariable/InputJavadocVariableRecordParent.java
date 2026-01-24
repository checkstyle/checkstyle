/*
JavadocVariable
accessModifiers = public
ignoreNamePattern = (default)null
tokens = ENUM_CONSTANT_DEF, VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

class InputJavadocVariableRecordParent {
    record PackageRecord(int x) {
        public static int publicField = 0;
    }

    public record PublicRecord(int y) {
        /** Documented field */
        public static int documentedField = 0;

        public static int undocumentedField = 0; // violation, 'Missing a Javadoc comment'
    }
}
