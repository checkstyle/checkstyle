/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public interface InputDesignForExtensionInterfaceMemberScopeIsPublic {

    class Inner {

        public String getProperty() { // violation
            return null;
        }

    }

}
