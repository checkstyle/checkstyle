package com.puppycrawl.tools.checkstyle.checks.whitespace;

@SuppressWarnings({"this", "that"})
public class InputWhitespaceAround
{
    protected InputWhitespaceAround ( int i )
    {
        this (); //whitespace
        toString ();
    }
    protected InputWhitespaceAround ()
    {
        super ();
    }
    
    public void enhancedFor ()
    {
        int[] i = new int[2];
        for ( int j: i ) {
            System.out.println ( j );
        }
    }
}

@interface CronExpression {
	Class<?>[] groups() default {}; 
}

@interface CronExpression1 {
	Class<?>[] groups() default { }; // extra space
}
