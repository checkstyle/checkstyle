////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2002
////////////////////////////////////////////////////////////////////////////////

/**
 * Test case for hidden fields
 * @author Rick Giles
 **/
class InputHiddenField
{
    private int hidden = 0;
    
    public InputHiddenField()
    {
        int hidden = 0; //shadows field
    }
    
    public InputHiddenField(int hidden) //parameter shadows field
    {
    }
    
    public void shadow()
    {
        int hidden = 0; //shadows field
    }
    
    public void shadowFor()
    {
        for (int hidden = 0; hidden < 1; hidden++) { //shadows field
        }
    }
    
    public void shadowParam(int hidden) //parameter shadows field
    {
    }
    
    public class Inner
    {
        private int innerHidden = 0;
        
        public Inner()
        {
            int innerHidden = 0; //shadows field
        }
    
        public Inner(int innerHidden) //shadows field
        {
        }
        
        private void innerShadow()
        {
            int innerHidden = 0; //shadows field
            int hidden = 0; //not an error
        }
        
        private void innerShadowFor()
        {
            for (int innerHidden = 0; innerHidden < 1; innerHidden++) {
            }
            //not an error
            for (int hidden = 0; hidden < 1; hidden++) {
            }
        }
        
        private void shadowParam(
            int innerHidden, //parameter shadows field
            int hidden //not an error
        )
        {
        }
        
        {
            int innerHidden = 0;//shadows field
        }
    }

    {
        int hidden = 0;//shadows field
    }       
}
    
interface NothingHidden
{
    public static int notHidden = 0;
    
    // not an error
    public void noShadow(int notHidden);
}
