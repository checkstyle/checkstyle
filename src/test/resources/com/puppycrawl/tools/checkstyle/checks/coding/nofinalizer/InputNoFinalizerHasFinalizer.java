package com.puppycrawl.tools.checkstyle.checks.coding.nofinalizer;

public class InputNoFinalizerHasFinalizer
{
    public void finalize()
    {
        // It's not enough to check if the METHOD_DEF branch contains a PARAMETER_DEF, as that would
        // treat this method as having a parameter.
        Runnable runnable = new Runnable() {
        
            public void run() {
                reallyFinalize("hi");
            }

            // generates a PARAMETER_DEF AST inside the METHOD_DEF of finalize()
            private void reallyFinalize(String s)
            {
            }
        };
        runnable.run();
    }
    
    // should not be reported by NoFinalizer check
    public void finalize(String x)
    {
    }
}
