/*
JavadocVariable
accessModifiers = public
ignoreNamePattern = (default)null
tokens = (default)ENUM_CONSTANT_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

class InputJavadocVariablePackagePrivateClass {
    public static String str = ""; // violation, 'Missing a Javadoc comment'
    protected int value;
    int packageField;
    private String privateField;
}

public class PublicClass {
    /** Documented field - OK */
    public static String documented = "";

    public int undocumented; // violation, 'Missing a Javadoc comment'

    protected int protectedField;

    int packageField;

    private String privateField;

    class PackageInnerClass {
        public int innerPublic;
    }

    public class PublicInnerClass {
        /** Documented inner field */
        public int documented;

        public int undocumented; // violation, 'Missing a Javadoc comment'
    }

    private class PrivateInnerClass {
        public int innerPublic;
    }
}
