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
    public int mPublic; // violation 'Name 'mPublic' must match pattern'
    protected int mProtected; // violation 'Name 'mProtected' must match pattern'
    int mPackage; // violation 'Name 'mPackage' must match pattern'
    private int mPrivate; // violation 'Name 'mPrivate' must match pattern'

    public int _public;
    protected int _protected;
    int _package;
    private int _private;
}
