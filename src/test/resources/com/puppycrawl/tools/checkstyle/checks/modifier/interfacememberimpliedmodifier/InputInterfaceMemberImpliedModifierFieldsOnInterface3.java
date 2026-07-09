/*
InterfaceMemberImpliedModifier
violateImpliedPublicField = (default)true
violateImpliedStaticField = false
violateImpliedFinalField = (default)true
violateImpliedPublicMethod = (default)true
violateImpliedAbstractMethod = (default)true
violateImpliedPublicNested = (default)true
violateImpliedStaticNested = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.interfacememberimpliedmodifier;

public interface InputInterfaceMemberImpliedModifierFieldsOnInterface3 {

    public static final int fieldPublicStaticFinal = 1;

    // violation below 'Implied modifier 'final' should be explicit.'
    public static int fieldPublicStatic = 1;

    public final int fieldPublicFinal = 1;

    // violation below 'Implied modifier 'final' should be explicit.'
    public int fieldPublic = 1;

    // violation below 'Implied modifier 'public' should be explicit.'
    static final int fieldStaticFinal = 1;

    static int fieldStatic = 1;
    // 2 violations above:
    //      'Implied modifier 'final' should be explicit.'
    //      'Implied modifier 'public' should be explicit.'

    // violation below 'Implied modifier 'public' should be explicit.'
    final int fieldFinal = 1;

    int field = 1;
    // 2 violations above:
    //      'Implied modifier 'final' should be explicit.'
    //      'Implied modifier 'public' should be explicit.'

}
