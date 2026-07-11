package com.openjdk.checkstyle.test.chapterformatting.ruleimportstatements.importorder;

// violation first line 'Header mismatch*'

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import static java.lang.Math.PI;
import static java.lang.Math.max;

import static org.junit.Assert.assertEquals;

/**
 * Valid import order: java.* then javax.* then third-party (alphabetically),
 * with static imports at the bottom, all sorted alphabetically.
 */
public final class InputImportOrderValid {
    /** Dummy constant. */
    public static final double TAU = 2 * PI;

    /**
     * Private constructor to prevent instantiation.
     */
    private InputImportOrderValid() {
    }

    /**
     * Uses imports to demonstrate valid ordering.
     */
    public static void demonstrate() {
        final List<String> list = Arrays.asList("a", "b", "c");
        final Map<String, String> map = Map.of("key", "value");

        final JFrame frame = new JFrame();
        final JButton button = new JButton("Click");

        final boolean isEmpty = StringUtils.isBlank("");
        final int num = Preconditions.checkNotNull(42);

        final int maximum = max(1, 2);
        assertEquals(3, 1 + 2);
    }
}
