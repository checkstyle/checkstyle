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

public class InputInterfaceMemberImpliedModifierLocalTypes {

    void method() {
        interface Local {
            int CONST = 1;
            // 3 violations above:
            //     'Implied modifier 'final' should be explicit.'
            //     'Implied modifier 'public' should be explicit.'
            //     'Implied modifier 'static' should be explicit.'

            void run();
            // 2 violations above:
            //     'Implied modifier 'abstract' should be explicit.'
            //     'Implied modifier 'public' should be explicit.'

            // violation below 'Implied modifier 'public' should be explicit.'
            default void defaultMethod() {
            }

            class Nested {
            }
            // 2 violations 2 lines above:
            //     'Implied modifier 'public' should be explicit.'
            //     'Implied modifier 'static' should be explicit.'
        }

        class LocalClass {
            interface Member {
                public static final int CONST = 1;

                public abstract void run();
            }
        }
    }

    interface Outer {
        // violation below 'Implied modifier 'public' should be explicit.'
        default void method() {
            int local = 1;

            class LocalInDefault {
                int field;

                void run() {
                }
            }

            interface LocalInterfaceInDefault {
                public static final int CONST = 1;
            }
        }
    }
}
