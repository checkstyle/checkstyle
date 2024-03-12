/*
MethodParamPad
allowLineBreaks = (default)false
option = SPACE
tokens = (default)CTOR_DEF, LITERAL_NEW, METHOD_CALL, METHOD_DEF, SUPER_CTOR_CALL, \
         ENUM_CONSTANT_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;

@SuppressWarnings({"this", "that"})
public class InputMethodParamPadWhitespaceAround
{
    protected InputMethodParamPadWhitespaceAround ( int i )
    {
        this (); // ok, whitespace between 'this' and ()
        toString ();
    }
    protected InputMethodParamPadWhitespaceAround ()
    {
        super ();
    }

    protected InputMethodParamPadWhitespaceAround ( String s)
    {
        // ok, until https://github.com/checkstyle/checkstyle/issues/13675
        this();
    }

    public void enhancedFor ()
    {
        int[] i = new int[2];
        for ( int j: i ) {
            System.identityHashCode ( j );
        }
    }
}

@interface CronExpression {
    // annotation type elements are not checked
    Class<?>[] groups() default {};
}

@interface CronExpression1 {
    // annotation type elements are not checked
    Class<?>[] groups() default { };
}
