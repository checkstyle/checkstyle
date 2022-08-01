/*
ConstantName
format = (default)^[A-Z][A-Z\d]*(_[A-Z\d]+)*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.constantname;


public interface InputConstantNameStaticModifierInInterface // ok
{
    static int f()
    {
        int someName = 5;
        return someName;
    }
}
