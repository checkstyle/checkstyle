/*
RequireThis
checkFields = (default)true
checkMethods = false
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisFieldAsResource {
    private final AutoCloseable value = null;
    private final AutoCloseable other = null;
    private Object shadowed;

    void fieldAsResource() throws Exception {
        try (value) {
            System.out.println(value); // violation 'variable .value. needs "this."'
        }
    }

    void fieldAsResourceCatchFinally() throws Exception {
        try (value) {
            System.out.println(this.value);
        } catch (Exception ex) {
            System.out.println(value); // violation 'variable .value. needs "this."'
        } finally {
            System.out.println(value); // violation 'variable .value. needs "this."'
        }
    }

    void qualifiedFieldAsResource() throws Exception {
        try (this.other) {
            System.out.println(other); // violation 'variable .other. needs "this."'
        }
    }

    void mixedResources() throws Exception {
        try (AutoCloseable shadowed = () -> { }; AutoCloseable copy = value) {
            // violation above 'variable .value. needs "this."'
            System.out.println(shadowed);
            System.out.println(value); // violation 'variable .value. needs "this."'
        }
    }

    void declaredResources() throws Exception {
        AutoCloseable local = () -> { };
        try (local; var inferred = new java.io.StringReader("");
                java.io.StringReader qualified = new java.io.StringReader("")) {
            System.out.println(local);
            System.out.println(inferred);
            System.out.println(qualified);
        }
    }
}
