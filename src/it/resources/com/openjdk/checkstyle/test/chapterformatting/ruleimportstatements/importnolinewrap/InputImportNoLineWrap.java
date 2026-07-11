package com.openjdk.checkstyle.test.chapterformatting.ruleimportstatements.importnolinewrap;

// violation first line 'Header is missing*'
// violation below 'should not be line-wrapped'
import java.util.
        HashMap;
import java.util.Map;
import java.util.
        Set;
// violation 2 lines above 'should not be line-wrapped'

/**
 * Test input for the import no line wrap rule.
 */
public class InputImportNoLineWrap {

    Map<Integer, Integer> map = new HashMap<>();
    Set<Integer> set;

}
