/*
JavadocVariable
accessModifiers = public

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

public class InputJavadocVariableNestedTraversal {
    public static class PublicOuter {
        private static class PrivateInner {
            public int fieldInNestedClass;
        }
    }
}
