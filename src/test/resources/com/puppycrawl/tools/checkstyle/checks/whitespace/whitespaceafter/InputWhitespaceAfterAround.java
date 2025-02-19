/*
WhitespaceAfter
tokens = (default)COMMA, SEMI, TYPECAST, LITERAL_IF, LITERAL_ELSE, LITERAL_WHILE, \
         LITERAL_DO, LITERAL_FOR, DO_WHILE


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

@SuppressWarnings({"this", "that"})
public class InputWhitespaceAfterAround
{
    protected InputWhitespaceAfterAround ( int i )
    {
        this (); //whitespace
        toString ();
    }
    protected InputWhitespaceAfterAround ()
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
