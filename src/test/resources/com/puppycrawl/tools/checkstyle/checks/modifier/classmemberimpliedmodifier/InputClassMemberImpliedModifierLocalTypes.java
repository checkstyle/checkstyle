/*
ClassMemberImpliedModifier
violateImpliedStaticOnNestedEnum = (default)true
violateImpliedStaticOnNestedInterface = (default)true
violateImpliedStaticOnNestedRecord = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.classmemberimpliedmodifier;

public class InputClassMemberImpliedModifierLocalTypes {

    // violation below 'Implied modifier 'static' should be explicit.'
    record Member(int a) {
    }

    {
        record InInitializer(int a) {
        }
    }

    void inMethod() {
        record LocalRecord(int a) {
            // violation below 'Implied modifier 'static' should be explicit.'
            record MemberOfLocal(int b) {
            }
        }
        enum LocalEnum { A }
        interface LocalInterface {
        }
    }

    Runnable inLambda() {
        return () -> {
            record InLambda(int a) {
            }
        };
    }

    Object inAnonymous() {
        return new Object() {
            void method() {
                enum InAnonymousMethod { B }
            }
        };
    }
}
