/*
ClassMemberImpliedModifier
violateImpliedStaticOnNestedEnum = (default)true
violateImpliedStaticOnNestedInterface = (default)true
violateImpliedStaticOnNestedRecord = (default)true

*/

package com.puppycrawl.tools.checkstyle.checks.modifier.classmemberimpliedmodifier;

public class InputClassMemberImpliedModifierLocalTypes {
    // violation below 'Implied modifier 'static' should be explicit.'
    record Member(int a) { }

    void local() {
        record LocalRecord(int a) { }
        enum LocalEnum { A }
        interface LocalInterface { }
    }

    Runnable lambda = () -> {
        record LambdaRecord(int a) { }
    };

    {
        enum InitEnum { A }
    }
}
