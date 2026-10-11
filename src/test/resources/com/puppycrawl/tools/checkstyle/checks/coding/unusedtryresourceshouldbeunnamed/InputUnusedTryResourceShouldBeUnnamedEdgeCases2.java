/*
UnusedTryResourceShouldBeUnnamed

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedtryresourceshouldbeunnamed;

import java.io.IOException;

public class InputUnusedTryResourceShouldBeUnnamedEdgeCases2 {

    static {
        try {
            // violation below 'Unused try resource 's' should be unnamed'
            try (AutoCloseable s = staticLock()) {
            } catch (Exception e) {}

            try (AutoCloseable s = staticLock()) {
                System.out.println(s);
            } catch (Exception e) {}
        } catch (Exception e) {}
    }

    void testFinalResource() throws Exception {
        // violation below 'Unused try resource 'f' should be unnamed'
        try (final AutoCloseable f = lock()) {
        } catch (Exception e) {}

        try (final AutoCloseable f = lock()) {
            System.out.println(f);
        } catch (Exception e) {}
    }

    void testChainOnlyLastUnused() throws Exception {
        // violation 4 lines below 'Unused try resource 'd' should be unnamed'
        try (AutoCloseable a = lock();
             AutoCloseable b = wrap(a);
             AutoCloseable c = wrap(b);
             AutoCloseable d = wrap(c)) {
        } catch (Exception e) {}

        try (AutoCloseable a = lock();
             AutoCloseable b = wrap(a);
             AutoCloseable c = wrap(b)) {
            System.out.println(c);
        } catch (Exception e) {}
    }

    void testTryInsideCatch() {
        try {
            mightThrow();
        } catch (Exception outer) {
            // violation below 'Unused try resource 'inner' should be unnamed'
            try (AutoCloseable inner = staticLock()) {
            } catch (Exception e) {}
        }
    }

    void testCatchShadowingFirstArm() throws Exception {
        // violation below 'Unused try resource 'x' should be unnamed'
        try (AutoCloseable x = lock()) {
        } catch (IOException x) {
            System.out.println(x.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void testNoCatchShadowingDifferentName() throws Exception {
        // violation below 'Unused try resource 'res' should be unnamed'
        try (AutoCloseable res = lock()) {
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void testTwoUnusedOneUsed() throws Exception {
        // violation 2 lines below 'Unused try resource 'unused1' should be unnamed'
        // violation 3 lines below 'Unused try resource 'unused2' should be unnamed'
        try (AutoCloseable unused1 = lock();
             AutoCloseable used = lock();
             AutoCloseable unused2 = lock()) {
            System.out.println(used);
        } catch (Exception e) {}
    }

    static AutoCloseable staticLock() { return null; }
    AutoCloseable lock() { return null; }
    AutoCloseable wrap(AutoCloseable ac) { return null; }
    void mightThrow() throws Exception {}
}
