/*
JavadocVariable
accessModifiers = public

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

public class InputJavadocVariableProtectedInPublicClass {
    
    protected static class ProtectedNestedClass {
        public int publicFieldNoJavadoc;
        
        /** documented */
        public int publicFieldWithJavadoc;
    }
}
