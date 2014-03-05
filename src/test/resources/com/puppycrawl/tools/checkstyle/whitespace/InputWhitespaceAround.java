package com.puppycrawl.tools.checkstyle.whitespace;

public class InputWhitespaceAround
{
    protected InputWhitespaceAround ( int i )
    {
        this ();
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
