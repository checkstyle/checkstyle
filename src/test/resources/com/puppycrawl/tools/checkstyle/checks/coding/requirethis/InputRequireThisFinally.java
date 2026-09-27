/*
RequireThis
checkFields = (default)true
checkMethods = false
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisFinally {
    private Object resource;
    private Object other;

    void resourceScope() throws Exception {
        try (AutoCloseable resource = () -> { }) {
            System.out.println(resource);
        } catch (Exception ex) {
            System.out.println(resource); // violation 'variable .resource. needs "this."'
        } finally {
            System.out.println(resource); // violation 'variable .resource. needs "this."'
            System.out.println(this.resource);
        }
        System.out.println(resource); // violation 'variable .resource. needs "this."'
    }

    void multipleResources() throws Exception {
        try (AutoCloseable resource = () -> { }; AutoCloseable other = resource) {
            System.out.println(other);
        } finally {
            System.out.println(resource); // violation 'variable .resource. needs "this."'
            System.out.println(other); // violation 'variable .other. needs "this."'
        }
    }

    void nestedResources() throws Exception {
        try (AutoCloseable resource = () -> { }) {
            try (AutoCloseable other = () -> { }) {
                System.out.println(resource);
                System.out.println(other);
            } finally {
                System.out.println(resource);
                System.out.println(other); // violation 'variable .other. needs "this."'
            }
            System.out.println(resource);
        } finally {
            System.out.println(resource); // violation 'variable .resource. needs "this."'
        }
    }

    void parameterScope(Object other) throws Exception {
        try (AutoCloseable resource = () -> { }) {
            System.out.println(resource);
        } finally {
            System.out.println(other);
            Object resource = new Object();
            System.out.println(resource);
        }
    }

    void localScope() throws Exception {
        Object other = new Object();
        try (AutoCloseable resource = () -> { }) {
            System.out.println(resource);
        } finally {
            System.out.println(other);
            {
                Object resource = new Object();
                System.out.println(resource);
            }
            System.out.println(resource); // violation 'variable .resource. needs "this."'
        }
    }

    void ordinaryTry() {
        try {
            System.out.println(this.resource);
        } finally {
            System.out.println(resource); // violation 'variable .resource. needs "this."'
        }
    }

    void ordinaryTryInsideResource() throws Exception {
        try (AutoCloseable resource = () -> { }) {
            try {
                System.out.println(resource);
            } finally {
                System.out.println(resource);
            }
        }
    }

    void tryInsideFinally() throws Exception {
        try (AutoCloseable resource = () -> { }) {
            System.out.println(resource);
        } finally {
            try (AutoCloseable other = () -> { }) {
                System.out.println(other);
                System.out.println(resource); // violation 'variable .resource. needs "this."'
            }
        }
    }

    void ordinaryTryWithLocal() {
        Object resource = new Object();
        try {
            System.out.println(resource);
        } finally {
            System.out.println(resource);
        }
    }
}
