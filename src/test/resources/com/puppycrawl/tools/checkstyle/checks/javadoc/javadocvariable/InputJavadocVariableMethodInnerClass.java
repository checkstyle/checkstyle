/*
JavadocVariable

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocvariable;

public class InputJavadocVariableMethodInnerClass {
    public int variablePublic; // violation, 'Missing a Javadoc comment'
    protected int variableProtected; // violation, 'Missing a Javadoc comment'
    int variablePackage; // violation, 'Missing a Javadoc comment'
    private int variablePrivate; // violation, 'Missing a Javadoc comment'

    public void testMethodInnerClass() {
        class InnerClass {
            public int innerClassVariablePublic;
            protected int innerClassVariableProtected;
            int innerClassVariablePackage;
            private int innerClassVariablePrivate;
        }
        Runnable runnable = new Runnable() {
            public int innerClassVariablePublic;
            protected int innerClassVariableProtected;
            int innerClassVariablePackage;
            private int innerClassVariablePrivate;
            public void run()
            {
                System.identityHashCode("running");
            }
        };
    }
}
