/*
UnusedTryResourceShouldBeUnnamed


*/

// non-compiled with javac: Compilable with Java25
package org.checkstyle.suppressionxpathfilter.coding.unusedtryresourceshouldbeunnamed;

public class InputXpathUnusedTryResourceShouldBeUnnamedVar {
    void test() {
        try (var a = lock()) { } // violation 'Unused try resource .* should be unnamed\.'
        catch (Exception e) {}

        try (var _ = lock()) {}
        catch (Exception e) {}
    }
    AutoCloseable lock() {
        return () -> {};
    }
}
