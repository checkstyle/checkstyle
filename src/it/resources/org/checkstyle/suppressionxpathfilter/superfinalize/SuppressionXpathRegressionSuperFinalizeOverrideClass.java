package org.checkstyle.suppressionxpathfilter.superfinalize;

public class SuppressionXpathRegressionSuperFinalizeOverrideClass extends FinalizeWithArgs {
    @Override
    protected void finalize() throws Throwable { // violation
        super.finalize(new Object());
    }
}

class FinalizeWithArgs {
    public void finalize(Object a) {};
}
