/*
MemberName
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.membername;

public class InputMemberName
{
    public int mPublic;
    protected int mProtected;
    int mPackage;//comment
    private int mPrivate;

    public int _public; // violation 'Name '_public' must match pattern'
    protected int _protected; // violation 'Name '_protected' must match pattern'
    int _package; // violation 'Name '_package' must match pattern'
    private int _private; // violation 'Name '_private' must match pattern'
}
