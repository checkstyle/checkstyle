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

    public InputMethodParamPad2 (int aParam) // violation
    {
        super (); // violation
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

    public void method (int aParam) // violation
    {
    }

    public void method
        (double aParam)
    {
        // invoke constructor
        InputMethodParamPad pad = new InputMethodParamPad();
        pad = new InputMethodParamPad (); // violation
        pad = new InputMethodParamPad
            ();

        // call method
        method();
        method (); // violation
        method
            ();
    }

    public void dottedCalls()
    {
        this.method();
        this.method (); // violation
        this.method
            ();

        InputMethodParamPad p = new InputMethodParamPad();
        p.method();
        p.method (); // violation
        p.method
            ();

        java.lang.Integer.parseInt("0");
        java.lang.Integer.parseInt ("0"); // violation
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
        FIRST () // violation
            {
            },

        SECOND
            ()
        {
        }
    }
}
