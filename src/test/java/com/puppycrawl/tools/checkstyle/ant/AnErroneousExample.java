package com.puppycrawl.tools.checkstyle.ant;

/**
 * This class should pass all current sun-checks.
 *
 * @author m-mikula
 */
public final class AnErroneousExample {
    private static AnErroneousExample erroneousExample = null;
    private AnErroneousExample() {
    }

    /**
     * Just a method to give you an instance of this flawless Example.
     * 
     * @return returns an instance of this class.
     */
    public AnErroneousExample getInstance(){
        if (erroneousExample == null) {
            erroneousExample = new AnErroneousExample();
        } 
        return erroneousExample;
    }
    /**
     * Does some stuff.
     */
    public void doStuff(){}
}
