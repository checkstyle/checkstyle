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

        enum SimpleInnerEnum {  // violation
            RED, GREEN, BLUE;
        }

        static interface StaticInnerInterface {
        }

        interface SimpleInnerInterface {  // violation
        }
    }

    enum SimpleEnum {  // violation
        RED, GREEN, BLUE;

        static enum StaticInnerEnum {
            RED, GREEN, BLUE;
        }

        enum SimpleInnerEnum {  // violation
            RED, GREEN, BLUE;
        }

        static interface StaticInnerInterface {
        }

        interface SimpleInnerInterface {  // violation
        }
    }

    static interface StaticInterface {
    }

    interface SimpleInterface {  // violation
    }
}
