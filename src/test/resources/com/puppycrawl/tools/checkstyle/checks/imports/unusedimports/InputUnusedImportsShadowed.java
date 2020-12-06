package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.List; // ok
import java.util.Map; // violation
import java.util.Set; // violation

/*
 * Config: default
 */
public class InputUnusedImportsShadowed {

    InputUnusedImportsShadowed() {
        List x = null; // java.util.List
    }

    static class Nested {
        List.Set field; // Nested.List.Set
        public Nested() {
            List x = null; // Nested.List
        }

        @Map
        interface List {
            Set foo(); // Nested.List.Set
            enum Set {
            }
        }
    }

    @interface Map {}

}
