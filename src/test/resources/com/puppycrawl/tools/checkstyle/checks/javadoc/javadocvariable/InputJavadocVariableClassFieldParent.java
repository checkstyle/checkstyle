/*
JavadocVariable
accessModifiers = public
ignoreNamePattern = (default)null
tokens = ENUM_CONSTANT_DEF, VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

class InputJavadocVariableClassFieldParent {

    class PackageClass {
        public int publicField = 0;
    }

    public class PublicClass {
        /** Documented field */
        public int documentedField = 0;

        public int undocumentedField = 1; // violation, 'Missing a Javadoc comment'
    }
}
