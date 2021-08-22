/*
MethodParamPad
allowLineBreaks = (default)false
option = space
tokens = (default)CTOR_DEF, LITERAL_NEW, METHOD_CALL, METHOD_DEF, SUPER_CTOR_CALL, \
         ENUM_CONSTANT_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;
import java.util.Vector;
/** Test input for MethodDefPadCheck */
public class InputMethodParamPad3
{
    public InputMethodParamPad3() // violation
    {
        super(); // violation
    }

    public InputMethodParamPad3 (int aParam)
    {
        super ();
    }

    public InputMethodParamPad3
        (double aParam) // violation
    {
        super
            (); // violation
    }

    public void method() // violation
    {
    }

    public void method (int aParam)
    {
    }

    public void method
        (double aParam) // violation
    {
        // invoke constructor
        InputMethodParamPad pad = new InputMethodParamPad(); // violation
        pad = new InputMethodParamPad ();
        pad = new InputMethodParamPad
            (); // violation

        // call method
        method(); // violation
        method ();
        method
            (); // violation
    }

    public void dottedCalls() // violation
    {
        this.method(); // violation
        this.method ();
        this.method
            (); // violation

        InputMethodParamPad p = new InputMethodParamPad(); // violation
        p.method(); // violation
        p.method ();
        p.method
            (); // violation

        java.lang.Integer.parseInt("0"); // violation
        java.lang.Integer.parseInt ("0");
        java.lang.Integer.parseInt
            ("0"); // violation
    }

    public void newArray() // violation
    {
        int[] a = new int[]{0, 1};
        java.util.Vector<String> v = new java.util.Vector<String>(); // violation
        java.util.Vector<String> v1 = new Vector<String>(); // violation
    }

    enum TestEnum {
        FIRST ()
            {
            },

        SECOND
            () // violation
        {
        }
    }
}
