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
    // violation below 'Method name 'PUBLICfoo' must match the pattern '^[a-z][a-zA-Z0-9]*$''
    public void PUBLICfoo() {

    }
    // violation below 'Method name 'PROTECTEDfoo' must match the pattern '^[a-z][a-zA-Z0-9]*$''
    protected void PROTECTEDfoo() { // violation

    }
}
