/*
GoogleNonConstantFieldName


*/

package org.checkstyle.suppressionxpathfilter.naming.googlenonconstantfieldname;

public class InputXpathGoogleNonConstantFieldNameAnonymousClass {

    Runnable run = new Runnable() {
        int f; // violation "Non-constant field name 'f' must start lowercase"
        public void run() {}
    };
}
