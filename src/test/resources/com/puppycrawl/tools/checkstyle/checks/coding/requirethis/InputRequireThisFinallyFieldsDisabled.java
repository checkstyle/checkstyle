/*
RequireThis
checkFields = false
checkMethods = false
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisFinallyFieldsDisabled {
    private Object resource;

    void resourceScope() throws Exception {
        try (AutoCloseable resource = () -> { }) {
            System.out.println(resource);
        } finally {
            System.out.println(resource);
        }
    }
}
