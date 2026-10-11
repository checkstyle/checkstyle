/*
UnusedTryResourceShouldBeUnnamed

*/
// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.coding.unusedtryresourceshouldbeunnamed;

import java.io.IOException;

public class InputUnusedTryResourceShouldBeUnnamedEdgeCases {

    void testVarUnderscore() throws Exception {
        try (var _ = lock()) {
        } catch (Exception e) {}

        try (var _ = lock(); var _ = lock()) {
        } catch (Exception e) {}
    }

    void testUsedOnlyInFinally() throws Exception {
        try (AutoCloseable a = lock()) {
            mightThrow();
            System.out.println(a);
        }
    }

    void testUsedAsMethodArgument() throws Exception {
        try (AutoCloseable a = lock()) {
            consume(a);
        } catch (Exception e) {}
    }

    void testUsedInTernary() throws Exception {
        try (AutoCloseable a = lock()) {
            Object x = (a != null) ? a : null;
        } catch (Exception e) {}
    }

    void testMultiCatchShadowing() throws Exception {
        // violation below 'Unused try resource 'e' should be unnamed'
        try (AutoCloseable e = lock()) {
        } catch (IOException | RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    void testMethodNameSameAsResource() throws Exception {
        // violation below 'Unused try resource 'lock' should be unnamed'
        try (AutoCloseable lock = lock()) {
        } catch (Exception e) {}

        try (AutoCloseable lock = lock()) {
            lock.close();
        } catch (Exception e) {}
    }

    void testSequentialTriesSameName() throws Exception {
        // violation below 'Unused try resource 'r' should be unnamed'
        try (AutoCloseable r = lock()) {
        } catch (Exception e) {}

        try (AutoCloseable r = lock()) {
            consume(r);
        } catch (Exception e) {}
    }

    void testUsedInsideLambdaInBody() throws Exception {
        try (AutoCloseable a = lock()) {
            Runnable r = () -> {
                try {
                    System.out.println(a);
                } catch (Exception ignored) {}
            };
            r.run();
        } catch (Exception e) {}
    }

    void testUsedInStringConcat() throws Exception {
        try (AutoCloseable a = lock()) {
            String s = "resource=" + a;
        } catch (Exception e) {}
    }

    void testUsedInInstanceof() throws Exception {
        try (AutoCloseable a = lock()) {
            boolean b = a instanceof AutoCloseable;
        } catch (Exception e) {}
    }

    void testBareReferenceMatchingDeclaredResource() throws Exception {
        try (AutoCloseable r = lock()) {
            try (r) {
            } catch (Exception e) {}
        } catch (Exception e) {}
    }

    void testResourceNameMatchesRightSideOfDot() throws Exception {
        // violation below 'Unused try resource 'close' should be unnamed'
        try (AutoCloseable close = lock()) {
            something.close();
        } catch (Exception e) {}
    }

    void testUsedInNestedCatchWithDifferentParamName() throws Exception {
        try (AutoCloseable a = lock()) {
            try {
                mightThrow();
            } catch (Exception e) {
                consume(a);
            }
        } catch (Exception e) {}
    }

    AutoCloseable lock() { return null; }
    void consume(AutoCloseable ac) {}
    void mightThrow() throws Exception {}
    AutoCloseable something = null;

}
