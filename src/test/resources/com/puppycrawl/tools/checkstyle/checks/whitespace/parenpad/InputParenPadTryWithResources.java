package com.puppycrawl.tools.checkstyle.checks.whitespace.parenpad;

class InputParenPadTryWithResources {
    private void tryWithResources() throws Exception {
        try (AutoCloseable a = null) {} // ok
        try (AutoCloseable a = null; AutoCloseable b = null) {} // ok
        try (AutoCloseable a = null; AutoCloseable b = null; ) {} // ok
        try (AutoCloseable a = null; AutoCloseable b = null; ) {} // ok
        try (AutoCloseable a = null ) {} // warning
        try (AutoCloseable a = null; AutoCloseable b = null ) {} // warning
    }
}
