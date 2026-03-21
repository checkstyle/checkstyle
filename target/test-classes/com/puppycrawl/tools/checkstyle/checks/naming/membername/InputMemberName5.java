/*
MemberName
format = ^_[a-z]*$
applyToPublic = false
applyToProtected = false
applyToPackage = (default)true
applyToPrivate = false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.membername;

public class InputMemberName5
{
    public int mPublic;
    protected int mProtected;
    int mPackage; // violation 'Name 'mPackage' must match pattern'
    private int mPrivate;

    public int _public;
    protected int _protected;
    int _package;
    private int _private;
}
