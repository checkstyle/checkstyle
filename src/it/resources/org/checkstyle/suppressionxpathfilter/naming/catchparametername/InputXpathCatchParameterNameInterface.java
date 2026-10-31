/*
CatchParameterName
format = ^[A-Z][a-z]+$


*/

package org.checkstyle.suppressionxpathfilter.naming.catchparametername;

interface InputXpathCatchParameterNameInterface {
    interface InnerInterface {
        default void method() {
            try {
            } catch (Exception EX) { // violation 'Name 'EX' must match pattern '^[A-Z][a-z]+$'.'
            }
        }
    }
}
