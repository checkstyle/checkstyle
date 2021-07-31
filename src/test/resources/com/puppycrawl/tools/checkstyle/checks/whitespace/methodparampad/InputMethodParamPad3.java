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
        super();
    }

    public InputMethodParamPad3 (int aParam)
    {
        super ();
    }

    public InputMethodParamPad3
        (double aParam)
    {
        super
            ();
    }

    public void method()
    {
    }

    public void method (int aParam)
    {
    }

    public void method
        (double aParam)
    {
        // invoke constructor
        InputMethodParamPad pad = new InputMethodParamPad();
        pad = new InputMethodParamPad ();
        pad = new InputMethodParamPad
            ();

        // call method
        method();
        method ();
        method
            ();
    }

    public void dottedCalls()
    {
        this.method();
        this.method ();
        this.method
            ();

        InputMethodParamPad p = new InputMethodParamPad();
        p.method();
        p.method ();
        p.method
            ();

        java.lang.Integer.parseInt("0");
        java.lang.Integer.parseInt ("0");
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
        FIRST ()
            {
            },

        SECOND
            ()
        {
        }
    }
}
