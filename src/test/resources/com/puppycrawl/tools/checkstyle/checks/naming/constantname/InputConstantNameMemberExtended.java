package com.puppycrawl.tools.checkstyle.checks.naming.constantname;




public class InputConstantNameMemberExtended
{
    public int mPublic;
    protected int mProtected;
    int mPackage;
    private int mPrivate;

    public int _public;
    protected int _protected;
    int _package;
    private int _private;

    class Inner {
        public int mPublic;
        protected int mProtected;
        int mPackage;
        private int mPrivate;

        public int _public;
        protected int _protected;
        int _package;
        private int _private;
    }

    Inner anon = new Inner() {
        public int mPublic;
        protected int mProtected;
        int mPackage;
        private int mPrivate;

        public int _public;
        protected int _protected;
        int _package;
        private int _private;
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

    public int mPublic = 0;
    int mProtected = 0;
    int mPackage = 0;
    int mPrivate = 0;

    public int _public = 0;
    int _protected = 0;
    int _package = 0;
    int _private = 0;

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
