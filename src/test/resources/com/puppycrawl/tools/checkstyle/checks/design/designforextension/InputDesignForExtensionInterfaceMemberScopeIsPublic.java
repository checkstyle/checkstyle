/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public interface InputDesignForExtensionInterfaceMemberScopeIsPublic {

    class Inner {

        // violation below 'Method 'getProperty' does not have javadoc that explains'
        public String getProperty() {
            return null;
        }

    }

}
