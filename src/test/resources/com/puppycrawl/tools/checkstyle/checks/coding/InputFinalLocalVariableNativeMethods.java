package com.puppycrawl.tools.checkstyle.checks.coding;

public class InputFinalLocalVariableNativeMethods
{
    public native String nativeFoo(int a, int b);    
    private native double average(int n1, int n2);

     static {
         System.loadLibrary("foo");
     }        

     public void print () {
         String str = nativeFoo(1, 4);
         System.out.println(str);
     }

     public static void main(final String[] args) {
         (new InputFinalLocalVariableNativeMethods()).print();
         System.out.println("In Java, the average is " +
             new InputFinalLocalVariableNativeMethods().average(3, 2));
         return;
     }
}
