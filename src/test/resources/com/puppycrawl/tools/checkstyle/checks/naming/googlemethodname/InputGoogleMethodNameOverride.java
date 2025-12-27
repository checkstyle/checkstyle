/*
GoogleMethodName

*/
package com.puppycrawl.tools.checkstyle.checks.naming.googlemethodname;

class ParentWithBadName {
    // violation below, 'Method name 'Foo' is not valid per Google Java Style Guide.'
    public boolean Foo() {
        return true;
    }
}

/** Test that @Override methods are skipped. */
public class InputGoogleMethodNameOverride extends ParentWithBadName {

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

    @Override
    public boolean Foo() {
        return false;
    }
}
