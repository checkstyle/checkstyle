/*
JavadocVariable
accessModifiers = public
ignoreNamePattern = (default)null
tokens = ENUM_CONSTANT_DEF, VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

class InputJavadocVariableInterfaceNesting {
    interface PackageInterface {
        public class NestedClass {
            public int publicField = 0;
        }
    }

    public interface PublicInterface {
        public class NestedClass {
            /** Documented field */
            public int documentedField = 0;

            public int undocumentedField = 1; // violation, 'Missing a Javadoc comment'
        }
    }
}
