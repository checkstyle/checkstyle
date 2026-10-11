/*
InterfaceMemberImpliedModifier
violateImpliedPublicField = (default)true
violateImpliedStaticField = (default)true
violateImpliedFinalField = (default)true
violateImpliedPublicMethod = (default)true
violateImpliedAbstractMethod = (default)true
violateImpliedPublicNested = (default)true
violateImpliedStaticNested = (default)true


*/

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
