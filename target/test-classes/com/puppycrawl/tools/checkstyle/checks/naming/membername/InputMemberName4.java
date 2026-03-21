/*
MemberName
format = ^_[a-z]*$
applyToPublic = false
applyToProtected = (default)true
applyToPackage = false
applyToPrivate = false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.membername;

public class InputMemberName4
{
    public int mPublic;
    protected int mProtected; // violation 'Name 'mProtected' must match pattern'
    int mPackage;//comment
    private int mPrivate;

    public int _public;
    protected int _protected;
    int _package;
    private int _private;
}
