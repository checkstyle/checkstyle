package com.puppycrawl.tools.checkstyle.checks.coding.declarationorder;

/*
 * Config:
 * ignoreConstructors = true
 * ignoreModifiers = false
 */
public class InputDeclarationOrderOnlyModifiers
{
    static final int FOO2 = 3;

    // public before package
    public static final int FOO = 3; // violation

    private static final int FOO3 = 3;

    // public before package and private
    public static final int FOO4 = 3; // violation

    private static final String ERROR = "error";

    // protected before private
    protected static final String ERROR1 = "error"; // violation

    // public before private
    public static final String WARNING = "warning"; // violation

    private int mMaxInitVars = 3;

    // statics should be before instance members
    // publics before private
    public static final int MAX_ITER_VARS = 3; // violation

    private class InnerClass
    {
        private static final int INNER_FOO = 2;

        // public before private
        public static final int INNER_FOO2 = 2; // violation

        public InnerClass()
        {
            int foo = INNER_FOO;
            foo += INNER_FOO2;
            foo += INNER_FOO3;
        }

        public InnerClass(int start)
        {
            int foo = start;
            foo += INNER_FOO2;
            foo += INNER_FOO3;
        }

        // member variables should be before methods or ctors
        // public before private
        public static final int INNER_FOO3 = 2; // violation
    }

    public int getFoo1()
    {
        return mFoo;
    }

    public InputDeclarationOrderOnlyModifiers()
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

    // member variables should be before methods or ctors
    private int mFoo = 0; // violation
}

enum InputDeclarationOrderEnum3
{
    ENUM_VALUE_1,
    ENUM_VALUE_2,
    ENUM_VALUE_3
    {
        private static final int INNER_FOO = 2;

        // public before private
        public static final int INNER_FOO2 = 2; // violation

        public void doIt()
        {
        }

        // member variables should be before methods or ctors
        // public before private
        public static final int INNER_FOO3 = 2; // violation
    };

    static final int FOO2 = 3;

    // public before package
    public static final int FOO = 3; // violation

    private static final int FOO3 = 3;

    // public before package and private
    public static final int FOO4 = 3; // violation

    private static final String ERROR = "error";

    // protected before private
    protected static final String ERROR1 = "error"; // violation

    // public before private
    public static final String WARNING = "warning"; // violation

    private int mMaxInitVars = 3;

    // statics should be before instance members
    // publics before private
    public static final int MAX_ITER_VARS = 3; // violation

    private class InnerClass
    {
        private static final int INNER_FOO = 2;

        // public before private
        public static final int INNER_FOO2 = 2; // violation

        public InnerClass()
        {
            int foo = INNER_FOO;
            foo += INNER_FOO2;
            foo += INNER_FOO3;
        }

        // member variables should be before methods or ctors
        // public before private
        public static final int INNER_FOO3 = 2; // violation
    }

    public int getFoo1()
    {
        return mFoo;
    }

    InputDeclarationOrderEnum3()
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
        return 2;
    }

    public int getFoo()
    {
        return mFoo;
    }

    private static int getFoo21()
    {
        return 1;
    }

    // member variables should be before methods or ctors
    private int mFoo = 0; // violation

    class AsyncProcess {
        private final int startLogErrorsCnt = 0;

        protected final int maxTotalConcurrentTasks = 0; // violation
    }
}
