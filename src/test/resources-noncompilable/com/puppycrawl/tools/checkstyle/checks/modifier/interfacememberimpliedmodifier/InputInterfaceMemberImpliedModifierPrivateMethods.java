//non-compiled with javac: Compilable with Java9
package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

/**
 * Input for Java 9 private methods in interfaces.
 */
public interface InputInterfaceMemberImpliedModifierPrivateMethods {

    private static void methodPrivateStatic() {
    }

    private void methodPrivateInstance() {
    }

}
