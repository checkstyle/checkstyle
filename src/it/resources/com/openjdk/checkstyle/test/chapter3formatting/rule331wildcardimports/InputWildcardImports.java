package com.openjdk.checkstyle.test.chapter3formatting.rule331wildcardimports;

import java.util.ArrayList;
import java.util.List;
import java.util.*; // violation 'Using the '.*' form of import should be avoided'
import static java.lang.Math.min;
import static java.lang.Math.*; // violation 'Using the '.*' form of import should be avoided'

/**
 * Invalid wildcard import examples for OpenJDK style section 3.3.1.
 */
public class InputWildcardImports {

    public int sum(int left, int right) {
        final List<Integer> values = new ArrayList<>();
        values.add(min(left, right));
        return left + right + max(left, right) + values.getFirst();
    }

}
