package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod; class InputMissingJavadocMethodCoverage {
    public void topMethod() {} // violation 'Missing a Javadoc comment.'
    // comment
    public void method() {} // violation 'Missing a Javadoc comment.'

    public int Cost1() { // violation 'Missing a Javadoc comment.'
        return 1;
    }

    public int getCost1(int value) { // violation 'Missing a Javadoc comment.'
        return value;
    }
}
