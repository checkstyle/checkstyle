package org.checkstyle.suppressionxpathfilter.coding.superfinalize;

class InputXpathSuperFinalizeInnerClass
{
    public void finalize() // warn
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
