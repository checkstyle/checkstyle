/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public record InputDesignForExtensionRecords(String string) {

    @Override
    public String toString() {
        return string + "my string!";
    }
}
