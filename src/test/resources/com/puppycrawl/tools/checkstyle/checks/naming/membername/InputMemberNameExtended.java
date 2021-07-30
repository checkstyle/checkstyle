/*
MemberName
format = ^[a-z][a-z0-9][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.membername;




public class InputMemberNameExtended
{
    public int mPublic; // violation
    protected int mProtected; // violation
    int mPackage; // violation
    private int mPrivate; // violation

    public int _public; // violation
    protected int _protected; // violation
    int _package; // violation
    private int _private; // violation

    class Inner {
        public int mPublic; // violation
        protected int mProtected; // violation
        int mPackage; // violation
        private int mPrivate; // violation

        public int _public; // violation
        protected int _protected; // violation
        int _package; // violation
        private int _private; // violation
    }

    Inner anon = new Inner() {
        public int mPublic; // violation
        protected int mProtected; // violation
        int mPackage; // violation
        private int mPrivate; // violation

        public int _public; // violation
        protected int _protected; // violation
        int _package; // violation
        private int _private; // violation
    };
}

interface In
{
    public int mPublic = 0;
    int mProtected = 0;
    int mPackage = 0;
    int mPrivate = 0;

    public int _public = 0;
    int _protected = 0;
    int _package = 0;
    int _private = 0;
}

enum Direction {

    NORTH(1),
    SOUTH(-1),
    EAST(-2),
    WEST(2);

    public int mPublic = 0; // violation
    int mProtected = 0; // violation
    int mPackage = 0; // violation
    int mPrivate = 0; // violation

    public int _public = 0; // violation
    int _protected = 0; // violation
    int _package = 0; // violation
    int _private = 0; // violation

    Direction(int code){
        this.code=code;
    }
    protected int code;
    public int getCode() {
          return this.code;
    }
    static Direction getOppositeDirection(Direction d){
          return null;
    }
}
