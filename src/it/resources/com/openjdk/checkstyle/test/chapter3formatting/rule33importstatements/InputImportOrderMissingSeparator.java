package com.openjdk.checkstyle.test.chapter3formatting.rule33importstatements;

import java.util.Arrays;
import java.util.Map;
import javax.swing.JButton;
// violation above ''javax.swing.JButton' should be separated from previous imports.'

/**
 * Invalid: missing blank line separator between java.* and javax.* groups.
 * According to OpenJDK style, each group should be separated by a blank line.
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
