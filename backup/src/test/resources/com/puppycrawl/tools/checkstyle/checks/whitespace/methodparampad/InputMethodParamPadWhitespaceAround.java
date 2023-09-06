/*
MethodParamPad
allowLineBreaks = (default)false
option = SPACE
tokens = (default)CTOR_DEF, LITERAL_NEW, METHOD_CALL, METHOD_DEF, SUPER_CTOR_CALL, \
         ENUM_CONSTANT_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.methodparampad;

@SuppressWarnings({"this", "that"})
public class InputMethodParamPadWhitespaceAround // ok
{
    protected InputMethodParamPadWhitespaceAround ( int i )
    {
        this (); //whitespace
        toString ();
    }
    protected InputMethodParamPadWhitespaceAround ()
    {
        super ();
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
    Class<?>[] groups() default {};
}

@interface CronExpression1 {
    Class<?>[] groups() default { }; // extra space
}
