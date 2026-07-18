package com.openjdk.checkstyle.test.chapterformatting.ruleimportstatements.importorder;

// violation first line 'Header mismatch*'

import javax.swing.JButton;
import javax.swing.JFrame;

import java.util.Arrays;
// violation above 'Import statement for 'java.util.Arrays' violates
// the configured import group order.'

/**
 * Invalid: javax.* imports appear before java.* imports.
 * According to OpenJDK style, java.* should come before javax.*
 */
public final class InputImportOrderWrongPackageOrder {
    /** Dummy constant. */
    public static final int MAX_VALUE = Integer.MAX_VALUE;

    /**
     * Private constructor to prevent instantiation.
     */
    private InputImportOrderWrongPackageOrder() {
    }

    /**
     * Demonstrates wrong package ordering.
     */
    public static void demonstrate() {
        final JFrame frame = new JFrame();
        final JButton button = new JButton("Test");
        final String[] arr = Arrays.copyOf(new String[]{"a"}, 5);
    }
}
