/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = PARAMETER_DEF
validateUnnamedVariables = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableNativeMethods
{
    public native String nativeFoo(int a, int b);
    private native double average(int n1, int n2);

     static {
         System.loadLibrary("foo");
     }

     public void print () {
         String str = nativeFoo(1, 4);
         System.identityHashCode(str);
     }

     public static void main(final String[] args) {
         (new InputFinalLocalVariableNativeMethods()).print();
         System.identityHashCode("In Java, the average is " +
             new InputFinalLocalVariableNativeMethods().average(3, 2));
         return;
     }
}
