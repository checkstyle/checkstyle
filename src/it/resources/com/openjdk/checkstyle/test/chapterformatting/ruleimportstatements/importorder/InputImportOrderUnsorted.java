package com.openjdk.checkstyle.test.chapterformatting.ruleimportstatements.importorder;

// violation first line 'Header mismatch'

import java.util.Map;
import java.util.Arrays;
// violation above '.* 'java.util.Arrays' .* Should be before 'java.util.Map'.'

import javax.swing.JFrame;
import javax.swing.JButton;
// violation above '.* 'javax.swing.JButton' .* Should be before 'javax.swing.JFrame'.'

/**
 * Invalid: imports within each group are not alphabetically sorted.
 */
public final class InputImportOrderUnsorted {
    /** Dummy constant. */
    public static final int VALUE = 42;

    /**
     * Private constructor to prevent instantiation.
     */
    private InputImportOrderUnsorted() {
    }

    /**
     * Demonstrates unsorted imports within groups.
     */
    public static void demonstrate() {
        final Map<String, String> map = Map.of("key", "value");
        final String[] arr = Arrays.copyOf(new String[]{"a"}, 5);
        final JFrame frame = new JFrame();
        final JButton button = new JButton("Test");
    }
}
