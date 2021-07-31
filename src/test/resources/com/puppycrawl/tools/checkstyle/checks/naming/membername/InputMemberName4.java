/*
MemberName
format = ^_[a-z]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.membername;

public class InputMemberName4
{
    public int mPublic;
    protected int mProtected; // violation
    int mPackage;//comment
    private int mPrivate;

    public int _public;
    protected int _protected;
    int _package;
    private int _private;
}
