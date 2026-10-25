/*
UnusedLocalVariable
allowUnnamedVariables = (default)true
jdkVersion = (default)22


*/

package org.checkstyle.suppressionxpathfilter.coding.unusedlocalvariable;

public class InputXpathUnusedLocalVariableThree {

    public void foo(){
       int i = 0;
       do{
        int c = 15; // violation 'Unused named local variable ''c''\.'
        i++;
       }while(i<5);
    }
}
