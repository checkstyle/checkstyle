/*
SuperFinalize


*/

package org.checkstyle.suppressionxpathfilter.coding.superfinalize;

public class InputXpathSuperFinalizeNoFinalize{
    public void finalize() // violation "Method 'finalize' should call 'super.finalize'."
    {
    }
}
