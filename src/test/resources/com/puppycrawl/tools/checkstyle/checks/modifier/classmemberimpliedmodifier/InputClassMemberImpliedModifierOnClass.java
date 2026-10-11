/*
ClassMemberImpliedModifier
violateImpliedStaticOnNestedEnum = (default)true
violateImpliedStaticOnNestedInterface = (default)true
violateImpliedStaticOnNestedRecord = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.classmemberimpliedmodifier;

/**
 * Test {@code ClassMemberImpliedModifierCheck} with default attributes.
 * <pre>
 *  <module name="ClassMemberImpliedModifier">
 *  </module>
 * </pre>
 */
public class InputClassMemberImpliedModifierOnClass {

    public static final int fieldPublicStaticFinal = 1;

    public static int fieldPublicStatic = 1;

    public final int fieldPublicFinal = 1;

    public int fieldPublic = 1;

    public static void methodPublicStatic() {
    }

    public static final void methodPublicStaticFinal() {
    }

    public void methodPublic() {
    }

    public final void methodPublicFinal() {
    }

    private void methodPrivate() {
    }

    static enum StaticEnum {
        RED, GREEN, BLUE;

        static enum StaticInnerEnum {
            RED, GREEN, BLUE;
        }

        // violation below 'Implied modifier 'static' should be explicit.'
        enum SimpleInnerEnum {
            RED, GREEN, BLUE;
        }

        static interface StaticInnerInterface {
        }

        // violation below 'Implied modifier 'static' should be explicit.'
        interface SimpleInnerInterface {
        }
    }

    // violation below 'Implied modifier 'static' should be explicit.'
    enum SimpleEnum {
        RED, GREEN, BLUE;

        static enum StaticInnerEnum {
            RED, GREEN, BLUE;
        }

        // violation below 'Implied modifier 'static' should be explicit.'
        enum SimpleInnerEnum {
            RED, GREEN, BLUE;
        }

        static interface StaticInnerInterface {
        }

        // violation below 'Implied modifier 'static' should be explicit.'
        interface SimpleInnerInterface {
        }
    }

    static interface StaticInterface {
    }

    // violation below 'Implied modifier 'static' should be explicit.'
    interface SimpleInterface {
    }
}
