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


public class InputLambda3 {

    private static final Logger LOG = Logger.getLogger(InputLambda3.class.getName());

    public static void testVoidLambda(TestOfVoidLambdas test) {
        LOG.info("Method called");
        test.doSmth();
    }


    public static void main(String[] args) {
        testVoidLambda(() -> {
            LOG.info("Method in interface called");
        });
    }

    private interface TestOfVoidLambdas {

        public void doSmth();
    }
}
