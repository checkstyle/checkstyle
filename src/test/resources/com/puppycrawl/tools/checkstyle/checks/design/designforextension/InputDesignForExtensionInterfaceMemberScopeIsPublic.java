/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public interface InputDesignForExtensionInterfaceMemberScopeIsPublic {

    class Inner {

        // violation below 'Class 'Inner' looks like designed'
        public String getProperty() {
            return null;
        }

    }

}
