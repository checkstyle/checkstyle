/*
MethodName
format = (default)^[a-z][a-zA-Z0-9]*$
allowClassName = (default)false
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.methodname;

public class InputMethodNameOverriddenMethods extends SomeClass
{
    @Override
    public void PublicFOO() { //Ignored due to impossibility of change by developer

    }

    @java.lang.Override
    protected void ProtectedFOO() { //Ignored due to impossibility of change by developer

    }
}

class SomeClass {
    public void PublicFOO() {  // violation 'Name 'PublicFOO' must match pattern'

    }
    protected void ProtectedFOO() { // violation 'Name 'ProtectedFOO' must match pattern'

    }
}
