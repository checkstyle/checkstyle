//non-compiled with javac: Compilable with Java9
// private method in interface

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadocmethod;

/* Config:
 * scope = "public"
 */
public interface InputMissingJavadocMethodInterfacePrivateMethod {

    private static void myPrivateMethod() {

    }
}
