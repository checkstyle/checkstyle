package com.puppycrawl.tools.checkstyle.whitespace;

public class ParenPadWithSpace
{
    protected ParenPadWithSpace ( int i )
    {
        this ();
        toString ();
    }
    protected ParenPadWithSpace ()
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
