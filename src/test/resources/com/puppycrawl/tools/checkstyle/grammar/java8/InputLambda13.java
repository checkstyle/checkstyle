/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberName
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar.java8;

import java.util.logging.Logger;


public class InputLambda13 {

    private static final Logger LOG = Logger.getLogger(InputLambda13.class.getName());

    public static void testVoidLambda(TestOfVoidLambdas test) {
        LOG.info("Method called");
        test.doSmth("fef", 5);
    }


    public static void main(String[] args) {

        testVoidLambda((String s1, Integer i2) -> {
            LOG.info(s1);
        });
    }

    private interface TestOfVoidLambdas {

        public void doSmth(String first, Integer second);
    }
}
