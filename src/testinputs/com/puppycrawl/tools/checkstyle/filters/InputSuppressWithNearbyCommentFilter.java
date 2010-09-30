////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.filters;

/**
 * Test input for using comments to suppress errors.
 * 
 * @author Mick Killianey
 */
class InputSuppressWithNearbyCommentFilter
{
    private int A1;  // SUPPRESS CHECKSTYLE MemberNameCheck
    private int A2;  /* SUPPRESS CHECKSTYLE MemberNameCheck */
    /* SUPPRESS CHECKSTYLE MemberNameCheck */ private int A3;  
    
    private int B1;  // SUPPRESS CHECKSTYLE MemberNameCheck
    private int B2;  /* SUPPRESS CHECKSTYLE MemberNameCheck */
    /* SUPPRESS CHECKSTYLE MemberNameCheck */ private int B3;  
    
    private int C1;
    // ALLOW MemberName ON NEXT LINE
    private int C2;
    private int C3;  
    
    private int D1;
    private int D2;
    // ALLOW MemberName ON PREVIOUS LINE
    private int D3;  
    
    private static final int e1 = 0;
    private int E2;
    private int E3;    // ALLOW ConstantName UNTIL THIS LINE+2
    private static final int e4 = 0;
    private int E5;
    private static final int e6 = 0;
    private int E7;
    private int E8;    /* ALLOW MemberName UNTIL THIS LINE-3 */
    private static final int e9 = 0;
    
    // ALLOW Unused UNTIL THIS LINE+5
    public static void doit1(int aInt) // this is +1
    {
    }

    public static void doit2(int aInt) // this is +5
    {
    }

    public static void doit3(int aInt) // this is +9
    {
    }

    public void doit4()
    {
        try {
            // blah blah blah
            for(int i = 0; i < 10; i++) {
                // blah blah blah
                while(true) {
                    try {
                        // blah blah blah
                    } catch(Exception e) {
                        // bad bad bad
                    } catch (Throwable t) {
                    	// ALLOW CATCH Throwable BECAUSE I threw this together.
                    }
                }
                // blah blah blah
            }
            // blah blah blah
        } catch(Exception ex) {
            // ALLOW CATCH Exception BECAUSE I am an exceptional person.
        }
    }
}
