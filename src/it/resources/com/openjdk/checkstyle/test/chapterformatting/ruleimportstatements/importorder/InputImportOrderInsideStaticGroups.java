package com.openjdk.checkstyle.test.chapterformatting.ruleimportstatements.importorder;

// violation first line 'Header mismatch*'

import java.util.List;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static org.mockito.Mockito.mock;
import static java.util.Collections.emptyList;
// violation above """Import statement for 'java.util.Collections.emptyList'
// violates the configured import group order."""

import javax.swing.JButton;
// violation above """Import 'javax.swing.JButton' violates the configured
// relative order between static and non-static import"""

/**
 * Test file for static import order.
 */
public final class InputImportOrderInsideStaticGroups {
    /** Dummy constant. */
    public static final double TAU = 2 * PI;

    final JButton button = new JButton("Click");

    List<String> list2 = emptyList();

    List<String> mockList = mock(List.class);

    int a = abs(1);

}
