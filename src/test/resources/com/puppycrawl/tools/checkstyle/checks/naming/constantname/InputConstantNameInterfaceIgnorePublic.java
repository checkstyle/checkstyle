/*
ConstantName
format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$
applyToPublic = false
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.constantname;

public interface InputConstantNameInterfaceIgnorePublic {
    int __MAX_SIZE = 1024;
    public int __MAX_SIZE2 = 1024;
}

@interface Anno {
    int __MAX_SIZ1E = 1024;
    public int __MAX_SIZE2 = 1024;
}
