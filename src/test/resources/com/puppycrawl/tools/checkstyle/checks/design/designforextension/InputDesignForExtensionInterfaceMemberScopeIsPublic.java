/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public interface InputDesignForExtensionInterfaceMemberScopeIsPublic {

    class Inner {

        // violation below ''getProperty' does not have javadoc that explains how to do that safely'
        public String getProperty() {
            return null;
        }

    }

}
