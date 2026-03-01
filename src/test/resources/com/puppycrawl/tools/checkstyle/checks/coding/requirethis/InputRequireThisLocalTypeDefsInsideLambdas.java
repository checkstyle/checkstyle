/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

import java.util.function.Consumer;

public class InputRequireThisLocalTypeDefsInsideLambdas {

    int index;

    private static void method(Consumer<String> consumer) {
        consumer.accept("foo");
    }

    private void testClassInsideLambda() {
        method(s -> {
            class Inner {
                int index;

                void method(int index) {
                    int x = index;
                }
            }
        });
    }

    private void testEnumInsideLambda() {
        method(s -> {
            enum InnerEnum {
                VALUE;
                int index;

                void method(int index) {
                    int x = index;
                }
            }
        });
    }

    private void testInterfaceInsideLambda() {
        method(s -> {
            interface InnerInterface {
                default void method(int index) {
                    int x = index;
                }
            }
        });
    }

    private void testRecordInsideLambda() {
        method(s -> {
            record InnerRecord(int index) {
                void method(int index) {
                    int x = index;
                }
            }
        });
    }

    private void testAnonymousClassInsideLambda() {
        method(s -> {
            Runnable r = new Runnable() {
                int index;

                public void run() {
                }

                void method(int index) {
                    int x = index;
                }
            };
        });
    }

    private void testDirectLambdaParam() {
        method(index -> {
            String x = index;
        });
    }

    private void testNonLambdaMethod(int index) {
        index = index; // violation 'Reference to instance variable 'index' needs "this.".'
    }

    private void testCtorNestedInLambda() {
        method(s -> {
            class InnerSubscriber {
                int index;

                InnerSubscriber(int index) {
                    index = index;
                    // violation above, 'Reference to instance variable 'index' needs "this.".'
                }
            }
        });
    }

    private void testClassBoundaryWithMatchingLambdaParam() {
        method(index -> {
            class Inner {
                int index;

                void useIndex() {
                    // violation below, 'Reference to instance variable 'index' needs "this.".'
                    int x = index;
                }
            }
        });
    }
}
