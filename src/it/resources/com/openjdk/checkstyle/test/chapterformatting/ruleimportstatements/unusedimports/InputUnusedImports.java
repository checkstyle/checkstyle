package com.openjdk.checkstyle.test.chapterformatting.ruleimportstatements.unusedimports;

// violation first line 'Header is missing'

import java.util.HashMap; // violation 'Unused import - java.util.HashMap'
import java.util.List;

import static java.lang.Integer.parseInt;
 // violation above 'Unused import - java.lang.Integer.parseInt.'

/**
 * Test input for the unused import rule.
 */
public final class InputUnusedImports {
    List<Integer> list;
}
