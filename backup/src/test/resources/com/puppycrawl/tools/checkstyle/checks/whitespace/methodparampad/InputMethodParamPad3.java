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
    public InputMethodParamPad3() // violation ''(' is not preceded with whitespace'
    {
        super(); // violation ''(' is not preceded with whitespace'
    }

    public InputMethodParamPad3 (int aParam)
    {
        super ();
    }

    public InputMethodParamPad3
        (double aParam) // violation ''(' should be on the previous line'
    {
        super
            (); // violation ''(' should be on the previous line'
    }

    public void method() // violation ''(' is not preceded with whitespace'
    {
    }

    public void method (int aParam)
    {
    }

    public void method
        (double aParam) // violation ''(' should be on the previous line'
    {
        // invoke constructor
        InputMethodParamPad pad =
                new InputMethodParamPad(); // violation ''(' is not preceded with whitespace'
        pad = new InputMethodParamPad ();
        pad = new InputMethodParamPad
            (); // violation ''(' should be on the previous line'

        // call method
        method(); // violation ''(' is not preceded with whitespace'
        method ();
        method
            (); // violation ''(' should be on the previous line'
    }

    public void dottedCalls() // violation ''(' is not preceded with whitespace'
    {
        this.method(); // violation ''(' is not preceded with whitespace'
        this.method ();
        this.method
            (); // violation ''(' should be on the previous line'

        InputMethodParamPad p =
                new InputMethodParamPad(); // violation ''(' is not preceded with whitespace'
        p.method(); // violation ''(' is not preceded with whitespace'
        p.method ();
        p.method
            (); // violation ''(' should be on the previous line'

        java.lang.Integer.parseInt("0"); // violation ''(' is not preceded with whitespace'
        java.lang.Integer.parseInt ("0");
        java.lang.Integer.parseInt
            ("0"); // violation ''(' should be on the previous line'
    }

    public void newArray() // violation ''(' is not preceded with whitespace'
    {
        int[] a = new int[]{0, 1};
        java.util.Vector<String> v =
                new java.util.Vector<String>(); // violation ''(' is not preceded with whitespace'
        java.util.Vector<String> v1 =
                new Vector<String>(); // violation ''(' is not preceded with whitespace'
    }

    enum TestEnum {
        FIRST ()
            {
            },

        SECOND
            () // violation ''(' should be on the previous line'
        {
        }
    }
}
