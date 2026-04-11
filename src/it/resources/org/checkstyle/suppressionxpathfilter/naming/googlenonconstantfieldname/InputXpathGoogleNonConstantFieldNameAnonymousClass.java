package org.checkstyle.suppressionxpathfilter.naming.googlenonconstantfieldname;

public class InputXpathGoogleNonConstantFieldNameAnonymousClass {

    Runnable run = new Runnable() {
        int f; // warn 'Non-constant field name 'f' must start lowercase, be at least 2 chars'
        public void run() {}
    };
}
