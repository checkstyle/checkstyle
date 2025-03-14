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
    public int mPublic; // violation 'Name 'mPublic' must match pattern'
    protected int mProtected; // violation 'Name 'mProtected' must match pattern'
    int mPackage; // violation 'Name 'mPackage' must match pattern'
    private int mPrivate; // violation 'Name 'mPrivate' must match pattern'

    public int _public; // violation 'Name '_public' must match pattern'
    protected int _protected; // violation 'Name '_protected' must match pattern'
    int _package; // violation 'Name '_package' must match pattern'
    private int _private; // violation 'Name '_private' must match pattern'

    class Inner {
        public int mPublic; // violation 'Name 'mPublic' must match pattern'
        protected int mProtected; // violation 'Name 'mProtected' must match pattern'
        int mPackage; // violation 'Name 'mPackage' must match pattern'
        private int mPrivate; // violation 'Name 'mPrivate' must match pattern'

        public int _public; // violation 'Name '_public' must match pattern'
        protected int _protected; // violation 'Name '_protected' must match pattern'
        int _package; // violation 'Name '_package' must match pattern'
        private int _private; // violation 'Name '_private' must match pattern'
    }

    Inner anon = new Inner() {
        public int mPublic; // violation 'Name 'mPublic' must match pattern'
        protected int mProtected; // violation 'Name 'mProtected' must match pattern'
        int mPackage; // violation 'Name 'mPackage' must match pattern'
        private int mPrivate; // violation 'Name 'mPrivate' must match pattern'

        public int _public; // violation 'Name '_public' must match pattern'
        protected int _protected; // violation 'Name '_protected' must match pattern'
        int _package; // violation 'Name '_package' must match pattern'
        private int _private; // violation 'Name '_private' must match pattern'
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

    public int mPublic = 0; // violation 'Name 'mPublic' must match pattern'
    int mProtected = 0; // violation 'Name 'mProtected' must match pattern'
    int mPackage = 0; // violation 'Name 'mPackage' must match pattern'
    int mPrivate = 0; // violation 'Name 'mPrivate' must match pattern'

    public int _public = 0; // violation 'Name '_public' must match pattern'
    int _protected = 0; // violation 'Name '_protected' must match pattern'
    int _package = 0; // violation 'Name '_package' must match pattern'
    int _private = 0; // violation 'Name '_private' must match pattern'

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
