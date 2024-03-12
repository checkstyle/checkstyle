/*
DesignForExtension
ignoredAnnotations = (default)After, AfterClass, Before, BeforeClass, Test
requiredJavadocPhrase = (default).*


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.design.designforextension;

public record InputDesignForExtensionRecords(String string) {

    @Override
    public String toString() {
        return string + "my string!";
    }
}
