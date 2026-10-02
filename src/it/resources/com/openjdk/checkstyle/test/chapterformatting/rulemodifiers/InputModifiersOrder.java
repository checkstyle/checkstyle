package com.openjdk.checkstyle.test.chapterformatting.rulemodifiers;

// violation first line 'Header mismatch'

public abstract class InputModifiersOrder {

    public static final int VALID = 1;

    // violation below 'public.*modifier out of order with the defined modifier order'
    static public final int INVALID = 2;

    private transient volatile int valid;

    // violation below 'transient.*modifier out of order with the defined modifier order'
    private volatile transient int invalid;

    public abstract void validAbstract();

    // violation below 'protected.*modifier out of order with the defined modifier order'
    abstract protected void invalidAbstract();

    public synchronized native void validNative();

    // violation below 'synchronized.*modifier out of order with the defined modifier order'
    public native synchronized void invalidNative();

    protected static class ValidNested { }

    // violation below 'protected.*modifier out of order with the defined modifier order'
    static protected class InvalidNested { }

    interface WithDefaultMethod {

        default void validDefault() { }
    }
}
