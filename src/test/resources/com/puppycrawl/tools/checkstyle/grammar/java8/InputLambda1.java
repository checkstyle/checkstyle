/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar.java8;

import java.util.logging.Logger;


public class InputLambda1 {

    private static final Logger LOG = Logger.getLogger(InputLambda1.class.getName());

    static Runnable r1 = ()->LOG.info("Hello world one!");
    static Runnable r2 = () -> LOG.info("Hello world two!");

    public static void main(String[] args) {
        r1.run();
        r2.run();
    }
}
