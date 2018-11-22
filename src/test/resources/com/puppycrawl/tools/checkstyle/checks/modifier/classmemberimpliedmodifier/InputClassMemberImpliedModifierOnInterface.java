package com.puppycrawl.tools.checkstyle.checks.modifier.classmemberimpliedmodifier;

/**
 * Test {@code ClassMemberImpliedModifierCheck} with default attributes.
 * <pre>
 *  <module name="ClassMemberImpliedModifier">
 *  </module>
 * </pre>
 */
public interface InputClassMemberImpliedModifierOnInterface {

    public static final int fieldPublicStaticFinal = 1;

    public static int fieldPublicStatic = 1;

    public final int fieldPublicFinal = 1;

    public int fieldPublic = 1;

    static void methodPublicStatic() {
    }

    void methodPublic();

    default void methodPublicFinal() {
    }

    static enum StaticEnum {
        RED, GREEN, BLUE;
    }

    enum SimpleEnum {
        RED, GREEN, BLUE;
    }

    static interface StaticInterface {
    }

    interface SimpleInterface {
    }

    class InnerClass {

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
}
