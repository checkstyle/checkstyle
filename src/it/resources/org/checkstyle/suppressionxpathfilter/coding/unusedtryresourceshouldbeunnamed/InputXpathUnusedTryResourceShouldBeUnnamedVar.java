package org.checkstyle.suppressionxpathfilter.coding.unusedtryresourceshouldbeunnamed;

public class InputXpathUnusedTryResourceShouldBeUnnamedVar {
    void test() {
        try (var a = lock()) { } // warn
        catch (Exception e) {}

        try (var _ = lock()) {}
        catch (Exception e) {}
    }
    AutoCloseable lock() {
        return () -> {};
    }
}

