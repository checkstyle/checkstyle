/*
SuperFinalize


*/

package org.checkstyle.suppressionxpathfilter.coding.superfinalize;

class InputXpathSuperFinalizeInnerClass
{
    public void finalize() // violation "Method 'finalize' should call 'super.finalize'."
    {
        class InnerClass
        {
            public void finalize() throws Throwable
            {
                super.finalize();
            }
        }
    }
}
