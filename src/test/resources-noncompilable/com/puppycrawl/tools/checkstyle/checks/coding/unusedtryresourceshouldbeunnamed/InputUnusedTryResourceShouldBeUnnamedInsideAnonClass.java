/*
UnusedTryResourceShouldBeUnnamed

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedtryresourceshouldbeunnamed;

public class InputUnusedTryResourceShouldBeUnnamedInsideAnonClass {

    void testInsideAnonClass() {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // violation below, 'Unused try resource 'a' should be unnamed'
                    try (AutoCloseable a = lock()) {

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            };
        }

    void testInsideAnonClass2() {
        // violation below, 'Unused try resource 'a' should be unnamed'
        try (AutoCloseable a = lock()) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // violation below, 'Unused try resource 'b' should be unnamed'
                    try (AutoCloseable b = lock()) {

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            };
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    void testInsideAnonClass3() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (AutoCloseable a = lock()) {
                    System.out.println(a);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }

    AutoCloseable lock() {
        return null;
    }
}
