package com.openjdk.checkstyle.test.chapter3formatting.rule331wildcardimports;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.max;

/**
 * Valid import examples for OpenJDK style section 3.3.1.
 */
public class InputWildcardImportsValid {

    public List<Integer> toSingletonList(int value) {
        final List<Integer> result = new ArrayList<>();
        result.add(max(value, 0));
        return result;
    }

}
