/*
NoFinalizer


*/

//non-compiled with eclipse till https://bugs.eclipse.org/bugs/show_bug.cgi?id=543090
//Compilable by javac, but noncompilable by eclipse
package com.puppycrawl.tools.checkstyle.checks.coding.nofinalizer;

public class InputNoFinalizerFallThrough { // ok

    void tryResource() throws Exception {
        switch (hashCode()) {
        case 1:
            try (final Resource resource = new Resource()) {
                return;
            }
        case 2:
            try (final Resource resource = new Resource()) {
                return;
            }
            finally {
                return;
            }
        case 3:
            try (final Resource resource = new Resource()) {
                return;
            }
            catch (Exception ex) {
                return;
            }
        case 4:
            try (final Resource resource = new Resource()) {
            }
            finally {
                return;
            }
        case 5:
            try (final Resource resource = new Resource()) {
                return;
            }
            finally {
            }
        case 6:
            try (final Resource resource = new Resource()) {
            }
            catch (Exception ex) {
                return;
            }
            // fallthru
        case 7:
            try (final Resource resource = new Resource()) {
            }
            // fallthru
        case 8:
            try (final Resource resource = new Resource()) {
            }
            finally {
            }
            // fallthru
        case 9:
            try (final Resource resource = new Resource()) {
            }
            catch (Exception ex) {
            }
            // fallthru
        case 10:
            try (final Resource resource = new Resource()) {
                return;
            }
            catch (Exception ex) {
            }
            // fallthru
        default:
            break;
        }
    }

    private static class Resource implements AutoCloseable { // ok
        @Override
        public void close() throws Exception {
        }
    }
}
