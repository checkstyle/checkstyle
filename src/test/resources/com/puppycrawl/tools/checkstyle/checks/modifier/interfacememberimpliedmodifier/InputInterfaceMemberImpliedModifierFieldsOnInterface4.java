/*
InterfaceMemberImpliedModifier
violateImpliedPublicField = false
violateImpliedStaticField = (default)true
violateImpliedFinalField = (default)true
violateImpliedPublicMethod = (default)true
violateImpliedAbstractMethod = (default)true
violateImpliedPublicNested = (default)true
violateImpliedStaticNested = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

public interface InputInterfaceMemberImpliedModifierFieldsOnInterface4 {

    public static final int fieldPublicStaticFinal = 1;

    public static int fieldPublicStatic = 1; // violation

    public final int fieldPublicFinal = 1; // violation

    public int fieldPublic = 1; // 2 violations

    static final int fieldStaticFinal = 1;

    static int fieldStatic = 1; // violation

    final int fieldFinal = 1; // violation

    int field = 1; // 2 violations

}
