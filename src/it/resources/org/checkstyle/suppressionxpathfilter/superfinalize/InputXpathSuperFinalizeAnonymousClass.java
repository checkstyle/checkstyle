package org.checkstyle.suppressionxpathfilter.superfinalize;

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
