package com.openjdk.checkstyle.test.chapterformatting.ruleimportstatements.importnolinewrap;

import java.util. // violation 'should not be line-wrapped'
        HashMap;
import java.util.Map;
import java.util. // violation 'should not be line-wrapped'
        Set;

/**
 * Test input for the import no line wrap rule.
 */
public class InputImportNoLineWrap {

    Map<Integer, Integer> map = new HashMap<>();
    Set<Integer> set;

}
