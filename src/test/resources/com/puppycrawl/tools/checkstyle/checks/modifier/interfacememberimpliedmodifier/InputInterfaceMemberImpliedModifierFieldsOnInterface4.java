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

    // violation below 'Implied modifier 'final' should be explicit.'
    public static int fieldPublicStatic = 1;

    // violation below 'Implied modifier 'static' should be explicit.'
    public final int fieldPublicFinal = 1;

    public int fieldPublic = 1;
    // 2 violations above:
    //     'Implied modifier 'final' should be explicit.'
    //     'Implied modifier 'static' should be explicit.'

    static final int fieldStaticFinal = 1;

    // violation below 'Implied modifier 'final' should be explicit.'
    static int fieldStatic = 1;

    // violation below 'Implied modifier 'static' should be explicit.'
    final int fieldFinal = 1;

    int field = 1;
    // 2 violations above:
    //     'Implied modifier 'final' should be explicit.'
    //     'Implied modifier 'static' should be explicit.'

}
