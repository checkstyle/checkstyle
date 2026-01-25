/*
JavadocVariable
accessModifiers = (default)public, protected, package, private
ignoreNamePattern = (default)null
tokens = (default)VARIABLE_DEF, ENUM_CONSTANT_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

public class InputJavadocVariableGetAccessModifier {

    public enum PublicEnum {
        PUBLIC_ENUM_CONSTANT,
        ANOTHER_CONSTANT;

        public int publicField;
        private int privateField;
    }

    private enum PrivateEnum {
        PRIVATE_ENUM_CONSTANT;

        public int publicField;
    }

    enum PackageEnum {
        PACKAGE_ENUM_CONSTANT;
    }

    protected enum ProtectedEnum {
        PROTECTED_ENUM_CONSTANT;
    }

    public int publicField;
    private int privateField;
    protected int protectedField;
    int packageField;

    private static class PrivateOuterClass {
        public int publicFieldInPrivateClass;

        private static class DoublyNestedPrivateClass {
            public int publicFieldInDoublyNestedClass;
        }
    }
}
