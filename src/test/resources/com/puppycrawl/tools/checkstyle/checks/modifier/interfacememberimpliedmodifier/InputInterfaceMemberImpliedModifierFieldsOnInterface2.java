/*
InterfaceMemberImpliedModifier
violateImpliedPublicField = (default)true
violateImpliedStaticField = (default)true
violateImpliedFinalField = false
violateImpliedPublicMethod = (default)true
violateImpliedAbstractMethod = (default)true
violateImpliedPublicNested = (default)true
violateImpliedStaticNested = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

public interface InputInterfaceMemberImpliedModifierFieldsOnInterface2 {

    public static final int fieldPublicStaticFinal = 1;

    public static int fieldPublicStatic = 1;

    public final int fieldPublicFinal = 1; // violation

    public int fieldPublic = 1; // violation

    static final int fieldStaticFinal = 1; // violation

    static int fieldStatic = 1; // violation

    final int fieldFinal = 1; // 2 violations

    int field = 1; // 2 violations

}
