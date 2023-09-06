/*
MemberName
format = ^_[a-z]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.membername;

public class InputMemberName2
{
    public int mPublic; // violation
    protected int mProtected; // violation
    int mPackage; // violation
    private int mPrivate; // violation

    public int _public;
    protected int _protected;
    int _package;
    private int _private;
}
