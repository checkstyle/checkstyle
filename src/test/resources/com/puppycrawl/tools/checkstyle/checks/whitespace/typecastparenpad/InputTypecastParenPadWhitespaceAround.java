package com.puppycrawl.tools.checkstyle.checks.whitespace.typecastparenpad;

@SuppressWarnings({"this", "that"})
class InputTypecastParenPadWhitespaceAround
{
    protected InputTypecastParenPadWhitespaceAround ( int i )
    {
        this (); //whitespace
        toString ();
    }
    protected InputTypecastParenPadWhitespaceAround ()
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

@interface CronExpression_TypecastParenPad {
	Class<?>[] groups() default {};
}

@interface CronExpression1_TypecastParenPad {
	Class<?>[] groups() default { }; // extra space
}
