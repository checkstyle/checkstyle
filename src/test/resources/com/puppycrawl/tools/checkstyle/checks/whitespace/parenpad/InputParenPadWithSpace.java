package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

public class InputParenPadWithSpace
{
    protected InputParenPadWithSpace ( int i )
    {
        this ();
        toString ();
    }
    protected InputParenPadWithSpace ()
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
