/*
com.puppycrawl.tools.checkstyle.checks.modifier.InterfaceMemberImpliedModifierCheck
violateImpliedPublicField = false
violateImpliedStaticField = (default)true
violateImpliedFinalField = (default)true
violateImpliedPublicMethod = (default)true
violateImpliedAbstractMethod = (default)true
violateImpliedPublicNested = (default)true
violateImpliedStaticNested = (default)true


*/

package com.puppycrawl.tools.checkstyle.api.violation;

public interface InputViolation1 {

    public static final int fieldPublicStaticFinal = 1;

    int field = 1; // 2 violations

}
