/*
JavadocVariable
scope = public
excludeScope = (default)null
ignoreNamePattern = (default)null
tokens = ENUM_CONSTANT_DEF, VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

public interface InputJavadocVariableInterfaceMemberScopeIsPublic {

    /** First field */
    public static int field1 = 0;

    public static int field2 = 0; // violation

    int field3 = 0; // violation

    enum Enum {

        /** First constant */
        A,

        B; // violation

    }

}
