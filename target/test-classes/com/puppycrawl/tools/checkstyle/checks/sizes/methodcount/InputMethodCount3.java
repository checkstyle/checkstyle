/*
MethodCount
maxTotal = 2
maxPrivate = 0
maxPackage = (default)100
maxProtected = (default)100
maxPublic = (default)100
tokens = (default)CLASS_DEF, ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.methodcount;

public class InputMethodCount3 { // violation 'Total number of methods is 5 (max allowed is 2).'

    /**
     * Dummy inner class to check that the inner-classes methods are not counted for the outer class
     */
    /**
     * Dummy method doing nothing
     */
    void doNothing50() {
    }

    /**
     * Dummy method doing nothing
     */
    void doNothing51() {
    }

    /**
     * Dummy method doing nothing
     */
    void doNothing52() {
    }

    /**
     * Dummy method doing nothing
     */
    void doNothing53() {
    }

    /**
     * Dummy method doing nothing
     */
    void doNothing54() {
    }

}
