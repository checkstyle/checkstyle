package com.openjdk.checkstyle.test.chapter3formatting.rule331wildcardimports;

import java.util.*; // violation '.*form of import should be avoided.*'
import static java.lang.Math.*; // violation '.*form of import should be avoided.*'

/**
 * Invalid wildcard import examples for OpenJDK style section 3.3.1.
 */
public class InputWildcardImports {

    public int sum(int left, int right) {
        return left + right + max(left, right);
    }

}
