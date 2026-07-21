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

    // violation below 'Implied modifier 'static' should be explicit.'
    public final int fieldPublicFinal = 1;

    // violation below 'Implied modifier 'static' should be explicit.'
    public int fieldPublic = 1;

    // violation below 'Implied modifier 'public' should be explicit.'
    static final int fieldStaticFinal = 1;

    // violation below 'Implied modifier 'public' should be explicit.'
    static int fieldStatic = 1;

    final int fieldFinal = 1;
    // 2 violations above:
    //        'Implied modifier 'public' should be explicit.'
    //        'Implied modifier 'static' should be explicit.'

    int field = 1;
    // 2 violations above:
    //        'Implied modifier 'public' should be explicit.'
    //        'Implied modifier 'static' should be explicit.'
}
