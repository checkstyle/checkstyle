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
        int hidden = 0;
    }
    
    public InputHiddenField(int hidden)
    {
    }
    
    public void shadow()
    {
        int hidden = 0;
    }
    
    public void shadowFor()
    {
        for (int hidden = 0; hidden < 1; hidden++) {
        }
    }
    
    public void shadowParam(int hidden)
    {
    }
    
    public class Inner
    {
        private int innerHidden = 0;
        
        public Inner()
        {
            int innerHidden = 0;
        }
    
        public Inner(int innerHidden)
        {
        }
        
        private void innerShadow()
        {
            int innerHidden = 0;
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
            int innerHidden,
            int hidden //not an error
        )
        {
        }
        
        {
            int innerHidden = 0;
        }
    }

    {
        int hidden = 0;
    }       
}
