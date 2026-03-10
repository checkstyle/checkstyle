/*
JavadocVariable
accessModifiers = (default)public,protected,package,private
ignoreNamePattern = (default)null
tokens = ENUM_CONSTANT_DEF, VARIABLE_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

public class InputJavadocVariableOnPrivateEnumConstants {

    private enum PrivateEnum {

        /** Documented private constant. */
        PRIVATE_DOCUMENTED,

        /** Another documented constant. */
        ANOTHER_DOCUMENTED,

        PRIVATE_UNDOCUMENTED, // violation, 'Missing a Javadoc comment.'
    }
}

