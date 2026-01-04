/*
MissingOverrideOnRecordAccessor

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.missingoverrideonrecordaccessor;

interface Runnable {
    void run();
}

/**
 * Record with method that is NOT an accessor.
 */
public record InputMissingOverrideOnRecordAccessorNonAccessor(String name) implements Runnable {
    @Override
    public String name() {
        return name.toUpperCase();
    }

    public void run() {
        System.out.println(name);
    }

    public void customMethod() {
        System.out.println("custom");
    }
}
