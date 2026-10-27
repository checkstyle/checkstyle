/*
UnusedLocalVariable
allowUnnamedVariables = (default)true
jdkVersion = (default)22


*/

package org.checkstyle.suppressionxpathfilter.coding.unusedlocalvariable;

public class InputXpathUnusedLocalVariableOne {

    public void foo() {
        int a = 20; // violation 'Unused named local variable ''a''\.'
    }
}
