package org.checkstyle.suppressionxpathfilter.coding.superfinalize;

public class InputXpathSuperFinalizeAnonymousClass
{
    public void createAnonymousClass()
    {
        Object anonymousClassObject = new Object() {
            @Override
            protected void finalize() // warn
            {
            }
        };
    }
}
