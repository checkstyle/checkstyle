/*
ClassMemberImpliedModifier
violateImpliedStaticOnNestedEnum = false
violateImpliedStaticOnNestedInterface = (default)true
violateImpliedStaticOnNestedRecord = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.modifier.classmemberimpliedmodifier;

/**
 * Test {@code ClassMemberImpliedModifierCheck} with one attribute set to false.
 * <pre>
 *  <module name="ClassMemberImpliedModifier">
 *    <property name="violateImpliedStaticOnNestedEnum" value="false"/>
 *  </module>
 * </pre>
 */
public class InputClassMemberImpliedModifierOnClassSetEnumFalse {

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

        enum SimpleInnerEnum {
            RED, GREEN, BLUE;
        }

        static interface StaticInnerInterface {
        }

        interface SimpleInnerInterface {  // violation
        }
    }

    enum SimpleEnum {
        RED, GREEN, BLUE;

        static enum StaticInnerEnum {
            RED, GREEN, BLUE;
        }

        enum SimpleInnerEnum {
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
