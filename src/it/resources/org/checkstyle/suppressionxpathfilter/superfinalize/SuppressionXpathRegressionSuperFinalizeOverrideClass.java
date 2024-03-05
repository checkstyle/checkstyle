package org.checkstyle.suppressionxpathfilter.superfinalize;

public class SuppressionXpathRegressionSuperFinalizeOverrideClass extends FinalizeWithArgs {
    @Override
    protected void finalize() throws Throwable { // warn
        super.finalize(new Object());
    }
}

class FinalizeWithArgs {
    public void finalize(Object a) {};
}
