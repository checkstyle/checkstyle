////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.filters;

/**
 * Test input for using comments to suppress errors.
 * @author Rick Giles
 **/
class InputSuppressionCommentFilter
{
    private int I;
    
    /* CHECKSTYLE:OFF */
    private int J;
    /* CHECKSTYLE:ON */
    
    private int K;

    //CSOFF: MemberNameCheck|ConstantNameCheck
    private int L;
    private static final int m = 0;
    /*
     * CSON: MemberNameCheck
     */
    private int M2;
    private static final int n = 0;
    //CSON: ConstantNameCheck
    
    //CS_OFF
    private int P;
    //CS_ON
    
    private int Q;
    
    //CS_OFF: ConstantNameCheck
    private int R;
    private static final int s = 0;
    //CS_ON
    
    //CHECKSTYLE:OFF
    private int T;
    //CHECKSTYLE:ON

    //UNUSED OFF: aInt
    public static void doit1(int aInt)
    {
    }
    //UNUSED ON: aInt
    public static void doit2(int aInt)
    {
    }

    public void doit3()
    {
        try {
            // lots of code omitted
            for(int i = 0; i < 10; i++) {
                // more code omitted
                while(true) {
                    try {
                        //CHECKSTYLE:OFF
                    } catch(Exception e) {
                       //CHECKSTYLE:ON
                    }
                }
                // code omitted
            }
            //CHECKSTYLE:OFF
        } catch(Exception ex) {
            //CHECKSTYLE:ON
        }

        try{
            //IllegalCatchCheck OFF: Exception
        } catch(RuntimeException ex){
        } catch(Exception ex){
            //IllegalCatchCheck ON: Exception
        }
    }

}
