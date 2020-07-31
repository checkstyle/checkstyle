package com.puppycrawl.tools.checkstyle.checks.naming.constantname;

/*
* Config:
* applyToPublic = false
*/
public interface InputConstantNameInterfaceIgnorePublic {
    int __MAX_SIZE = 1024; // ok
    public int __MAX_SIZE2 = 1024; // ok
}

@interface Anno {
    int __MAX_SIZ1E = 1024; // ok
    public int __MAX_SIZE2 = 1024; // ok
}
