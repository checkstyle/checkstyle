package org.checkstyle.suppressionxpathfilter.superfinalize;

public class SuppressionXpathRegressionSuperFinalizeInnerFinalize {
    public void finalize() // violation
    {
        class Inner
        {
            public void finalize() throws Throwable
            {
                super.finalize();
            }
        }
    }
}
