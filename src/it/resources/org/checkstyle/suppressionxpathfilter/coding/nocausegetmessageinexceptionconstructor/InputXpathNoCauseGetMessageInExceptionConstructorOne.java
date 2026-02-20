/*
NoCauseGetMessageInExceptionConstructor


*/

package org.checkstyle.suppressionxpathfilter.coding.nocausegetmessageinexceptionconstructor;

public class InputXpathNoCauseGetMessageInExceptionConstructorOne {
        void method() throws Exception {
            try {
                // code
            } catch (Exception ex) {
                // violation below "Avoid using '.getMessage()' in throw statement."
                throw new Exception("Error: " + ex.getMessage(), ex);
            }
        }
}
