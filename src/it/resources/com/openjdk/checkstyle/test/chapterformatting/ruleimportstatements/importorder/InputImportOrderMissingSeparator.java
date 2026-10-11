package com.openjdk.checkstyle.test.chapterformatting.ruleimportstatements.importorder;

// violation first line 'Header mismatch'

import java.util.Arrays;
import java.util.Map;
import javax.swing.JButton;

/**
 * Test input to check no error will be thrown for separator.
 */
public final class InputImportOrderMissingSeparator {
    /** Dummy constant. */
    public static final int VALUE = 42;

    /**
     * Private constructor to prevent instantiation.
     */
    private InputImportOrderMissingSeparator() {
    }

    /**
     * Demonstrates missing separator between import groups.
     */
    public static void demonstrate() {
        final String[] arr = Arrays.copyOf(new String[]{"a"}, 5);
        final Map<String, String> map = Map.of("key", "value");
        final JButton button = new JButton("Test");
    }
}
