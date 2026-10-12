/*
RequireThis
checkFields = (default)true
checkMethods = false
validateOnlyOverlapping = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisFinallyDefault {
    private Object resource;

    void resourceScope() throws Exception {
        try (AutoCloseable resource = () -> { }) {
            System.out.println(resource);
        } finally {
            System.out.println(resource);
        }
    }
}
