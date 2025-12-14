/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

/** Test that @Override methods are skipped. */
public class InputGoogleMethodNameOverride {

    // These would normally violate but should be skipped due to @Override
    @Override
    public String toString() {
        return "test";
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
