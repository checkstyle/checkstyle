/*
MethodParamPad
allowLineBreaks = true
option = (default)nospace
tokens = (default)CTOR_DEF, LITERAL_NEW, METHOD_CALL, METHOD_DEF, SUPER_CTOR_CALL, \
         ENUM_CONSTANT_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;
import java.util.Vector;
/** Test input for MethodDefPadCheck */
public class InputMethodParamPad2
{
    public InputMethodParamPad2()
    {
        super();
    }

    public InputMethodParamPad2 (int aParam) // violation ''(' is preceded with whitespace'
    {
        super (); // violation ''(' is preceded with whitespace'
    }

    public InputMethodParamPad2
        (double aParam)
    {
        super
            ();
    }

    public void method()
    {
    }

    public void method (int aParam) // violation ''(' is preceded with whitespace'
    {
    }

    public void method
        (double aParam)
    {
        // invoke constructor
        InputMethodParamPad pad = new InputMethodParamPad();
        pad = new InputMethodParamPad (); // violation ''(' is preceded with whitespace'
        pad = new InputMethodParamPad
            ();

        // call method
        method();
        method (); // violation ''(' is preceded with whitespace'
        method
            ();
    }

    public void dottedCalls()
    {
        this.method();
        this.method (); // violation ''(' is preceded with whitespace'
        this.method
            ();

        InputMethodParamPad p = new InputMethodParamPad();
        p.method();
        p.method (); // violation ''(' is preceded with whitespace'
        p.method
            ();

        java.lang.Integer.parseInt("0");
        java.lang.Integer.parseInt ("0"); // violation ''(' is preceded with whitespace'
        java.lang.Integer.parseInt
            ("0");
    }

    public void newArray()
    {
        int[] a = new int[]{0, 1};
        java.util.Vector<String> v = new java.util.Vector<String>();
        java.util.Vector<String> v1 = new Vector<String>();
    }

    enum TestEnum {
        FIRST () // violation ''(' is preceded with whitespace'
            {
            },

        SECOND
            ()
        {
        }
    }
}
