/*
MissingJavadocMethod


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

public class InputMissingJavadocMethodBasic {

    // violation below 'Missing a Javadoc comment'
    public void shouldWarn() {}

    // violation below 'Missing a Javadoc comment'
    public void shouldNotWarn() {}

    class A {}
}
