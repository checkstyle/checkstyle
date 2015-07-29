////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2003
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;


public class InputUncommentedMain2
{
    private class PC {
        
        // uncommented main with depth 2
        public void main(String[] args)
        {
            System.out.println("InputUncommentedMain.main()");
        }
        
        //lets go deeper
        private class PC2 {
            
            // uncommented main with depth 3
            public void main(String[] args)
            {
                System.out.println("InputUncommentedMain.main()");
            }
            
            
        }
    }
    
    public static void main(String[] args)
    {
        System.out.println("InputUncommentedMain.main()");
    }
    
}

interface IF {
    
    void main(String[] args);
}
