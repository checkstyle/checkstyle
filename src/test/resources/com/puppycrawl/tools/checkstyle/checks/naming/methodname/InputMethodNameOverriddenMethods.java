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
    public void PUBLICfoo() { //Ignored due to impossibility of change by developer

    }

    @java.lang.Override
    protected void PROTECTEDfoo() { //Ignored due to impossibility of change by developer

    }
}

class SomeClass {
    public void PUBLICfoo() { // violation ''PUBLICfoo' must match the pattern'

    }
    protected void PROTECTEDfoo() { // violation ''PROTECTEDfoo' must match the pattern'

    }
}
