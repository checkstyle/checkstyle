package com.openjdk.checkstyle.test.chapterformatting.ruleimportstatements.importorder;

// violation first line 'Header mismatch'

import static java.lang.Math.PI;
import static java.lang.Math.max;

import java.util.Arrays;
// violation above """Import 'java.util.Arrays' violates the configured relative order
// between static and non-static imports."""

/**
 * Invalid: static imports appear before non-static imports.
 * According to OpenJDK style with option=bottom, non-static should come first.
 */
public final class InputImportOrderStaticFirst {
    /** Dummy constant using static import. */
    public static final double VALUE = PI;

    /**
     * Private constructor to prevent instantiation.
     */
    private InputImportOrderStaticFirst() {
    }

    /**
     * Demonstrates invalid static import ordering.
     */
    public static void demonstrate() {
        final String[] arr = Arrays.copyOf(new String[]{"a"}, 5);
        final double tau = 2 * PI;
        final int maximum = max(1, 2);
    }
}
