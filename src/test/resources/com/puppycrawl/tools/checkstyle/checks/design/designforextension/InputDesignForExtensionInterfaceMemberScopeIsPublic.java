/*
<<<<<<< Updated upstream
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Override, Test
requiredJavadocPhrase = (default).*

=======
com.puppycrawl.tools.checkstyle.checks.design.DesignForExtensionCheck
ignoredAnnotations = (default)SafeVarargs, Override
requiredJavadocPhrase = (default)
>>>>>>> Stashed changes
*/
package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public interface InputDesignForExtensionInterfaceMemberScopeIsPublic {

    class Inner {

        public String getProperty() { // violation
            return null;
        }

    }

}
