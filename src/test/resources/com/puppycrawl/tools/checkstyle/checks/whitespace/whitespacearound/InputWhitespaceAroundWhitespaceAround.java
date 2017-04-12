package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

@SuppressWarnings({"this", "that"})
public class InputWhitespaceAroundWhitespaceAround
{
    protected InputWhitespaceAroundWhitespaceAround(int i )
    {
        this (); //whitespace
        toString ();
    }
    protected InputWhitespaceAroundWhitespaceAround()
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
