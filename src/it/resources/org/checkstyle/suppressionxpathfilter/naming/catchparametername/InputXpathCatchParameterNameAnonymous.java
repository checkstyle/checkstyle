/*
CatchParameterName
format = ^[a-z][a-zA-Z0-9]+$


*/

package org.checkstyle.suppressionxpathfilter.naming.catchparametername;

public class InputXpathCatchParameterNameAnonymous {
    class InnerClass {
        InnerClass() {
            new Runnable() {
                @Override
                public void run() {
                    try {
                    } catch (Exception e1) {
                    } try {
                    // violation below 'Name 'E1' must match pattern '^[a-z][a-zA-Z0-9]+$'.'
                    } catch (Exception E1) {
                    }
                }
            };
        }
    }
}
