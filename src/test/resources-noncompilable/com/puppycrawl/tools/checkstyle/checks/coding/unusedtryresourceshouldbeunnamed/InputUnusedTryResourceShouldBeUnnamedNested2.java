/*
UnusedTryResourceShouldBeUnnamed

*/

// non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unusedtryresourceshouldbeunnamed;

public class InputUnusedTryResourceShouldBeUnnamedNested2 {

    void testNested4() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (AutoCloseable a = lock()){
                    System.out.println(a);
                    // violation below, 'Unused try resource 'b' should be unnamed'
                    try (AutoCloseable b = lock()){
                        try (AutoCloseable c = lock()){
                            System.out.println(c);
                            // violation below, 'Unused try resource 'd' should be unnamed'
                            try (AutoCloseable d = lock()){

                            } catch (Exception e){
                                System.out.println(e);
                            }

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                    } catch (Exception e){
                        System.out.println(e);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }

    void testNested5() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try (AutoCloseable a = lock()){
                    System.out.println(a);
                    // violation below, 'Unused try resource 'b' should be unnamed'
                    try (AutoCloseable b = lock()){
                        try (AutoCloseable c = lock()){
                            System.out.println(c);
                            Runnable runnable2 = new Runnable() {
                                @Override
                                public void run() {
                                    // violation below, 'Unused try resource 'd' should be unnamed'
                                    try (AutoCloseable d = lock()){

                                    } catch (Exception e){
                                        System.out.println(e);
                                    }
                                }
                            };

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }

                    } catch (Exception e){
                        System.out.println(e);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        };
    }

    void testNested6(){
        try (AutoCloseable a = lock()) {
            // violation below, 'Unused try resource 'b' should be unnamed'
            try (AutoCloseable b = a) {
                try (AutoCloseable c = lock()) {
                    try (AutoCloseable d = c) {
                        // violation below, 'Unused try resource 'e' should be unnamed'
                        try (AutoCloseable e = d) {

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    AutoCloseable lock() {
        return null;
    }
}
