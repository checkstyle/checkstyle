package com.puppycrawl.tools.checkstyle.ant;

/**
 * This class should pass all current sun-checks.
 *
 * @author m-mikula
 */
public final class AWarningExample {

    /**
     * This field for an awesome Singleton example.
     */
    private static AWarningExample warningExample = null;

    /**
     * This is a constructor.
     */
    private AWarningExample() {
    }

    /**
     * Just a method to give you an instance of this flawless Example.
     *
     * @return returns an instance of this class.
     */
    public AWarningExample getInstance() {
        if (warningExample == null) {
            warningExample = new AWarningExample();
        }
        return warningExample;
    }

    /**
     * This part should throw a magic number warning.
     * @return returns 2.
     */
    public int getSomeMagicNumber() {
        return 4 / 2;
    }

    /**
     * This part should throw another magic number warning.
     * @return returns 2.
     */
    public int getAnotherMagicNumber() {
        return 6 / 3;
    }
}
