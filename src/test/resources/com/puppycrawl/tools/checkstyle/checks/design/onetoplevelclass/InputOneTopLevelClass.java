package com.puppycrawl.tools.checkstyle.checks.design.onetoplevelclass;

public class InputOneTopLevelClass
{
    static final int FOO2 = 3;

    // violation: public before package
    public static final int FOO = 3;

    private static final int FOO3 = 3;

    // violation: public before package and private
    public static final int FOO4 = 3;

    private static final String ERROR = "error";

    // violation: protected before private
    protected static final String ERROR1 = "error";

    // violation: public before private
    public static final String WARNING = "warning";

    private int mMaxInitVars = 3;

    // violation: statics should be before instance members
    // violation: publics before private
    public static final int MAX_ITER_VARS = 3;

    private class InnerClass
    {
        private static final int INNER_FOO = 2;

        // violation: public before private
        public static final int INNER_FOO2 = 2;

        public InnerClass()
        {
            int foo = INNER_FOO;
            foo += INNER_FOO2;
            foo += INNER_FOO3;
        }

        // violation: member variables should be before methods or ctors
        // violation: public before private
        public static final int INNER_FOO3 = 2;
    }

    public int getFoo1()
    {
        return mFoo;
    }

    //  violation: ctors before methods
    public InputOneTopLevelClass()
    {
        String foo = ERROR;
        foo += ERROR1;
        foo += WARNING;
        int fooInt = mMaxInitVars;
        fooInt += MAX_ITER_VARS;
        fooInt += mFoo;
    }

    public static int getFoo2()
    {
        return 13;
    }

    public int getFoo()
    {
        return mFoo;
    }

    private static int getFoo21()
    {
        return 14;
    }

    // violation: member variables should be before methods or ctors
    private int mFoo = 0;
}
