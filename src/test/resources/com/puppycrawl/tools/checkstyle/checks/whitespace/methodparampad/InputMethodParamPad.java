/*
MethodParamPad
allowLineBreaks = (default)false
option = (default)nospace
tokens = (default)CTOR_DEF, LITERAL_NEW, METHOD_CALL, METHOD_DEF, SUPER_CTOR_CALL, \
         ENUM_CONSTANT_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;
import java.util.Vector;
/** Test input for MethodDefPadCheck */
public class InputMethodParamPad
{
    public InputMethodParamPad()
    {
        super();
    }

    public InputMethodParamPad (int aParam) // violation
    {
        super (); // violation
    }

    public InputMethodParamPad
        (double aParam) // violation
    {
        super
            (); // violation
    }

    public void method()
    {
    }

    public void method (int aParam) // violation
    {
    }

    public void method
        (double aParam) // violation
    {
        // invoke constructor
        InputMethodParamPad pad = new InputMethodParamPad();
        pad = new InputMethodParamPad (); // violation
        pad = new InputMethodParamPad
            (); // violation

        // call method
        method();
        method (); // violation
        method
            (); // violation
    }

    public void dottedCalls()
    {
        this.method();
        this.method (); // violation
        this.method
            (); // violation

        InputMethodParamPad p = new InputMethodParamPad();
        p.method();
        p.method (); // violation
        p.method
            (); // violation

        java.lang.Integer.parseInt("0");
        java.lang.Integer.parseInt ("0"); // violation
        java.lang.Integer.parseInt
            ("0"); // violation
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
            () // violation
        {
        }
    }
}
